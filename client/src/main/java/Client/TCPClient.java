package Client;

import Commands.Command;
import Common.Settings;
import Common.TCPUnit;
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

public class TCPClient extends TCPUnit {

    //region Поля
    private CommandReaderClient commandReader;
    private final String host;
    private SocketChannel socketChannel;
    private User currentUser;
    /**
     * Задержка перед повторной отправкой запросов
     */
    private final int msSleepTimeout = 5000;
    /**
     * Таймаут ожидания сервера
     */
    private final int msTimeout = 5000;
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
        data.user = this.currentUser;
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
            if (selector.select(this.msTimeout) == 0) {
                Print("Тайм-аут: сервер недоступен или не отвечает");
                Thread.sleep(this.msSleepTimeout);
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
                Thread.sleep(this.msSleepTimeout);
                continue;
            }
            try {
                System.out.println("Введите команду или 'exit', чтобы выйти:");
                String commandName = scanner.nextLine().trim();
                String[] words = commandName.split("\\s+");
                if (words.length == 1) {
                    if (commandName.equals(Command.Titles.exit))
                        return;
                    Send(this.socketChannel, (Data) this.commandReader.Execute(commandName, new Object[]{}));
                }
                if (words.length > 1) {
                    String[] params = new String[words.length - 1];
                    System.arraycopy(words, 1, params, 0, words.length - 1);
                    Send(this.socketChannel, (Data) this.commandReader.Execute(words[0], params));
                }
                Object serverResponse = Receive(socketChannel);
                if (serverResponse != null && serverResponse.getClass() == User.class) {
                    this.currentUser = (User) serverResponse;
                    System.out.println("Вы авторизованы на сервере!");
                } else {
                    Print("Получен ответ от сервера:");
                    Print(serverResponse);
                }

            } catch (Exception ex) {
                this.Print(ex);
            }

        }
        socketChannel.close();

    }

    //endregion

}
