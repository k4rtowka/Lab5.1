package Client;

import Commands.Command;
import Models.Data;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class TCPClient {
    // Поля
    private Scanner scanner;
    private CommandReaderClient commandReader;
    private final String host;
    private final int port;
    private SocketChannel socketChannel;
    private boolean isStarted;

    // Конструкторы
    public TCPClient() {
        this(System.in, "localhost", 8080);
    }

    public TCPClient(InputStream inputStream, String host, int port) {
        this.scanner = new Scanner(inputStream);
        this.commandReader = new CommandReaderClient(inputStream);
        this.host = host;
        this.port = port;
    }

    // Методы
    /**
     * Выводит указанный объект в консоль.
     *
     * @param object Объект для вывода.
     */
    void Print(Object object) {
        System.out.println(object);
    }

    /**
     * Метод отправляет сериализованный объект через переданный SocketChannel.
     * @param socketChannel канал, через который будет отправлен объект
     * @param data объект, который нужно отправить
     * @throws IOException если произошла ошибка ввода-вывода при отправке данных
     */
    private void Send(SocketChannel socketChannel, Data data) throws IOException {
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
     * Метод принимает данные через переданный SocketChannel и выводит полученный ответ на консоль.
     * @param socketChannel канал, через который будут приниматься данные
     * @throws IOException если произошла ошибка ввода-вывода при чтении данных
     * @throws ClassNotFoundException если класс объекта не найден при десериализации
     */
    private void Receive(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(16384);
        Selector selector = socketChannel.provider().openSelector();

        int interestSet = SelectionKey.OP_READ;
        socketChannel.register(selector, interestSet);

        while (true) {
            if (selector.select(5000) == 0) {
                throw new SocketTimeoutException("Тайм-аут: сервер недоступен или не отвечает");
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
                    // Проверка, что данные были действительно прочитаны
                    if (bytesRead <= 0) {
                        continue;
                    }
                    buffer.flip();

                    ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer.array());
                    ObjectInputStream objStream = new ObjectInputStream(byteStream);

                   // String response = objStream.readObject() == null? "null":objStream.readObject().toString();
                    String response = objStream.readObject().toString();
                    System.out.println("Получен ответ от сервера: " + response);
                    keys.remove();
                    return;
                }
            }
        }
    }

    /**
     * Метод запускает клиент и устанавливает соединение с сервером. Читает команды с консоли, отправляет их на сервер и получает ответы.
     * @throws Exception если произошла ошибка при выполнении команды или при работе с сокетом
     */
    public void Start() throws Exception {
        this.socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        this.socketChannel.configureBlocking(false);
        this.isStarted = true;
        System.out.println("Клиент запущен...");
        while (this.isStarted) {
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

                Receive(socketChannel);
            } catch (Exception ex) {
                this.Print(ex.getMessage());
            }
        }
        socketChannel.close();

    }
}
