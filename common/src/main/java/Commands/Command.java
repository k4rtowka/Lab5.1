package Commands;

import Common.Strings;
import Models.CollectionManager;

import java.io.Serializable;

/**
 * Базовый класс команды
 */
public abstract class Command implements Serializable {

    //region Свойства для команд: имена и описания
    public static class Titles {
        public static String help = "help";
        public static String info = "info";
        public static String show = "show";
        public static String insert = "insert";
        public static String update = "update";
        public static String removeKey = "remove_key";
        public static String clear = "clear";
        public static String save = "save";
        public static String executeScript = "execute_script";
        public static String exit = "exit";
        public static String wait = "wait";
        public static String removeLower = "remove_lower";
        public static String replaceIfLower = "replace_if_lower";
        public static String removeGreaterKey = "remove_greater_key";
        public static String countByHeartCount = "count_by_heart_count";
        public static String filterByCategory = "filter_by_category";
        public static String printDescending = "print_descending";
    }

    public static class Descriptions {
        public static String help = "вывести справку по доступным командам";
        public static String info = "вывести в стандартный поток вывода информацию о коллекции";
        public static String show = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
        public static String insert = "добавить новый элемент в коллекцию";
        public static String update = "обновить объект с указанным ID";
        public static String removeKey = "удалить объект с указанным ID";
        public static String clear = "очистить коллекцию";
        public static String save = "сохранить коллекцию в файл";
        public static String executeScript = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
        public static String exit = "завершить программу (без сохранения в файл)";
        public static String wait = "поставить текущий поток на паузу на указанное число миллисекунд";
        public static String removeLower = "удалить из коллекции все элементы, меньшие, чем заданный";
        public static String replaceIfLower = "заменить значение по ключу, если новое значение меньше старого";
        public static String removeGreaterKey = "удалить из коллекции все элементы, ключ которых превышает заданный";
        public static String countByHeartCount = "вывести количество элементов, значение поля heartCount которых равно заданному";
        public static String filterByCategory = "вывести элементы, значение поля category которых равно заданному";
        public static String printDescending = "вывести элементы коллекции в порядке убывания";
    }
    //endregion

    //region Поля
    /**
     * Название команды
     */
    private String name;
    /**
     * Описание
     */
    private String description;
    /**
     * Объект для управления коллекцией объектов
     */
    protected transient CollectionManager collectionManager;

    private Integer expectedParamsCount;
    //endregion

    //region Конструкторы
    public Command(CollectionManager collectionManager) {
        this.name = null;
        this.description = null;
        this.collectionManager = collectionManager;
    }

    public Command(String name, CollectionManager collectionManager) {
        this.name = name;
        this.collectionManager = collectionManager;
    }

    public Command(String name, String description, CollectionManager collectionManager, int expectedParamsCount) {
        this.name = name;
        this.description = description;
        this.collectionManager = collectionManager;
        this.expectedParamsCount = expectedParamsCount;
    }
    //endregion

    //region Методы


    @Override
    public String toString() {
        return String.format("%s - %s", this.name, this.description);
    }

    /**
     * Проверяет входные параметры на корректность
     *
     * @param params              входные параметры
     * @param expectedParamsCount ожидаемое число входных параметров
     * @return <b>true</b> если количество параметров правильное, <b>false</b> если неправильно.
     * @throws Exception если неправильно число параметров
     */
    public boolean CheckParams(Object[] params, int expectedParamsCount) throws Exception {
        if (expectedParamsCount == 0) {
            if (params == null || params.length == 0)
                return true;
            throw new Exception(Strings.Errors.Commands.expectingNoParams);
        }
        if (expectedParamsCount < 0)
            throw new Exception(Strings.Errors.Commands.expectingPositiveParamsCount);
        if (params == null && expectedParamsCount > 0)
            throw new Exception(String.format("Не переданы параметры, ожидаемое число параметров %d", expectedParamsCount));
        if (expectedParamsCount != params.length)
            throw new Exception(String.format("Ожидаемое число параметров %d, переданное число параметров %d", expectedParamsCount, params.length));
        return true;
    }

    public boolean CheckType(Object param, Class<?> expectedType) throws Exception {
        if (param == null) {
            throw new Exception(Strings.Errors.Commands.expectingNotNull);
        }

        if (expectedType.getSimpleName().equals("Integer")) {
            try {
                int i = Integer.parseInt(param.toString());
                return true;
            } catch (Exception e) {
                throw new Exception(String.format("Ожидался тип %s, но получен %s!",
                        expectedType.getSimpleName(), param.getClass().getSimpleName()));
            }
        }
        if (!expectedType.isInstance(param)) {
            throw new Exception(String.format("Ожидался тип %s, но получен %s!",
                    expectedType.getSimpleName(), param.getClass().getSimpleName()));
        }

        return true;
    }


    /**
     * Возвращает имя команды
     *
     * @return название команды
     */
    public String getName() {
        return this.name;
    }

    /**
     * Устанавливает имя команды
     *
     * @param name название команды
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Выполняет команду с хранилищем объектов
     * и заносит запись в лог
     *
     * @param params параметры команды
     * @return результат выполнения команды, true - если элемент добавлен
     */
    public Object Execute(Object[] params) throws Exception {
        if (this.collectionManager == null)
            throw new Exception("Не объект управления коллекцией отсутствует");
        if (this.CheckParams(params, this.expectedParamsCount)) {
            return this.execute(params);
        }
        return null;
    }

    /**
     * Выполняет команду с указанным параметром.
     *
     * @param param параметр команды
     * @return результат выполнения команды
     * @throws Exception если произошла ошибка при выполнении команды
     */
    public Object Execute(Object param) throws Exception {
        return this.Execute(new Object[]{param});
    }

    /**
     * Выполняет команду без параметров
     *
     * @return результат выполнения команды
     * @throws Exception если произошла ошибка при выполнении команды
     */
    public Object Execute() throws Exception {
        return this.Execute(null);
    }


    /**
     * Выполняет команду с хранилищем объектов
     *
     * @param params параметры команды
     * @return результат выполнения команды
     */
    protected abstract Object execute(Object[] params) throws Exception;
    //endregion

}
