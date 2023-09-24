package Server;

public class MainServer {
    public static void main(String[] args) {
        try {
            TCPServer server = new TCPServer(System.in, 8098);
            server.Start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
