package Tests.Common;

import Client.TCPClient;
import Server.TCPServer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

public class CommonTest {


    protected int GetRandomRegisteredPort() {
        final int MIN_REGISTERED_PORT = 1024;
        final int MAX_REGISTERED_PORT = 49151;
        Random random = new Random();
        return random.nextInt((MAX_REGISTERED_PORT - MIN_REGISTERED_PORT) + 1) + MIN_REGISTERED_PORT;
    }

    protected Thread CreateServer(String commands, int port) {
        Thread serverThread = new Thread(() -> {
            try {
                InputStream in;
                if (commands != null && commands.length() > 0) {
                    in = new ByteArrayInputStream(commands.getBytes());

                } else {
                    in = System.in;
                }
                TCPServer server = new TCPServer(in, port);
                server.Start();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        serverThread.start();
        return serverThread;
    }

    protected Thread CreateClient(String commands, int port) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Thread clientThread = new Thread(() -> {
            try {
                InputStream in = new ByteArrayInputStream(commands.getBytes());
                TCPClient client = new TCPClient(in, "localhost", port);
                client.Start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        clientThread.start();
        return clientThread;
    }
}
