package Server;

import Client.TCPClient;

public class MainServer {
    public static void main(String[] args) {
        try {
            TCPServer server = new TCPServer();
            server.Start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
