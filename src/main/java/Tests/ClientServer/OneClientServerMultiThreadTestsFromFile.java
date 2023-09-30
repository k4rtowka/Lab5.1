package Tests.ClientServer;

import Tests.Common.CommonTest;
import org.junit.jupiter.api.Test;

public class OneClientServerMultiThreadTestsFromFile extends CommonTest {
    @Test
    public void TestSendHelpCommand() throws InterruptedException {
        int port = this.GetRandomRegisteredPort();
        Thread serverThread = this.CreateMultiThreadServerFromFile("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("help\nshow\nexit\n", port);

        serverThread.join();
        clientThread.join();
    }



    @Test
    public void TestExecuteInsert() throws InterruptedException {
        int port = this.GetRandomRegisteredPort();;
        Thread serverThread = this.CreateMultiThreadServerFromSQL("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("execute_script scriptInsert.txt\nshow\nexit\n", port);

        serverThread.join();
        clientThread.join();
    }
}
