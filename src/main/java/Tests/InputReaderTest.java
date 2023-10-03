package Tests;

import IO.InputReader;
import Models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

enum TestEnum {
    FIRST_OPTION,
    SECOND_OPTION,
    THIRD_OPTION
}

class InputReaderTest {
    //region Поля
    private Random random = new Random();
    private static final String TEST_FILENAME = "test.json";
    private static final int ITEMS_COUNT = 100;
    CollectionManagerToFile manager;
    InputReader reader;
    //endregion

    @BeforeEach
    void setUp() throws Exception {
        this.manager = new CollectionManagerToFile(TEST_FILENAME);
        this.reader = new InputReader(this.manager, System.in, true);
    }

    @AfterEach
    public void RestoreSystemInStream() {
        System.setIn(System.in);
        this.reader = new InputReader(this.manager, System.in, true);
    }

    private void SetSystemInStream(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        this.reader = new InputReader(this.manager, System.in, true);
    }

    //region Тесты вывода значений
    @ParameterizedTest
    @ValueSource(strings = {"First", " Second", " ", " ABf ", "", "123 Fasd gds"})
    void printMultipleValues(String message) { //Работает если в классе InputReader параметр isShowPrompt = true
        String consoleOutput = "nope";
        PrintStream originalOut = System.out;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(100);
             PrintStream capture = new PrintStream(outputStream)) {
            System.setOut(capture);
            reader.setShowPrompt(true);
            reader.Print(message);
            capture.flush();
            consoleOutput = outputStream.toString();
            System.setOut(originalOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(message + "\n", consoleOutput);
    }

    @Test
    void printEnumValues() {
        String expectedOutput = "1. FIRST_OPTION\n2. SECOND_OPTION\n3. THIRD_OPTION\n";

        // Перехватываем вывод на консоль
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Вызываем метод
        reader.PrintEnumValues(TestEnum.class);

        // Возвращаем стандартный вывод
        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString());
    }
    //endregion

