package Tests.ClientServer;

import Tests.Common.CommonTest;
import org.junit.jupiter.api.Test;

public class OneClientServerMultiThreadTests extends CommonTest {
    @Test
    public void TestSendHelpCommand() throws InterruptedException {
        int port = this.GetRandomRegisteredPort();
        Thread serverThread = this.CreateMultiThreadServer("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("help\nshow\nexit\n", port);

        serverThread.join();
        clientThread.join();
    }
}
