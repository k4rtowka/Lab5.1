package Common;

import java.io.InputStream;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class TCPUnit {
    public InputStream inputStream;
    /**
     * Сканер для чтения команд из консоли.
     */
    public Scanner scanner;
    public int port;
    public boolean isStarted;
    public boolean isDebug;

    public TCPUnit(InputStream inputStream, int port, boolean isStarted, boolean isDebug) {
        this.inputStream = inputStream;
        this.scanner = new Scanner(inputStream);
        this.port = port;
        this.isDebug = isDebug;
        this.isStarted = isStarted;
    }


    public TCPUnit() {
        this(System.in, 8080, false, false);
    }

    /**
     * Выводит объект в консоль
     *
     * @param object
     */
    public void Print(Object object) {
        System.out.println(object);
    }

    /**
     * Выводит ошибку в консоль, если стоит флаг отладки, выведет стек вызовов
     *
     * @param ex
     */
    public void Print(Exception ex) {
        System.out.println(ex.getMessage());
        if (this.isDebug)
            ex.printStackTrace();
    }
}
