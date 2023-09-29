package Server;

import Commands.Command;
import Common.Settings;
import Common.TCPUnit;
import Models.CollectionManagerToFile;
import Models.Data;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class TCPServerMultiThread extends TCPUnit {
    //region Поля

    private ConcurrentHashMap<SocketAddress, Integer> clientAddresses;
    private ExecutorService readPool;
    private ExecutorService processPool;
    private ForkJoinPool sendPool;

    private String dataFilePath;
    private CollectionManagerToFile collectionManager;
    private CommandReaderServer commandReader;
    private ServerSocket serverSocket;
    //endregion

    //region Конструкторы
    public TCPServerMultiThread(InputStream inputStream, int port) {
        super(inputStream, port, false, Settings.isDebug);
        this.readPool = Executors.newFixedThreadPool(10);  // Fixed thread pool for reading requests
        this.processPool = Executors.newCachedThreadPool();  // Cached thread pool for processing requests
        this.sendPool = new ForkJoinPool();  // ForkJoinPool for sending responses
        this.clientAddresses = new ConcurrentHashMap<>();  // Thread-safe map
        try {
            this.dataFilePath = "data.xml";
            this.CheckFile(this.dataFilePath);
            this.collectionManager = new CollectionManagerToFile(this.dataFilePath);
            this.commandReader = new CommandReaderServer(this.collectionManager, System.in);
            this.commandReader.SetCurrentThread(Thread.currentThread());
        } catch (Exception ex) {
            this.Print(ex);
        }
    }

    public TCPServerMultiThread() {
        this(System.in, 8080);
    }
    //endregion

    //region Методы

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

    private void Send(ObjectOutputStream output, Object data) throws IOException {
        output.writeObject(data);
        output.flush();
    }

    private Object Receive(Socket client, Data data) {
        try {
            InetAddress clientAddress = client.getInetAddress();
            int clientPort = client.getPort();
            Print(String.format("Получена команда от клиента %s(%d):\n%s", clientAddress, clientPort, data));
            if (data.command == null) {
                return "Получена не существующая команда!";
            }
            if (data.command.getName().equals(Command.Titles.save)) {
                return "Команда запрещена на стороне клиента";
            }
            synchronized (collectionManager) {
                return this.commandReader.Execute(data.command.getName(), data == null ? null : data.data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void Start() throws Exception {
        try {
            serverSocket = new ServerSocket(this.port);
            serverSocket.setSoTimeout(3000);
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
                Socket client = serverSocket.accept();  // Get the client socket
                readPool.submit(() -> {
                    try {
                        InetAddress clientAddress = client.getInetAddress();
                        int clientPort = client.getPort();
                        SocketAddress clientSocketAddress = new InetSocketAddress(clientAddress, clientPort);
                        clientAddresses.put(clientSocketAddress, clientAddresses.size() + 1);

                        ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                        Data clientData = (Data) input.readObject();

                        processPool.submit(() -> {
                            Object result = Receive(client, clientData);

                            sendPool.submit(() -> {
                                try {
                                    ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                                    Send(output, result);
                                    // Move input.close() here
                                    input.close();  // Close the input stream
                                    output.close();  // Close the output stream
                                } catch (IOException e) {
                                    Print("Error sending data to client: " + e.getMessage());
                                }
                            });

                        });

                    } catch (IOException | ClassNotFoundException e) {
                        Print("Error reading data from client: " + e.getMessage());
                    }
                });

                //endregion
            }

            Print("Остановка сервера...");
        } catch (IOException e) {
            Print("Host is busy. Try Later");
            Print(e);
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                Print("Error closing server socket.");
                Print(e);
            }
        }
    }



    //endregion
}
