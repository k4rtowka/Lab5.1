package Server;

import Models.CollectionManager;
import Models.Data;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class TCPServer {
    //region Поля
    private Scanner scanner;
    private Map<SocketAddress, Integer> clientAddresses;
    private ServerSocketChannel serverSocketChannel;
    private String dataFilePath;
    private boolean isStarted = false;
    private CollectionManager collectionManager;
    private CommandReaderServer commandReader;
    private Selector selector;
    private int port;
    //endregion

    //region Конструкторы
    public TCPServer(int port) {
        try {
            this.port = port;
            this.dataFilePath = "data.xml";
            this.scanner = new Scanner(System.in);
            this.CheckFile(this.dataFilePath);
            this.collectionManager = new CollectionManager(this.dataFilePath);
            this.commandReader = new CommandReaderServer(this.collectionManager, System.in);
            this.clientAddresses = Collections.synchronizedMap(new HashMap<>());
        } catch (Exception ex) {
            this.Print(ex.getMessage());
        }
    }

    public TCPServer() {
        this(8080);
    }
    //endregion

    //region Методы

    /**
     * Выводит указанный объект в консоль.
     *
     * @param object Объект для вывода.
     */
    void Print(Object object) {
        System.out.println(object);
    }

    /**
     * Метод для проверки наличия файла и его создания при отсутствии.
     *
     * @param fileName Имя файла.
     * @throws IOException В случае ошибок ввода/вывода.
     */
    public void CheckFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("Файл " + fileName + " уже существует.");
        } else {
            if (file.createNewFile()) {
                System.out.println("Файл " + fileName + " был успешно создан.");
            } else {
                System.out.println("Невозможно создать файл " + fileName);
            }
        }
    }

    private Object Receive(SocketChannel clientAddress, Data data) {
        try {
            Print(String.format("Получена команда от клиента %s(%s):\n%s", clientAddresses.get(clientAddress.getRemoteAddress()), clientAddress, data));
            return this.commandReader.Execute(data.command.getName(), data.data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void Send(SocketChannel clientChannel, Object data) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);

        objStream.writeObject(data);
        objStream.flush();

        ByteBuffer sendBuffer = ByteBuffer.wrap(byteStream.toByteArray());
        while (sendBuffer.hasRemaining()) {
            clientChannel.write(sendBuffer);
        }
    }

    public void Start() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(this.port));

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            Print("Сервер запущен...");

            ByteBuffer buffer = ByteBuffer.allocate(16384);
            while (!isStarted) {
                //region Чтение команд с клавиатуры
                if (System.in.available() > 0) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String input = reader.readLine();
                    switch (input) {
                        case "exit": {
                            System.out.println("Программа завершена");
                            isStarted = false;
                            return;
                        }
                        case "save": {
                            collectionManager.save();
                            System.out.println("Данные сохранены");
                        }
                    }
                }
                //endregion
                //region Обработка сообщений от клиентов
                if (selector.selectNow() == 0) continue;
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                synchronized (clientAddresses) { // Added synchronization due to the synchronizedMap
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isValid() && key.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            SocketChannel client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            clientAddresses.put(client.getRemoteAddress(), clientAddresses.size() + 1);
                        } else if (key.isValid() && key.isReadable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            buffer.clear();
                            int bytesRead = client.read(buffer);
                            if (bytesRead > 0) {
                                try {
                                    ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer.array());
                                    ObjectInputStream objStream = new ObjectInputStream(byteStream);
                                    Data clientData = (Data) objStream.readObject();
                                    Object result = Receive(client, clientData);
                                    Send(client, result);
                                } catch (StreamCorruptedException ex) {
                                    // Explanation for the sleep, if necessary
                                    Thread.sleep(100);
                                }
                            }
                        }
                        keyIterator.remove();
                    }
                }
                //endregion
            }
            Print("Остановка сервера...");
        } catch (IOException ex) {
            System.out.println("Host is busy.Try Later");
            // Consider logging the exception, not just exiting
        } catch (Exception e) {
            // Log or handle other exceptions if needed
        } finally {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                System.out.println("Error closing server socket channel.");
            }
        }
    }
    //endregion
}
