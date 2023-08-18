package IO;

import Models.Chapter;
import Models.Coordinates;
import Models.SpaceMarine;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

public class InputReader {

    //region Поля

    /**
     * Сканер для считывания ввода из заданного потока данных.
     */
    private Scanner scanner;

    /**
     * Показывать вывод инструкций по вводу в консоль или нет
     */
    private boolean isShowPrompt;

    /**
     * Чтение из файла или нет
     */
    private boolean isReadFromFile;
    //endregion

    //region Методы

    public void Print(String message) {
        if (this.isShowPrompt)
            System.out.println(message);
    }

    private <T> T GetValue(String message, String errorMessage, Function<String, T> parser) {
        try {
            Print(message);
            return parser.apply(scanner.nextLine());
        } catch (Exception e) {
            Print(errorMessage != null ? errorMessage : e.getMessage());
            if (this.isReadFromFile)
                return null;
            return GetValue(message, errorMessage, parser);
        }
    }

    public <T> T GetValue(String message, Class<T> typeClass) throws IllegalArgumentException {
        if (typeClass == String.class) {
            return typeClass.cast(GetValue(message, "Ошибка! Введите строку заново:", String::new));
        } else if (typeClass == Integer.class) {
            return typeClass.cast(GetValue(message, "Ошибка! Введите целое число заново:", Integer::parseInt));
        } else if (typeClass == Double.class) {
            return typeClass.cast(GetValue(message, "Ошибка! Введите число с плавающей запятой заново:", Double::parseDouble));
        } else if (typeClass == Float.class) {
            return typeClass.cast(GetValue(message, "Ошибка! Введите число с плавающей запятой заново:", Float::parseFloat));
        } else if (typeClass == Long.class) {
            return typeClass.cast(GetValue(message, "Ошибка! Введите длинное целое число заново:", Long::parseLong));
        } else if (typeClass == Boolean.class) {
            return typeClass.cast(GetValue(message, "Ошибка! Введите true или false заново:", Boolean::parseBoolean));
        }
        throw new IllegalArgumentException("Неизвестный тип данных.");
    }

    /**
     * Выводит все значения перечисления, пронумерованные списком.
     *
     * @param <E>       тип перечисления
     * @param enumClass класс перечисления
     */
    public <E extends Enum<E>> void PrintEnumValues(Class<E> enumClass) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (int i = 0; i < enumConstants.length; i++) {
            this.Print(String.format("%d. %s", i + 1, enumConstants[i].name()));
        }
    }

    /**
     * Получает значение перечисления из указанного класса перечисления на основе ввода пользователя.
     * Пользователь может ввести либо номер, либо название одного из перечислений.
     * Если указанное значение не существует в перечислении, будет запрошено повторное ввод.
     *
     * @param <E>       тип перечисления
     * @param enumClass класс перечисления
     * @return значение перечисления, введенное пользователем
     */
    public <E extends Enum<E>> E GetEnumValue(Class<E> enumClass) {
        try {
            PrintEnumValues(enumClass);
            String value = this.scanner.nextLine();
            E[] enumConstants = enumClass.getEnumConstants();
            //region Проверяем, является ли введенная строка числом
            if (value.matches("\\d+")) {
                int enumIndex = Integer.parseInt(value) - 1;
                if (enumIndex >= 0 && enumIndex < enumConstants.length) {
                    return enumConstants[enumIndex];
                }
            }
            //endregion

            //region Если введенная строка не число, сверяем ее с названиями в перечисляемом типе
            value = value.toUpperCase(Locale.ROOT);
            return Enum.valueOf(enumClass, value);
            //endregion

        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println("Некорректный ввод. Пожалуйста, введите значение из списка или его номер:");
            return this.GetEnumValue(enumClass);
        }
    }

    /**
     * Получает объект Chapter из ввода пользователя.
     *
     * @return объект Chapter
     */
    public Chapter GetChapter() {
        try {
            return new Chapter(
                    GetValue("Введите название главы: ", String.class),
                    GetValue("Введите число морпехов: ", Long.class)
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return GetChapter();
        }
    }

    /**
     * Получает объект Coordinates из ввода пользователя.
     *
     * @return объект Coordinates
     */
    public Coordinates GetCoordinates() {
        try {
            return new Coordinates(
                    GetValue("Введите координату X: ", Double.class),
                    GetValue("Введите координату Y: ", Integer.class)
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (this.isReadFromFile)
                return null;
            return GetCoordinates();
        }
    }

    /**
     * Получает объект SpaceMarine на основе ввода пользователя.
     * Значения полей запрашиваются с помощью других вспомогательных методов.
     *
     * @return объект SpaceMarine, созданный на основе ввода пользователя
     */
    public SpaceMarine GetSpaceMarine() {
        try {
            //TODO: заполнить морпеха
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            if (this.isReadFromFile)
                return null;
            return GetSpaceMarine();
        }
    }

    //endregion
}
