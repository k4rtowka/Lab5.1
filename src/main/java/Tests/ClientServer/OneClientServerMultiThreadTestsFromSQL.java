package Tests.ClientServer;

import Tests.Common.CommonTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OneClientServerMultiThreadTestsFromSQL extends CommonTest {

    Thread serverThread = null;
    Thread clientThread = null;
    int port = 8080;

    public void Shutdown() {
        try {
            // Дайте потокам время выполниться
            Thread.sleep(2000);

            // Прерываем потоки
            serverThread.interrupt();
            clientThread.interrupt();

            // Ожидаем завершения потоков
            serverThread.join();
            clientThread.join();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    @BeforeEach
    public void Setup() {
        this.port = this.GetRandomRegisteredPort();
    }

    @Test
    public void TestSendHelpCommandSQL_Unauthorized() throws InterruptedException {
        int port = this.GetRandomRegisteredPort();
        Thread serverThread = this.CreateMultiThreadServerFromSQL("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("help\nshow\nexit", port);

        // Дайте потокам время выполниться
        Thread.sleep(2000);

        // Прерываем потоки
        serverThread.interrupt();
        clientThread.interrupt();

        // Ожидаем завершения потоков
        serverThread.join();
        clientThread.join();
    }

    @Test
    public void TestExecuteScriptSQL_Unauthorized() throws InterruptedException {
        serverThread = this.CreateMultiThreadServerFromSQL("wait 1000\nexit\n", port);
        clientThread = this.CreateClient("execute_script /Users/danek/Documents/Java/Lab5.1/src/main/java/SciptsToExecute/scriptAuthorizeSimple.txt\nexit", port);

    }

    @Test
    public void TestSendHelpCommandSQL_Authorized() throws InterruptedException {
        serverThread = this.CreateMultiThreadServerFromSQL("wait 1000\nexit\n", port);
        clientThread = this.CreateClient("login\nUser1\n12345678\nhelp\nshow\nexit\n", port);
        // Ожидаем завершения потоков
        serverThread.join();
        clientThread.join();
        //Shutdown();
    }

    @Test
    public void TestRemoveCommandSQL_Authorized() throws InterruptedException {
        serverThread = this.CreateMultiThreadServerFromSQL("wait 1000\nexit\n", port);
        clientThread = this.CreateClient("remove_key 1\nexit\n", port);
        // Ожидаем завершения потоков
        serverThread.join();
        clientThread.join();
        //Shutdown();
    }


}
