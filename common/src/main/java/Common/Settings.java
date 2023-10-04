package Common;

public class Settings {
    public static Boolean isDebug = false;
    public static String DB_URL = "jdbc:postgresql://localhost:5432/DBMarines";
    public static String DB_USERNAME = "postgres";
    public static String DB_PASSWORD = "postgres";

    public class Timeouts {
        public class Server {

            /**
             * Время ожидания запроса от клиента в мс.
             */
            public static int socketTimeout = 3000;
        }

        public class Client {
            /**
             * Задержка перед повторной отправкой запросов в мс.
             */
            public static int requestTimeout = 5000;
            /**
             * Таймаут ожидания сервера в мс.
             */
            public static int connectionTimeout = 5000;
        }
    }
}
