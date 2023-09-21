package Tests.Server;

import static org.junit.jupiter.api.Assertions.*;

import Tests.Common.CommonTest;
import org.junit.jupiter.api.Test;

public class ServerTests extends CommonTest {
    @Test
    void TestStartAndExit() {
        try {
            Thread serverThread = CreateServer("exit\n", 8080);
            serverThread.join();
            assertEquals(serverThread.getState(), Thread.State.TERMINATED);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestWait() {
        try {
            int milliseconds = 1000;
            long startTime = System.currentTimeMillis();
            Thread serverThread = CreateServer(String.format("wait %d\nexit\n", milliseconds), 8080);
            serverThread.join();
            assertEquals(serverThread.getState(), Thread.State.TERMINATED);
            long endTime = System.currentTimeMillis();
            // Продолжительность в миллисекундах
            long duration = endTime - startTime;
            assertTrue(duration >= milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
