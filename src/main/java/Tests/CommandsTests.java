package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Commands.*;
import Common.Strings;
import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CommandsTests extends BaseTest {
    private final int size = 100;

    @BeforeEach
    public void setUp() throws Exception {
        this.InitCollection("CommandsTests.xml", size);
    }

    //region Clear
    @Test
    public void test_Clear_ExecuteWithValidParams() throws Exception {
        CommandClear clear = new CommandClear(collectionManager);
        clear.Execute();
        assertEquals(0, collectionManager.getMarines().size());
    }

    @Test
    public void test_Clear_ExecuteWithInvalidParams() throws Exception {
        CommandClear clear = new CommandClear(collectionManager);
        Integer beforeSize = this.collectionManager.GetSize();
        // Given
        Object[] params = new Object[1];
        params[0] = "someRandomValue";

        Exception exception = assertThrows(Exception.class, () -> {
            clear.Execute(params);
        });
        assertEquals(Strings.Errors.Commands.expectingNoParams, exception.getMessage());
        assertEquals(beforeSize, this.collectionManager.GetSize());
    }
    //endregion

    //region CountByHeartCount
    @Test
    public void test_CountByHeartCount_ExecuteWithValidParams() throws Exception {
        CommandCountByHeartCount countByHeartCount = new CommandCountByHeartCount(collectionManager);
        for (int i = 1; i < 4; i++) {
            Long count = 0L;
            for (SpaceMarine marine : collectionManager.getMarines().values()) {
                if (marine.getHeartCount() == i) count++;
            }
            assertEquals(count, countByHeartCount.Execute(i));
        }
    }

    @Test
    public void test_CountByHeartCount_ExecuteWithInvalidParams() throws Exception {
        CommandCountByHeartCount countByHeartCount = new CommandCountByHeartCount(collectionManager);
        // Given
        Object[] params = new Object[2];
        params[0] = "someRandomValue";
        params[1] = "someRandomValue";

        Exception exception = assertThrows(Exception.class, () -> {
            countByHeartCount.Execute(params);
        });
        assertEquals("Ожидаемое число параметров 1, переданное число параметров 2", exception.getMessage());

        exception = assertThrows(Exception.class, () -> {
            countByHeartCount.Execute(null);
        });
        assertEquals("Не переданы параметры, ожидаемое число параметров 1", exception.getMessage());


    }
    //endregion

    //region FilterByCategory
    @Test
    public void test_FilterByCategory_ExecuteWithValidParams() throws Exception {
        CommandFilterByCategory filterByCategory = new CommandFilterByCategory(collectionManager);
        for (Category category : Category.values()) {
            List<SpaceMarine> list = new ArrayList<>();
            for (SpaceMarine marine : collectionManager.getMarines().values()) {
                if (marine.getCategory() == category) list.add(marine);
            }
            assertEquals(list, filterByCategory.Execute(category));
        }
    }

    @Test
    public void test_FilterByCategory_ExecuteWithInvalidParams() {
        CommandFilterByCategory filterByCategory = new CommandFilterByCategory(collectionManager);

        Object[] params = new Object[2];
        params[0] = "Something";
        params[1] = "Something";

        Exception exception = assertThrows(Exception.class, () ->
                filterByCategory.Execute(params));
        assertEquals("Ожидаемое число параметров 1, переданное число параметров 2", exception.getMessage());

        exception = assertThrows(Exception.class, () -> filterByCategory.Execute(null));
        assertEquals("Не переданы параметры, ожидаемое число параметров 1", exception.getMessage());

        exception = assertThrows(Exception.class, () -> filterByCategory.Execute("INCORRECT_PARAM"));
        assertEquals(String.format(Strings.Errors.Commands.expectedTypeErrorFormat, Category.class.getSimpleName(), String.class.getSimpleName()),
                exception.getMessage());
    }
    //endregion

    //region Insert
    @Test
    public void test_Insert_ExecuteWithValidParams() throws Exception {
        CommandInsert insert = new CommandInsert(collectionManager);
        int beforeExecute = collectionManager.GetSize();
        SpaceMarine testMarine = GenerateRandomSpaceMarine(101);
        insert.Execute(testMarine);
        assertEquals(beforeExecute + 1, collectionManager.GetSize());
        assertEquals(0, testMarine.compareTo(collectionManager.getMarines().get(101)));
    }

    @Test
    public void test_Insert_ExecuteWithInvalidParams() throws Exception {
        CommandInsert insert = new CommandInsert(collectionManager);
        Object[] params = new Object[2];
        params[0] = "something";
        params[1] = "something";

        Exception exception = assertThrows(Exception.class,
                () -> insert.Execute(params));
        assertEquals("Ожидаемое число параметров 1, переданное число параметров 2", exception.getMessage());

        exception = assertThrows(Exception.class,
                () -> insert.Execute(null));
        assertEquals("Не переданы параметры, ожидаемое число параметров 1", exception.getMessage());

        exception = assertThrows(Exception.class, () -> insert.Execute("INCORRECT_PARAM"));
        assertEquals(String.format(Strings.Errors.Commands.expectedTypeErrorFormat, SpaceMarine.class.getSimpleName(), String.class.getSimpleName()),
                exception.getMessage());
    }
    //endregion

    //region RemoveGreaterKey
    @Test
    public void test_RemoveGreaterKey_ExecuteWithValidParams() throws Exception {
        CommandRemoveGreaterKey removeGreaterKey = new CommandRemoveGreaterKey(collectionManager);
        removeGreaterKey.Execute(size / 2);
        assertEquals(size / 2, collectionManager.GetSize());
    }

    @Test
    public void test_RemoveGreaterKey_ExecuteWithInvalidParams() {
        CommandRemoveGreaterKey commandRemoveGreaterKey = new CommandRemoveGreaterKey(collectionManager);
        Object[] params = new Object[]{"something", "something"};

        Exception exception = assertThrows(Exception.class,
                () -> commandRemoveGreaterKey.Execute(params));
        assertEquals("Ожидаемое число параметров 1, переданное число параметров 2", exception.getMessage());

        exception = assertThrows(Exception.class, () -> commandRemoveGreaterKey.Execute(null));
        assertEquals("Не переданы параметры, ожидаемое число параметров 1", exception.getMessage());

        exception = assertThrows(Exception.class, () -> commandRemoveGreaterKey.Execute("INCORRECT_PARAM"));
        assertEquals(String.format(Strings.Errors.Commands.expectedTypeErrorFormat, Integer.class.getSimpleName(), String.class.getSimpleName()),
                exception.getMessage());
    }
    //endregion

    //region RemoveKey
    @Test
    public void test_RemoveKey_ExecuteWithValidParams() throws Exception {
        CommandRemoveKey removeKey = new CommandRemoveKey(collectionManager);
        int beforeExec = 100;
        for (int i = 1; i < 101; i++) {
            removeKey.Execute(i);
            assertEquals(--beforeExec, collectionManager.GetSize());
        }
    }

    @Test
    public void test_RemoveKey_ExecuteWithInvalidParams() {
        CommandRemoveKey removeKey = new CommandRemoveKey(collectionManager);
        Object[] params = new Object[2];
        params[0] = "something";
        params[1] = "something";

        Exception exception = assertThrows(Exception.class,
                () -> removeKey.Execute(params));
        assertEquals("Ожидаемое число параметров 1, переданное число параметров 2", exception.getMessage());

        exception = assertThrows(Exception.class, () -> removeKey.Execute(null));
        assertEquals("Не переданы параметры, ожидаемое число параметров 1", exception.getMessage());

        exception = assertThrows(Exception.class, () -> removeKey.Execute("INCORRECT_PARAM"));
        assertEquals(String.format(Strings.Errors.Commands.expectedTypeErrorFormat, Integer.class.getSimpleName(), String.class.getSimpleName()),
                exception.getMessage());
    }
    //endregion

    //region RemoveLower
    @Test
    public void test_RemoveLower_ExecuteWithValidParams() throws Exception {
        CommandRemoveLower removeLower = new CommandRemoveLower(collectionManager);
        collectionManager.clear();
        SpaceMarine lowerMarine = new SpaceMarine(1, "Lower", new Coordinate(3.4, 1),
                this.GetCurrentTimestamp(), 100, 1, Category.ASSAULT, WeaponType.POWER_BLADE, new Chapter());
        SpaceMarine higherMarine = new SpaceMarine(1, "Lower", new Coordinate(3.4, 1),
                this.GetCurrentTimestamp(), 100, 3, Category.ASSAULT, WeaponType.POWER_BLADE, new Chapter());
        collectionManager.insert(lowerMarine);
        collectionManager.insert(higherMarine);

        removeLower.Execute(higherMarine);

        assertEquals(1, collectionManager.getMarines().size());
    }

    @Test
    public void test_RemoveLower_ExecuteWithInvalidParams() {
        CommandRemoveLower removeLower = new CommandRemoveLower(collectionManager);
        Object[] params = new Object[]{"some", "some"};

        Exception exception = assertThrows(Exception.class,
                () -> removeLower.Execute(params));
        assertEquals("Ожидаемое число параметров 1, переданное число параметров 2", exception.getMessage());

        exception = assertThrows(Exception.class,
                () -> removeLower.Execute(null));
        assertEquals("Не переданы параметры, ожидаемое число параметров 1", exception.getMessage());

        exception = assertThrows(Exception.class, () -> removeLower.Execute("INCORRECT_PARAM"));
        assertEquals(String.format(Strings.Errors.Commands.expectedTypeErrorFormat, SpaceMarine.class.getSimpleName(), String.class.getSimpleName()),
                exception.getMessage());
    }
    //endregion

    //region ReplaceIfLower
    @Test
    public void test_ReplaceIfLower_ExecuteWithValidParams() throws Exception {
        CommandReplaceIfLower replaceIfLower = new CommandReplaceIfLower(collectionManager);
        SpaceMarine lowerMarine = new SpaceMarine(1, "Lower", new Coordinate(3.4, 1),
                this.GetCurrentTimestamp(), 100, 1, Category.ASSAULT, WeaponType.POWER_BLADE, new Chapter());
        SpaceMarine higherMarine = new SpaceMarine(1, "Lower", new Coordinate(3.4, 1),
                this.GetCurrentTimestamp(), 100, 3, Category.ASSAULT, WeaponType.POWER_BLADE, new Chapter());
        collectionManager.insert(higherMarine);

        Object[] params = new Object[]{101, lowerMarine};
        replaceIfLower.Execute(params);

        assertEquals(0, collectionManager.getMarines().get(101).compareTo(lowerMarine));
    }

    @Test
    public void test_ReplaceIfLower_ExecuteWithInvalidParams() {
        CommandReplaceIfLower replaceIfLower = new CommandReplaceIfLower(collectionManager);
        Object[] params = new Object[]{"some"};
        //если передать нужное число параметров, но при этом параметры будут не того типа, то эта ошибка не отлавливается
        Exception exception = assertThrows(Exception.class,
                () -> replaceIfLower.Execute(params));
        assertEquals("Ожидаемое число параметров 2, переданное число параметров 1", exception.getMessage());

        exception = assertThrows(Exception.class,
                () -> replaceIfLower.Execute(null));
        assertEquals("Не переданы параметры, ожидаемое число параметров 2", exception.getMessage());

        Object[] params2 = new Object[]{"INCORRECT_PARAM", new SpaceMarine()};
        exception = assertThrows(Exception.class, () -> replaceIfLower.Execute(params2));
        assertEquals(String.format(Strings.Errors.Commands.expectedTypeErrorFormat, Integer.class.getSimpleName(), String.class.getSimpleName()),
                exception.getMessage());

        Object[] params3 = new Object[]{1, "INCORRECT_PARAM"};
        exception = assertThrows(Exception.class, () -> replaceIfLower.Execute(params3));
        assertEquals(String.format(Strings.Errors.Commands.expectedTypeErrorFormat, SpaceMarine.class.getSimpleName(), String.class.getSimpleName()),
                exception.getMessage());
    }
    //endregion

    //region Save
    @Test
    public void test_Save_ExecuteWithValidParams() throws Exception {
//        CommandSave save = new CommandSave(collectionManager);
//        save.Execute();
//
//        CollectionManagerToFile loadedCollectionManager = new CollectionManagerToFile(savePath);
//
//        assertEquals(0, collectionManager.compareTo(loadedCollectionManager));
    }

    @Test
    public void test_Save_ExecuteWithInvalidParams() {
        CommandSave save = new CommandSave(collectionManager);
        Object[] params = new Object[]{"some"};

        Exception exception = assertThrows(Exception.class, () ->
                save.Execute(params));
        assertEquals(Strings.Errors.Commands.expectingNoParams, exception.getMessage());
    }
    //endregion

    //region Update
    @Test
    public void test_Update_ExecuteWithValidParams() throws Exception {
        CommandUpdate commandUpdate = new CommandUpdate(collectionManager);
        Object[] params;
        for (int i = 1; i < 51; i++) {
            SpaceMarine marine = GenerateRandomSpaceMarine(i);
            params = new Object[]{i, marine};
            commandUpdate.Execute(params);
            assertEquals(0, collectionManager.getMarines().get(i).compareTo(marine));
        }
    }

    @Test
    public void test_Update_ExecuteWithInvalidParams() {
        CommandUpdate commandUpdate = new CommandUpdate(collectionManager);
        Object[] params = new Object[]{"Something"};

        Exception exception = assertThrows(Exception.class,
                () -> commandUpdate.Execute(params));

        assertEquals("Ожидаемое число параметров 2, переданное число параметров 1", exception.getMessage());

        exception = assertThrows(Exception.class, () -> commandUpdate.Execute(null));
        assertEquals("Не переданы параметры, ожидаемое число параметров 2", exception.getMessage());

        Object[] params2 = new Object[]{"INCORRECT_PARAM", new SpaceMarine()};
        exception = assertThrows(Exception.class, () -> commandUpdate.Execute(params2));
        assertEquals(String.format(Strings.Errors.Commands.expectedTypeErrorFormat, Integer.class.getSimpleName(), String.class.getSimpleName()),
                exception.getMessage());

        Object[] params3 = new Object[]{1, "INCORRECT_PARAM"};
        exception = assertThrows(Exception.class, () -> commandUpdate.Execute(params3));
        assertEquals(String.format(Strings.Errors.Commands.expectedTypeErrorFormat, SpaceMarine.class.getSimpleName(), String.class.getSimpleName()),
                exception.getMessage());
    }
    //endregion

    //region Help
    @Test
    public void test_Help_ExecuteWithValidParams() throws Exception {
        CommandHelp help = new CommandHelp(this.collectionManager);

        StringBuilder result = new StringBuilder();
        result.append(Command.Titles.help).append(" - ").append(Command.Descriptions.help).append("\n");
        result.append(Command.Titles.info).append(" - ").append(Command.Descriptions.info).append("\n");
        result.append(Command.Titles.show).append(" - ").append(Command.Descriptions.show).append("\n");
        result.append(Command.Titles.insert).append(" - ").append(Command.Descriptions.insert).append("\n");
        result.append(Command.Titles.update).append(" - ").append(Command.Descriptions.update).append("\n");
        result.append(Command.Titles.removeKey).append(" - ").append(Command.Descriptions.removeKey).append("\n");
        result.append(Command.Titles.clear).append(" - ").append(Command.Descriptions.clear).append("\n");
//        result.append(Command.Titles.save).append(" - ").append(Command.Descriptions.save).append("\n");
        result.append(Command.Titles.executeScript).append(" - ").append(Command.Descriptions.executeScript).append("\n");
        result.append(Command.Titles.exit).append(" - ").append(Command.Descriptions.exit).append("\n");
        result.append(Command.Titles.removeLower).append(" - ").append(Command.Descriptions.removeLower).append("\n");
        result.append(Command.Titles.replaceIfLower).append(" - ").append(Command.Descriptions.replaceIfLower).append("\n");
        result.append(Command.Titles.removeGreaterKey).append(" - ").append(Command.Descriptions.removeGreaterKey).append("\n");
        result.append(Command.Titles.countByHeartCount).append(" - ").append(Command.Descriptions.countByHeartCount).append("\n");
        result.append(Command.Titles.filterByCategory).append(" - ").append(Command.Descriptions.filterByCategory).append("\n");
        result.append(Command.Titles.printDescending).append(" - ").append(Command.Descriptions.printDescending).append("\n");

        assertEquals(result.toString(), help.Execute());
    }

    @Test
    public void test_Help_ExecuteWithInvalidParams(){
        CommandHelp command = new CommandHelp(collectionManager);
        Object[] params = new Object[]{"some"};

        Exception exception = assertThrows(Exception.class, () ->
                command.Execute(params));
        assertEquals(Strings.Errors.Commands.expectingNoParams, exception.getMessage());
    }

    @Test
    public void test_Help_GetCommand() throws Exception {
        CommandHelp command = new CommandHelp(collectionManager);
        for(Command value : command.GetCommands()){
            assertEquals(value.toString(), command.GetCommand(value.getName()).toString());
        }
    }
    //endregion

    //region Info
    @Test
    public void test_Info_ExecuteWithValidParams() throws Exception {
        CommandInfo command = new CommandInfo(this.collectionManager);
        String expected = "Информация о коллекции:"
                + "\nТип коллекции: TreeMap"
                + "\nДата инициализации: " + collectionManager.getInitializationDate()
                + "\nКоличество элементов: " + this.collectionManager.GetSize()
                + "\nПоследний вставленный ID: " + this.collectionManager.GetSize();
        assertEquals(expected, command.Execute());
    }

    @Test
    public void test_Info_ExecuteWithInvalidParams(){
        CommandInfo command = new CommandInfo(collectionManager);
        Object[] params = new Object[]{"some"};

        Exception exception = assertThrows(Exception.class, () ->
                command.Execute(params));
        assertEquals(Strings.Errors.Commands.expectingNoParams, exception.getMessage());

    }
    //endregion

    //region PrintDescending
    @Test
    public void test_PrintDescending_ExecuteWithValidParams() throws Exception {

        //region Для пустой коллекции
        this.collectionManager.clear();
        CommandPrintDescending command = new CommandPrintDescending(collectionManager);
        assertEquals(Strings.Messages.Collection.emptyCollection, command.Execute());
        //endregion

        List<SpaceMarine> testMarines = this.GenerateSpaceMarines(3);
        for(int i = 0; i < 3;i++) {
            collectionManager.insert(testMarines.get(i));
        }

        testMarines = testMarines.stream().sorted(Comparator.reverseOrder()).toList();
        StringBuilder result = new StringBuilder();
        SpaceMarine marine = new SpaceMarine();
        for (int i = 0; i < 3; i++){
            marine = testMarines.get(i);

            result.append("{");
            result.append("\"SpaceMarine id\": ").append(marine.getId()).append(", ");
            result.append("\"SpaceMarine name\": \"").append(marine.getName()).append("\", ");
            result.append("\"SpaceMarine coordinates\": ").append(marine.getCoordinates()).append(", ");
            result.append("\"SpaceMarine creation date\": \"").append(marine.getCreationDate()).append("\", \n");
            result.append("\"SpaceMarine health\": ").append((marine.getHealth() == null) ? "\"not currently set\"" : marine.getHealth()).append(", ");
            result.append("\"SpaceMarine heartCount\": ").append(marine.getHeartCount()).append(", ");
            result.append("\"SpaceMarine AstartesCategory\": \"").append(marine.getCategory()).append("\", \n");
            result.append("\"SpaceMarine MeleeWeapon\": ").append((marine.getWeaponType() == null) ? "\"not currently set\"" : "\"" + marine.getWeaponType() + "\"").append(", ");
            result.append("\"SpaceMarine Chapter\": ").append((marine.getChapter() == null) ? "\"not currently set\"" : "\"" + marine.getChapter() + "\"");
            result.append("}");
            result.append("\n");


        }
        assertEquals(result.toString(), command.Execute() + "\n");
    }

    @Test
    public void test_PrintDescending_ExecuteWithInvalidParams(){
        CommandPrintDescending command = new CommandPrintDescending(collectionManager);
        Object[] params = new Object[]{"some"};

        Exception exception = assertThrows(Exception.class, () ->
                command.Execute(params));
        assertEquals(Strings.Errors.Commands.expectingNoParams, exception.getMessage());
    }
    //endregion

    //region Show
    @Test
    public void test_Show_ExecuteWithValidParams() throws Exception {
        //region Для пустой коллекции
        this.collectionManager.clear();
        CommandShow show = new CommandShow(collectionManager);
        assertEquals(Strings.Messages.Collection.emptyCollection, show.Execute());
        //endregion

        List<SpaceMarine> testMarines = this.GenerateSpaceMarines(3);
        SpaceMarine marine;
        StringBuilder result = new StringBuilder();
        result.append("Элементы коллекции:\n");
        for(int i = 0; i < 3;i++) {
            marine = testMarines.get(i);
            this.collectionManager.insert(marine);


            result.append("{");
            result.append("\"SpaceMarine id\": ").append(marine.getId()).append(", ");
            result.append("\"SpaceMarine name\": \"").append(marine.getName()).append("\", ");
            result.append("\"SpaceMarine coordinates\": ").append(marine.getCoordinates()).append(", ");
            result.append("\"SpaceMarine creation date\": \"").append(marine.getCreationDate()).append("\", \n");
            result.append("\"SpaceMarine health\": ").append((marine.getHealth() == null) ? "\"not currently set\"" : marine.getHealth()).append(", ");
            result.append("\"SpaceMarine heartCount\": ").append(marine.getHeartCount()).append(", ");
            result.append("\"SpaceMarine AstartesCategory\": \"").append(marine.getCategory()).append("\", \n");
            result.append("\"SpaceMarine MeleeWeapon\": ").append((marine.getWeaponType() == null) ? "\"not currently set\"" : "\"" + marine.getWeaponType() + "\"").append(", ");
            result.append("\"SpaceMarine Chapter\": ").append((marine.getChapter() == null) ? "\"not currently set\"" : "\"" + marine.getChapter() + "\"");
            result.append("}");
            result.append("\n");

        }

        assertEquals(result.toString(), show.Execute());

    }

    @Test
    public void test_Show_ExecuteWithInvalidParams(){
        CommandShow command = new CommandShow(collectionManager);
        Object[] params = new Object[]{"some"};

        Exception exception = assertThrows(Exception.class, () ->
                command.Execute(params));
        assertEquals(Strings.Errors.Commands.expectingNoParams, exception.getMessage());
    }
    //endregion

}
