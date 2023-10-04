package Client;

import Commands.Command;
import Common.Settings;
import Common.TCPUnit;
import Common.UserInfo;
import Models.Data;
import Models.User;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPClient extends TCPUnit {

    //region Поля
    private CommandReaderClient commandReader;
    private final String host;
    private SocketChannel socketChannel;
    //endregion

    //region Конструкторы
    public TCPClient() {
        this(System.in, "localhost", 8080);
    }

    public TCPClient(InputStream inputStream, String host, int port) {
        super(inputStream, port, false, Settings.isDebug);
        this.commandReader = new CommandReaderClient(inputStream);
        this.host = host;
    }
    //endregion

    //region Методы


    /**
     * @param socketChannel
     * @param data
     * @throws IOException
     */
    private void Send(SocketChannel socketChannel, Data data) throws IOException {
        data.setUserInfo(this.currentUserInfo);
        // Сериализация объекта
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();

        byte[] bytes = byteArrayOutputStream.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        // Отправка данных
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }

        objectOutputStream.close();
        byteArrayOutputStream.close();

    }

    /**
     * @param socketChannel
     * @throws Exception
     */
    private Object Receive(SocketChannel socketChannel) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(16384);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        Selector selector = socketChannel.provider().openSelector();

        int interestSet = SelectionKey.OP_READ;
        socketChannel.register(selector, interestSet);

        while (true) {
            if (selector.select(Settings.Timeouts.Client.connectionTimeout) == 0) {
                Print("Тайм-аут: сервер недоступен или не отвечает");
                Thread.sleep(Settings.Timeouts.Client.requestTimeout);
            }

            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while (keys.hasNext()) {
                SelectionKey key = keys.next();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isReadable()) {
                    buffer.clear();
                    int bytesRead = socketChannel.read(buffer);

                    if (bytesRead == -1) {
                        // Конец потока достигнут
                        break;
                    }

                    buffer.flip();
                    byte[] data = new byte[bytesRead];
                    buffer.get(data);
                    byteStream.write(data, 0, bytesRead);

                    keys.remove();
                }

                try (ObjectInputStream objStream = new ObjectInputStream(
                        new ByteArrayInputStream(byteStream.toByteArray()))) {
                    return objStream.readObject();
                } catch (Exception ex) {
                    //Print(ex);
                }
            }
        }
    }

    private void ProcessCommand() throws Exception {
        System.out.println("Введите команду или 'exit', чтобы выйти:");
        String commandName = scanner.nextLine().trim();
        if (commandName.equals(Command.Titles.exit)) {
            this.isStarted = false;
            return;
        }
        String[] words = commandName.split("\\s+");
        Data commandData = new Data(this.currentUserInfo, this.commandHelp.GetCommand(commandName), null);
        commandData.setCommand(this.commandHelp.GetCommand(words[0]));
        if (commandData.getCommand() == null)
            throw new Exception("Передана неизвестная команда!");
        if (words.length == 1) {
            if (commandName.equals(Command.Titles.exit))
                return;
            Send(this.socketChannel, (Data) this.commandReader.Execute(commandData));
        }
        if (words.length > 1) {
            String[] params = new String[words.length - 1];
            System.arraycopy(words, 1, params, 0, words.length - 1);
            commandData.Add(params);

            Send(this.socketChannel, (Data) this.commandReader.Execute(commandData));
        }
        Object serverResponse = Receive(socketChannel);
        if (serverResponse != null && serverResponse.getClass() == UserInfo.class) {
            this.currentUserInfo = (UserInfo) serverResponse;
            System.out.println("Вы авторизованы на сервере!");
        } else {
            Print("Получен ответ от сервера:");
            Print(serverResponse);
        }
    }


    /**
     * @throws Exception
     */
    public void Start() throws Exception {
        this.isStarted = true;
        Print("Клиент запущен...");
        while (this.isStarted) {
            try {
                //if (this.socketChannel == null || !this.socketChannel.isConnected()) {
                this.socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
                this.socketChannel.configureBlocking(false);
                //}
            } catch (ConnectException ex) {
                Print("Ошибка подключения, повторное подключение");
                Thread.sleep(Settings.Timeouts.Client.requestTimeout);
                continue;
            }
            try {
                this.ProcessCommand();
            } catch (Exception ex) {
                this.Print(ex);

            }

        }
        socketChannel.close();

    }

    //endregion

}
