package Tests.Common;

import Client.TCPClient;
import Models.CollectionManagerToFile;
import Models.CollectionManagerToSQL;
import Server.TCPServerMultiThread;
import Server.TCPServerOneThread;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Random;

public class CommonTest {

    protected int GetRandomRegisteredPort() {
        final int MIN_REGISTERED_PORT = 1024;
        final int MAX_REGISTERED_PORT = 49151;
        Random random = new Random();
        return random.nextInt((MAX_REGISTERED_PORT - MIN_REGISTERED_PORT) + 1) + MIN_REGISTERED_PORT;
    }

    protected Thread CreateOneThreadServer(String commands, int port) {
        Thread serverThread = new Thread(() -> {
            try {
                InputStream in = getInputStreamForCommands(commands);
                TCPServerOneThread server = new TCPServerOneThread(in, port);
                server.Start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
        return serverThread;
    }

    protected Thread CreateMultiThreadServerFromFile(String commands, int port) {
        Thread serverThread = new Thread(() -> {
            try {
                InputStream in = getInputStreamForCommands(commands);
                TCPServerMultiThread server = new TCPServerMultiThread(new CollectionManagerToFile("data.xml"), in, port);
                server.Start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
        return serverThread;
    }

    protected Thread CreateMultiThreadServerFromSQL(String commands, int port) {
        final String DB_URL = "jdbc:postgresql://localhost:5432/DBMarines";
        final String DB_USERNAME = "postgres";
        final String DB_PASSWORD = "postgres";
        final int idUser = 14;
        Thread serverThread = new Thread(() -> {
            try {
                InputStream in = getInputStreamForCommands(commands);
                TCPServerMultiThread server = new TCPServerMultiThread(new CollectionManagerToSQL(DB_URL, DB_USERNAME, DB_PASSWORD, idUser), in, port);
                server.Start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
        return serverThread;
    }

    protected Thread CreateClient(String commands, int port) {
        Thread clientThread = new Thread(() -> {
            try {
                //InputStream in = getInputStreamForCommands(commands);
                InputStream in = new StringArrayInputStream(commands.split("\n"));
                TCPClient client = new TCPClient(in, "localhost", port);
                client.Start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        clientThread.start();
        return clientThread;
    }

    private InputStream getInputStreamForCommands(String commands) throws Exception {
        if (commands != null && commands.length() > 0) {
            PipedOutputStream out = new PipedOutputStream();
            PipedInputStream in = new PipedInputStream(out);
            out.write(commands.getBytes());
            out.close();
            return in;
        } else {
            return System.in;
        }
    }
}
