package Tests.ClientServer;

import Tests.Common.CommonTest;
import org.junit.jupiter.api.Test;

public class TwoClientServerTests extends CommonTest {
    @Test
    public void TestSendHelpCommand() throws InterruptedException {
        int port =this.GetRandomRegisteredPort();
        Thread serverThread = this.CreateOneThreadServer("wait 2000\nexit\n", port);
        Thread client1Thread = this.CreateClient("help\nexit\n", port);
        Thread client2Thread = this.CreateClient("show\nexit\n", port);

        serverThread.join();
        client1Thread.join();
        client2Thread.join();
    }
}
