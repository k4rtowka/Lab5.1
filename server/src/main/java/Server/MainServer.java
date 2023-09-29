package Server;

public class MainServer {
    public static void main(String[] args) {
        try {
            TCPServerOneThread server = new TCPServerOneThread(System.in, 8080);
            server.Start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
