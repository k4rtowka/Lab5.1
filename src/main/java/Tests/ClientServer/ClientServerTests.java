package Tests.ClientServer;

import Client.TCPClient;
import Server.TCPServer;
import Tests.Common.CommonTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ClientServerTests extends CommonTest {

    //SCRIPT_PATH=./src/main/java/Scipts/
    @Test
    public void TestSendHelpCommand() throws InterruptedException {
        int port =this.GetRandomRegisteredPort();
        Thread serverThread = this.CreateServer("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("help\nshow\nexit\n", port);

        serverThread.join();
        clientThread.join();
    }

    @Test
    public void TestExecuteOneScript() throws InterruptedException {
        int port = 8080;
        //execute_script scriptSimple.txt
        Thread serverThread = this.CreateServer("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("execute_script scriptSimple.txt\nexit\n", port);

        serverThread.join();
        clientThread.join();
    }

    @Test
    public void TestExecuteMultiScripts() throws InterruptedException {
        int port = 8080;
        Thread serverThread = this.CreateServer("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("execute_script scriptInsert.txt\nexecute_script scriptSimple.txt\nexit\n", port);

        serverThread.join();
        clientThread.join();
    }

    @Test
    public void TestExecuteRecursiveScripts() throws InterruptedException {
        int port = 8080;
        Thread serverThread = this.CreateServer("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("execute_script scriptRecursive.txt\nexit\n", port);

        serverThread.join();
        clientThread.join();
    }

    @Test
    public void TestExecuteUpdateScripts() throws InterruptedException {
        int port = 8080;
        Thread serverThread = this.CreateServer("wait 1000\nexit\n", port);
        Thread clientThread = this.CreateClient("execute_script scriptInsertUpdate.txt\nexit\n", port);

        serverThread.join();
        clientThread.join();
    }
}
