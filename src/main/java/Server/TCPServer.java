package Server;

import Commands.CommandHelp;
import Models.CollectionManager;
import Models.Data;

import java.io.*;
import java.net.*;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.*;

public class TCPServer {
    //region Поля
    /**
     * Сканер для чтения команд из консоли.
     */
    private Scanner scanner;
    private InputStream inputStream;
    private Map<SocketAddress, Integer> clientAddresses;
    private String dataFilePath;
    private boolean isStarted = false;
    private CollectionManager collectionManager;
    private CommandReaderServer commandReader;
    private Selector selector;
    private int port;
    private ServerSocket serverSocket;
    //endregion

    //region Конструкторы
    public TCPServer(InputStream inputStream, int port) {
        try {
            this.port = port;
            this.dataFilePath = "data.xml";
            this.inputStream = inputStream;
            this.scanner = new Scanner(inputStream);
            this.CheckFile(this.dataFilePath);
            this.collectionManager = new CollectionManager(this.dataFilePath);
            this.commandReader = new CommandReaderServer(this.collectionManager, System.in);
            this.clientAddresses = Collections.synchronizedMap(new HashMap<>());
            this.commandReader.SetCurrentThread(Thread.currentThread());
        } catch (Exception ex) {
            this.Print(ex.getMessage());
        }
    }

    public TCPServer() {
        this(System.in, 8080);
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

    private Object Receive(Socket client, Data data) {
        try {
            InetAddress clientAddress = client.getInetAddress();
            int clientPort = client.getPort();
            Print(String.format("Получена команда от клиента %s(%d):\n%s", clientAddress, clientPort, data));
            if (data.command == null) {
                return "Получена не существующая команда!";
            }
            Object result = this.commandReader.Execute(data.command.getName(), data == null ? null : data.data);
            //this.commandReader.Execute(Command.Titles.save,new Object[]{});
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void Send(ObjectOutputStream output, Object data) throws IOException {
        output.writeObject(data);
        output.flush();

    }

    private void send(Object data, Socket socket) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
            output.writeObject(data);
            output.flush();
        }
    }


    public void Start() throws Exception {
        try {
            serverSocket = new ServerSocket(this.port);
            Print("Сервер запущен...");
            this.isStarted = true;

            while (isStarted && !Thread.currentThread().isInterrupted()) {
                //region Чтение команд с клавиатуры
                if (this.inputStream.available() > 0) {
                    String command = this.scanner.nextLine().trim();
                    String[] commandItems = command.split("\\s+");
                    command = commandItems[0];
                    String[] params = Arrays.stream(commandItems).skip(1).toArray(String[]::new);
                    Object result = this.commandReader.Execute(command, params);
                    Print(result);
                }
                //endregion

                //region Обработка сообщений от клиентов
                try (Socket client = serverSocket.accept()) {
                    InetAddress clientAddress = client.getInetAddress();
                    int clientPort = client.getPort();
                    SocketAddress clientSocketAddress = new InetSocketAddress(clientAddress, clientPort);
                    clientAddresses.put(clientSocketAddress, clientAddresses.size() + 1);

                    try (ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                         ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream())) {

                        Data clientData = (Data) input.readObject();
                        Object result = Receive(client, clientData);
                        Send(output, result);
                        //send(result,client);

                    } catch (ClassNotFoundException e) {
                        Print("Error reading data from client: " + e.getMessage());
                    }
                } catch (IOException e) {
                    Print("Error accepting client connection: " + e.getMessage());
                }
                //endregion
            }

            Print("Остановка сервера...");
        } catch (IOException e) {
            Print("Host is busy. Try Later");
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                Print("Error closing server socket.");
            }
        }
    }

    //endregion
}
