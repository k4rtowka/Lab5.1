package Common;

public class Strings {
    public class Messages {
        public class Collection {
            public static String emptyCollection = "Коллекция пуста!";
        }
    }

    public class Errors {
        public class Commands {
            public static String missedObjectOwnerInfo = "Не указана информация о владельце объекта!";
            public static String incorrectOwnerUserId = "Пользователь не является владельцем объекта!";
            public static String expectedTypeErrorFormat = "Ожидался тип %s, но получен %s!";
            public static String expectingNoParams = "У команды нет входных параметров!";
            public static String expectingNotNull = "Параметр не может быть null!";
            public static String expectingPositiveParamsCount = "Ожидаемое число параметров у команды не может быть меньше нуля!";
        }
    }

}
