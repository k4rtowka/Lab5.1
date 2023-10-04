package Server;


import Common.TCPUnit;
import Common.UserInfo;
import Commands.Command;
import Models.CollectionManager;
import Models.Data;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler extends TCPUnit {

    private final CollectionManager collectionManager;
    private final CommandReaderServer commandReader;
    private final ConcurrentHashMap<Integer, UserInfo> clientAddresses;

    public ClientHandler(CollectionManager collectionManager,
                         CommandReaderServer commandReader,
                         ConcurrentHashMap<Integer, UserInfo> clientAddresses) {
        this.collectionManager = collectionManager;
        this.commandReader = commandReader;
        this.clientAddresses = clientAddresses;
    }

    public void handleClient(Socket client) {
        try {
            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            Data clientData = (Data) input.readObject();
            if (clientData != null && clientData.getUserInfo() != null) {
                clientAddresses.put(clientData.getUserInfo().getId(), new UserInfo(clientAddresses.size() + 1));
            }

            Object result = processCommand(client, clientData);

            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(result);
            output.flush();
            output.close();
            input.close();

        } catch (Exception ex) {
            Print(ex);
        }
    }

    private Object processCommand(Socket client, Data data) {
        try {
            UserInfo currentClientInfo = null;
            if (data.getUserInfo() != null) {
                currentClientInfo = clientAddresses.get(data.getUserInfo().getId());
            }

            if (data == null || data.getCommand() == null) {
                return "Получена не существующая команда!";
            }

            if (data.getCommand().getName().equals(Command.Titles.save)) {
                return "Команда запрещена на стороне клиента";
            }

            if (currentClientInfo != null) {
                synchronized (collectionManager) {
                    return commandReader.Execute(data);
                }
            } else {
                if (data.getCommand().getName().equals(Command.Titles.login) ||
                        data.getCommand().getName().equals(Command.Titles.register) ||
                        data.getCommand().getName().equals(Command.Titles.executeScript)) {
                    synchronized (collectionManager) {
                        return commandReader.Execute(data);
                    }
                } else {
                    return "Вы не авторизованы, используйте команды register или login.";
                }
            }
        } catch (Exception e) {
            return "Ошибка обработки команды: " + e.getMessage();
        }
    }
}

