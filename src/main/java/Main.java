import Client.TCPClient;
import Commands.CommandReader;
import IO.InputReader;
import Models.CollectionManager;
import Server.TCPServer;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
//            String path = System.getenv("SAVE_PATH");
//            CollectionManager collectionManager = new CollectionManager(path);
//            CommandReader inputReader = new CommandReader(collectionManager, System.in);
//            inputReader.Start();
            TCPServer server = new TCPServer(8080);
            server.Start();
            TCPClient client = new TCPClient();
            client.Start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}




