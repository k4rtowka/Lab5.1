package Server;

import Commands.Command;
import Common.Settings;
import Common.TCPUnit;
import Common.UserInfo;
import Models.CollectionManager;
import Models.CollectionManagerToSQL;
import Models.Data;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.*;

public class TCPServerMultiThread extends TCPUnit {

    private ConcurrentHashMap<Integer, UserInfo> clientAddresses;
    private ExecutorService clientHandlingPool;
    private ServerSocket serverSocket;
    private ClientHandler clientHandler;
    private CommandReaderServer commandReader;

    public TCPServerMultiThread(CollectionManager collectionManager, InputStream inputStream, int port) {
        super(inputStream, port, false, Settings.isDebug);
        this.clientAddresses = new ConcurrentHashMap<>();
        this.clientHandlingPool = Executors.newFixedThreadPool(10);
        this.commandReader = new CommandReaderServer(collectionManager, System.in);
        this.clientHandler = new ClientHandler(collectionManager, commandReader, clientAddresses);
    }

    public TCPServerMultiThread() throws Exception {
        this(new CollectionManagerToSQL(DB_URL, DB_USERNAME, DB_PASSWORD, -1), System.in, 8080);
    }

    public void Start() throws Exception {
        try {
            serverSocket = new ServerSocket(this.port);
            serverSocket.setSoTimeout(Settings.Timeouts.Server.socketTimeout);
            Print("Сервер запущен...");
            this.isStarted = true;

            while (isStarted && !Thread.currentThread().isInterrupted()) {

                // Чтение команд с клавиатуры
                if (this.inputStream.available() > 0) {
                    String line = this.scanner.nextLine().trim();
                    String[] commandItems = line.split("\\s+");
                    String commandName = commandItems[0];
                    String[] params = Arrays.stream(commandItems).skip(1).toArray(String[]::new);
                    //TODO: добавить админа
                    Object result = this.commandReader.Execute(new Data(new UserInfo(100), this.commandHelp.GetCommand(commandName), params));
                    if (commandName.equals(Command.Titles.exit)) {
                        isStarted = false;
                        break;
                    }
                    Print(result);
                }

                // Обработка сообщений от клиентов
                try {
                    Socket client = serverSocket.accept();
                    clientHandlingPool.submit(() -> clientHandler.handleClient(client));
                } catch (Exception ex) {
                    Print(ex);
                    Thread.sleep(1000);
                }
            }

            Print("Остановка сервера...");
            System.exit(0);
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
}
