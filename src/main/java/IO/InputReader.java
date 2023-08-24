package IO;

import Models.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
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

    /**
     * Объект для управления коллекцией
     */
     private CollectionManager collectionManager;
    //endregion

    //region Сеттеры

    public void setShowPrompt(boolean showPrompt) {
        isShowPrompt = showPrompt;
    }

    //endregion

    //region Конструктор

    /**
     * Конструктор, который инициализирует новый объект InputReader с CollectionManager и устанавливает
     * переменную-счетчик равной максимальному значению ID в CollectionManager плюс 1.
     *
     * @param collectionManager CollectionManager, который будет содержать объекты SpaceMarine.
     * @param scanner           Scanner, из которого будет считываться ввод.
     * @param isShowPrompt      флаг, указывающий, нужно ли отображать инструкции по вводу в консоль.
     */
    public InputReader(CollectionManager collectionManager, Scanner scanner, boolean isReadFromFile, boolean isShowPrompt) {
        this.isReadFromFile = isReadFromFile;
        this.collectionManager = collectionManager;
        this.scanner = scanner;
        this.isShowPrompt = isShowPrompt;
    }

    /**
     * Конструктор, который инициализирует новый объект InputReader с CollectionManager и устанавливает
     * переменную-счетчик равной максимальному значению ID в CollectionManager плюс 1.
     *
     * @param collectionManager CollectionManager, который будет содержать объекты SpaceMarine.
     * @param inputStream       InputStream, из которого будет считываться ввод.
     * @param isShowPrompt      флаг, указывающий, нужно ли отображать инструкции по вводу в консоль.
     */
    public InputReader(CollectionManager collectionManager, InputStream inputStream, boolean isShowPrompt) {
        this.isReadFromFile = inputStream instanceof FileInputStream;
        this.collectionManager = collectionManager;
        this.scanner = new Scanner(inputStream);
        this.isShowPrompt = isShowPrompt;
    }
    //endregion

    //region Методы

    /**
     * Вывод сообщения.
     *
     * @param message сообщения для вывода
     */
    public void Print(String message) {
        if (this.isShowPrompt)
            System.out.println(message);
    }

    /**
     * Получение значения.
     *
     * @param message сообщение перед получением значения
     * @param errorMessage сообщение об ошибке
     * @param parser String или <T>.parse
     * @param <T> - тип получаемого значения
     * @return правильно введенное значение
     */
    private <T> T GetValue(String message, String errorMessage, Function<String, T> parser) {
        try {
            Print(message);
            return parser.apply(this.scanner.nextLine());
        } catch (Exception e) {
            Print(errorMessage != null ? errorMessage : e.getMessage());
            if (this.isReadFromFile)
                return null;
            return GetValue(message, errorMessage, parser);
        }
    }

    /**
     * Получить значение.
     * При неправильном вводе пишется ошибка.
     *
     * @param message сообщение перед вводом значения
     * @param typeClass тип значения
     * @param <T> тип получаемого значения
     * @return правильно введенное значение
     * @throws IllegalArgumentException если получен не известный тип данных
     */
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
     * Если указанное значение не существует в перечислении, будет запрошен повторный ввод.
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

    private SpaceMarine GetSpaceMarine(SpaceMarine marine, int step) {
        try {
            if(step==1) {
                String name = GetValue("", String.class);
                marine.setName(name);
                step++;
            }
            if(step==2){
                Coordinates coordinates = GetCoordinates();
                marine.setCoordinates(coordinates);
                step++;
            }
            marine.setCreationDate(new Date());
            if(step==3){
                Integer health = GetValue("Введите здоровье: ", Integer.class);
                marine.setHealth(health);
                step++;
            }
            if(step==4){
                Long heartCount = GetValue("Введите количество сердец: ", Long.class);
                marine.setHeartCount(heartCount);
                step++;
            }
            if(step==5){
                AstartesCategory category = GetEnumValue(AstartesCategory.class);
                marine.setCategory(category);
                step++;
            }
            if(step==6){
                MeleeWeapon meleeWeapon = GetEnumValue(MeleeWeapon.class);
                marine.setMeleeWeapon(meleeWeapon);
                step++;
            }
            if(step==7){
                Chapter chapter = GetChapter();
                marine.setChapter(chapter);
                step++;
            }
            return marine;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            if (this.isReadFromFile)
                return null;
            return GetSpaceMarine(marine, step);
        }
    }

    /**
     * Получает объект SpaceMarine на основе ввода пользователя.
     * Значения полей запрашиваются с помощью других вспомогательных методов.
     *
     * @return объект SpaceMarine, созданный на основе ввода пользователя
     */
    public SpaceMarine GetSpaceMarine() {
        return GetSpaceMarine(new SpaceMarine(), 1);
       /* try {
            return new SpaceMarine(1,
                    GetValue("Введите имя: ", String.class),
                    GetCoordinates(),
                    new Date(),
                    GetValue("Введите здоровье: ", Integer.class),
                    GetValue("Введите количество сердец: ", Long.class),
                    GetEnumValue(AstartesCategory.class),
                    GetEnumValue(MeleeWeapon.class),
                    GetChapter());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            if (this.isReadFromFile)
                return null;
            return GetSpaceMarine();
        }*/
    }

    //endregion
}
