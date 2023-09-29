import Client.TCPClient;
import Server.TCPServerOneThread;

public class Main {

    public static void main(String[] args) {
        try {
//            String path = System.getenv("SAVE_PATH");
//            CollectionManager collectionManager = new CollectionManager(path);
//            CommandReader inputReader = new CommandReader(collectionManager, System.in);
//            inputReader.Start();
            TCPServerOneThread server = new TCPServerOneThread(System.in,8080);
            server.Start();
            TCPClient client = new TCPClient();
            client.Start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}