    //region Тесты получения примитивов из потока
    @Test
    public void testGetStringValue() {
        String expectedValue = "testString";
        SetSystemInStream(expectedValue); // подменяем стандартный ввод
        String actualValue = reader.GetValue("Enter string: ", String.class, false);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testGetIntegerValue() {
        String expectedValue = "12345";
        SetSystemInStream(expectedValue); // подменяем стандартный ввод
        Integer result = reader.GetValue("Enter integer: ", Integer.class, false);
        assertEquals(Integer.valueOf(expectedValue), result);
    }

    @Test
    public void testGetDoubleValue() {
        String expectedValue = "123.456";
        SetSystemInStream(expectedValue); // подменяем стандартный ввод
        Double result = reader.GetValue("Enter double: ", Double.class, false);
        assertEquals(Double.valueOf(expectedValue), result);
    }

    @Test
    public void testGetFloatValue() {
        String expectedValue = "123.45";
        SetSystemInStream(expectedValue); // подменяем стандартный ввод
        Float result = reader.GetValue("Enter float: ", Float.class, false);
        assertEquals(Float.valueOf(expectedValue), result);
    }

    @Test
    public void testGetLongValue() {
        String expectedValue = "1234567890";
        SetSystemInStream(expectedValue);
        Long result = reader.GetValue("Enter long: ", Long.class, false);
        assertEquals(Long.valueOf(expectedValue), result);
    }

    @Test
    public void testGetBooleanValue() {
        String expectedValue = "true";
        SetSystemInStream(expectedValue);
        Boolean result = reader.GetValue("Enter boolean: ", Boolean.class, false);
        assertTrue(result);
    }

    @Test
    public void testGetInvalidTypeValue() {
        assertThrows(IllegalArgumentException.class, () -> reader.GetValue("Some message", Character.class, false));
    }
    //endregion

    //region Тесты получения сложных объектов

    //region Enum
    @Test
    void getEnumValue_ValidNumberInput_ReturnsCorrectEnum() {
        // Пользователь вводит "2", ожидается SECOND_OPTION
        SetSystemInStream("2");
        TestEnum result = reader.GetEnumValue(TestEnum.class, false);
        assertEquals(TestEnum.SECOND_OPTION, result);
    }

    @Test
    void getEnumValue_ValidNameInput_ReturnsCorrectEnum() {
        // Пользователь вводит "FIRST_OPTION", ожидается FIRST_OPTION
        SetSystemInStream("FIRST_OPTION");
        TestEnum result = reader.GetEnumValue(TestEnum.class, false);
        assertEquals(TestEnum.FIRST_OPTION, result);
    }

    @Test
    void getEnumValue_InvalidNumberInput_PromptsAgain() {
        // Пользователь вводит "5", затем "1", ожидается FIRST_OPTION
        SetSystemInStream("5\n1");
        TestEnum result = reader.GetEnumValue(TestEnum.class, false);
        assertEquals(TestEnum.FIRST_OPTION, result);
    }

    @Test
    void getEnumValue_InvalidNameInput_PromptsAgain() {
        // Пользователь вводит "INVALID_NAME", затем "SECOND_OPTION", ожидается SECOND_OPTION
        SetSystemInStream("INVALID_NAME\nSECOND_OPTION");
        TestEnum result = reader.GetEnumValue(TestEnum.class, false);
        assertEquals(TestEnum.SECOND_OPTION, result);
    }
    //endregion

    //region Chapter
    @Test
    void getChapterValue_ValidInput_ReturnCorrectChapter() {
        SetSystemInStream("First\n2");
        Chapter testChapter = reader.GetChapter();
        assertEquals("First", testChapter.getName());
        assertEquals(2, testChapter.getMarinesCount());
    }

    @Test
    void getChapterValue_InvalidInput_PromptsAgain() {
        SetSystemInStream("First\n1001\nFirst\n2");
        Chapter testChapter = reader.GetChapter();
        assertEquals("First", testChapter.getName());
        assertEquals(2, testChapter.getMarinesCount());
    }
    //endregion

    //region Coordinates
    @Test
    void getCoordinatesValue_ValidXInput_ReturnCorrectCoordinates() {
        SetSystemInStream("4\n2");
        Coordinate coordinates = reader.GetCoordinates();
        assertEquals(4.0, coordinates.getX());
        assertEquals(2, coordinates.getY());
    }

    @Test
    void getCoordinatesValue_InvalidXInput_PromptsAgain() {
        SetSystemInStream("-1000\n4\n5.0\n2");
        Coordinate coordinates = reader.GetCoordinates();
        assertEquals(5.0, coordinates.getX());
        assertEquals(2, coordinates.getY());
    }
    //endregion

    //region SpaceMarine
    @Test
    void getSpaceMarine() {
        try {
            SetSystemInStream("Maren\n4.0\n2\n100\n2\n1\nCHAIN_SWORD\nfirst\n2");
            SpaceMarine spaceMarine = reader.GetSpaceMarine();
            assertEquals("Maren", spaceMarine.getName());
            assertEquals(new Coordinate(4.0, 2).toString(), spaceMarine.getCoordinates().toString());
            assertEquals(100, spaceMarine.getHealth());
            assertEquals(2, spaceMarine.getHeartCount());
            assertEquals(Category.DREADNOUGHT, spaceMarine.getCategory());
            assertEquals(WeaponType.CHAIN_SWORD, spaceMarine.getWeaponType());
            assertEquals(new Chapter("first", 2).toString(), spaceMarine.getChapter().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //endregion

    @Test
    public void testGetStringValueFromByteArrayInputStream() {
        String input = "Hello, World!";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        reader = new InputReader(manager, inputStream, false);

        String result = reader.GetValue("Enter a string: ", String.class, false);

        assertEquals("Hello, World!", result);
    }

    @Test
    public void testGetIntValueFromByteArrayInputStream() {
        String input = "42";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        reader = new InputReader(manager, inputStream, false);

        Integer result = reader.GetValue("Enter an integer: ", Integer.class, false);

        assertEquals(42, result);
    }

    @Test
    public void testGetEnumValueFromByteArrayInputStream() {
        String input = "POWER_BLADE";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        reader = new InputReader(manager, inputStream, false);

        WeaponType result = reader.GetEnumValue(WeaponType.class, false);

        assertEquals(WeaponType.POWER_BLADE, result);
    }

    @Test
    public void testGetChapterFromByteArrayInputStream() {
        String nameInput = "ChapterName";
        String countInput = "42";
        InputStream inputStream = new ByteArrayInputStream((nameInput + "\n" + countInput).getBytes());
        reader = new InputReader(manager, inputStream, false);

        Chapter result = reader.GetChapter();

        assertEquals(nameInput, result.getName());
        assertEquals(42, result.getMarinesCount());
    }

    @Test
    public void testGetCoordinatesFromByteArrayInputStream() {
        String xInput = "3.14";
        String yInput = "42";
        InputStream inputStream = new ByteArrayInputStream((xInput + "\n" + yInput).getBytes());
        reader = new InputReader(manager, inputStream, false);

        Coordinate result = reader.GetCoordinates();

        assertEquals(3.14, result.getX());
        assertEquals(42, result.getY());
    }

    @Test
    public void testGetValueWithRetryFromByteArrayInputStream() {
        String input = "not an integer\n42";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        reader = new InputReader(manager, inputStream, false);

        Integer result = reader.GetValue("Enter an integer: ", Integer.class, false);

        assertEquals(42, result);
    }

    @Test
    public void testGetValueCanBeNullFromByteArrayInputStream() {
        String input = "\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        reader = new InputReader(manager, inputStream, false);

        String result = reader.GetValue("Enter a string (can be null): ", String.class, true);

        assertNull(result);
    }

    @Test
    public void testGetValueFromEmptyByteArrayInputStream() {
        String input = "";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        reader = new InputReader(manager, inputStream, false);

        assertThrows(NoSuchElementException.class, () -> {
            reader.GetValue("Enter a string: ", String.class, false);
        });
    }

}