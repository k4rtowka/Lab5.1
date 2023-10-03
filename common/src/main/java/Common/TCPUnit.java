package Common;

import Commands.CommandHelp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.SocketChannel;
import java.util.Scanner;


/**
 * Базовый класс TCP объекта
 */
public class TCPUnit {
    //region Поля
    public InputStream inputStream;
    /**
     * Сканер для чтения команд из консоли.
     */
    public Scanner scanner;
    public int port;
    public boolean isStarted;
    public boolean isDebug;
    public CommandHelp commandHelp = new CommandHelp(null);
    public UserInfo currentUserInfo;

    //region SQL настройки
    public static String DB_URL = "jdbc:postgresql://localhost:5432/DBMarines";
    public static String DB_USERNAME = "postgres";
    public static String DB_PASSWORD = "postgres";
    //endregion

    //endregion

    //region Конструкторы
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
    //endregion

    //region Методы

    /**
     * Метод для проверки наличия файла и его создания при отсутствии.
     *
     * @param fileName Имя файла.
     * @throws IOException В случае ошибок ввода/вывода.
     */
    public void CheckFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("Файл " + fileName + " уже существует.");
        } else {
            if (file.createNewFile()) {
                System.out.println("Файл " + fileName + " был успешно создан.");
            } else {
                System.out.println("Невозможно создать файл " + fileName);
            }
        }
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
    //endregion
}
