package Tests.ClientServer;

import Tests.Common.CommonTest;
import org.junit.jupiter.api.Test;

public class OneClientServerMultiThreadTestsFromSQL extends CommonTest {
    @Test
    public void TestSendHelpCommandSQL_Unauthorized() throws InterruptedException {
        int port = this.GetRandomRegisteredPort();
        Thread serverThread = this.CreateMultiThreadServerFromSQL("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("help\nshow\nexit", port);

        serverThread.join();
        clientThread.join();
    }

    @Test
    public void TestExecuteScriptSQL_Unauthorized() throws InterruptedException {
        int port = this.GetRandomRegisteredPort();
        Thread serverThread = this.CreateMultiThreadServerFromSQL("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("execute_script /Users/danek/Documents/Java/Lab5.1/src/main/java/SciptsToExecute/scriptAuthorizeSimple.txt\nexit", port);

        serverThread.join();
        clientThread.join();
    }

    @Test
    public void TestSendHelpCommandSQL_Authorized() throws InterruptedException {
        int port = this.GetRandomRegisteredPort();
        Thread serverThread = this.CreateMultiThreadServerFromSQL("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("login\nUser1\n12345678\nhelp\nshow\nexit", port);

        serverThread.join();
        clientThread.join();
    }


}
