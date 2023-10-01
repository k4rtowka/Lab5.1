package Server;

import Commands.Command;
import Common.Settings;
import Common.TCPUnit;
import Models.CollectionManagerToFile;
import Models.Data;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServerOneThread extends TCPUnit {
    //region Поля

    private Map<SocketAddress, Integer> clientAddresses;
    private String dataFilePath;
    private CollectionManagerToFile collectionManager;
    private CommandReaderServer commandReader;
    private ServerSocket serverSocket;
    //endregion

    //region Конструкторы
    public TCPServerOneThread(InputStream inputStream, int port) {
        super(inputStream, port, false, Settings.isDebug);
        try {
            this.dataFilePath = "data.xml";
            this.CheckFile(this.dataFilePath);
            this.collectionManager = new CollectionManagerToFile(this.dataFilePath);
            this.commandReader = new CommandReaderServer(this.collectionManager, System.in);
            this.clientAddresses = Collections.synchronizedMap(new HashMap<>());
            this.commandReader.SetCurrentThread(Thread.currentThread());
        } catch (Exception ex) {
            this.Print(ex);
        }
    }

    public TCPServerOneThread() {
        this(System.in, 8080);
    }
    //endregion

    //region Методы


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
            Object result = this.commandReader.Execute(data.command.getName(), data == null ? null : data.params);
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
                    } catch (ClassNotFoundException e) {
                        Print("Error reading data from client: " + e.getMessage());
                    }
                } catch (Exception e) {
                    Print(e);
                }

                //endregion
            }

            Print("Остановка сервера...");
        } catch (IOException e) {
            Print("Host is busy. Try Later");
            Print(e);
            //e.printStackTrace();
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
