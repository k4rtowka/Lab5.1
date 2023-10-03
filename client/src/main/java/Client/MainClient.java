package Client;

import Common.UserInfo;

public class MainClient {
    public static void main(String[] args) {
        try {
            TCPClient client = new TCPClient(System.in, "localhost", 8080);
            client.currentUserInfo = new UserInfo(14);
            client.Start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
