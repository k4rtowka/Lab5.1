package Tests;

import Client.TCPClient;
import Server.TCPServer;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ClientServerTests {
    private Thread CreateServer() {
        Thread serverThread = new Thread(() -> {
            try {
                TCPServer server = new TCPServer();
                server.Start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
        return serverThread;
    }

    private Thread CreateClient(String commands) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Thread clientThread = new Thread(() -> {
            try {
                InputStream in = new ByteArrayInputStream(commands.getBytes());
                TCPClient client = new TCPClient(in, "localhost", 8080);
                client.Start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        clientThread.start();
        return clientThread;
    }

    @Test
    public void TestSendHelpCommand() throws InterruptedException {
        Thread serverThread = CreateServer();

        Thread clientThread = CreateClient("insert\nObjectScript\n100\n100\n100\n1\n1\n1\nChapterScript\n1\nhelp\nshow\nexit\n");

        serverThread.join();
        clientThread.join();
    }

}
