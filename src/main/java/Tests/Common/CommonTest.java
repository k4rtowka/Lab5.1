package Tests.Common;

import Client.TCPClient;
import Server.TCPServer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CommonTest {
    protected Thread CreateServer(String commands, int port) {
        Thread serverThread = new Thread(() -> {
            try {
                InputStream in;
                if (commands != null && commands.length() > 0) {
                    in = new ByteArrayInputStream(commands.getBytes());

                } else {
                    in = System.in;
                }
                Thread thread = Thread.currentThread();
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
            Thread.sleep(200);
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
