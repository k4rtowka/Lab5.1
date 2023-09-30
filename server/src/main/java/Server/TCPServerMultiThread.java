package Server;

import Commands.Command;
import Common.Settings;
import Common.TCPUnit;
import Models.*;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class TCPServerMultiThread extends TCPUnit {
    //region Поля

    private ConcurrentHashMap<Integer, ClientInfo> clientAddresses;
    private ExecutorService readPool;
    private ExecutorService processPool;
    private ForkJoinPool sendPool;

    private String dataFilePath;
    private CollectionManager collectionManager;
    private CommandReaderServer commandReader;
    private ServerSocket serverSocket;
    //endregion

    //region Конструкторы
    public TCPServerMultiThread(CollectionManager collectionManager, InputStream inputStream, int port) {
        super(inputStream, port, false, Settings.isDebug);
        this.readPool = Executors.newFixedThreadPool(10);  // Fixed thread pool for reading requests
        this.processPool = Executors.newCachedThreadPool();  // Cached thread pool for processing requests
        this.sendPool = new ForkJoinPool();  // ForkJoinPool for sending responses
        this.clientAddresses = new ConcurrentHashMap<>();  // Thread-safe map
        try {
            if (collectionManager == null)
                throw new Exception("Не передан объект для управления коллекцией!");
            this.collectionManager = collectionManager;
            this.commandReader = new CommandReaderServer(this.collectionManager, System.in);
            this.commandReader.SetCurrentThread(Thread.currentThread());
        } catch (Exception ex) {
            this.Print(ex);
        }
    }

    public TCPServerMultiThread() throws Exception {
        //this(new CollectionManagerToSQL("jdbc:postgresql://pg:2222/studs", "s388132", "RcCd,8731" , 11), System.in, 8080);
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
            ClientInfo currentClientInfo = null;
            if (data.user != null)
                currentClientInfo = this.clientAddresses.get(data.user.getId());
            InetAddress clientAddress = client.getInetAddress();
            int clientPort = client.getPort();
            Print(String.format("Получена команда от клиента %s(%d):\n%s", clientAddress, clientPort, data));
            if (data.command == null) {
                return "Получена не существующая команда!";
            }
            if (data.command.getName().equals(Command.Titles.save)) {
                return "Команда запрещена на стороне клиента";
            }
            if ((currentClientInfo != null)
                   // && currentClientInfo.isAuthorized()
            ) {
                synchronized (collectionManager) {
                    return this.commandReader.Execute(data.command.getName(), data == null ? null : data.data);
                }
            } else {
                if (data.command.getName().equals(Command.Titles.login) ||
                        data.command.getName().equals(Command.Titles.register) ||
                        data.command.getName().equals(Command.Titles.executeScript)) {
                    synchronized (collectionManager) {
                        return this.commandReader.Execute(data.command.getName(), data == null ? null : data.data);
                    }
                } else {
                    return "Вы не авторизованы, используйте команды register или login.";
                }
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
                try {
                    Socket client = serverSocket.accept();  // Get the client socket
                    readPool.submit(() -> {
                        try {
                            InetAddress clientAddress = client.getInetAddress();
                            int clientPort = client.getPort();
                            SocketAddress clientSocketAddress = new InetSocketAddress(clientAddress, clientPort);

                            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                            Data clientData = (Data) input.readObject();
                            if (clientData != null && clientData.user != null)
                                clientAddresses.put(clientData.user.getId(), new ClientInfo(clientAddresses.size() + 1));


                            processPool.submit(() -> {
                                Object result = Receive(client, clientData);
                                synchronized (clientAddresses) {
                                    if (result != null && result.getClass() == User.class) {
                                        ClientInfo clientInfo = this.clientAddresses.get(result);
                                        if (clientInfo == null)
                                            clientAddresses.put(((User) result).getId(), new ClientInfo(clientAddresses.size() + 1));
                                        clientInfo = this.clientAddresses.get(((User) result).getId());
                                        clientInfo.setAuthorized(true);
                                        clientInfo.setIdUser(((User) result).getId());
                                        clientAddresses.put(((User) result).getId(), clientInfo);
                                    }
                                }
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
                } catch (Exception ex) {
                    Print(ex);
                    Thread.sleep(1000);
                    continue;
                }
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
