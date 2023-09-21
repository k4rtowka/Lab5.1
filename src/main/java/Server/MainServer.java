package Server;

import Client.TCPClient;

public class MainServer {
    public static void main(String[] args) {
        try {
            TCPServer server = new TCPServer(System.in, 8080);
            server.Start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
