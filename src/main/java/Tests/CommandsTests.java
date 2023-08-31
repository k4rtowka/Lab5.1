package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Commands.CommandClear;
import Commands.CommandCountByHeartCount;
import Commands.CommandHelp;
import Models.SpaceMarine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandsTests extends BaseTest {


    @BeforeEach
    public void setUp() throws Exception {
        this.InitCollection("CommandsTests.xml", 100);
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
        assertEquals("У команды нет входных параметров!", exception.getMessage());
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

//        Exception exception = assertThrows(Exception.class, () -> {
//            countByHeartCount.Execute(params);
//        });
//        assertEquals("", exception.getMessage());
//
//        Exception exception = assertThrows(Exception.class, () -> {
//            countByHeartCount.Execute(null);
//        });
//        assertEquals("", exception.getMessage());
        this.ThrowNotImplemented();
    }
    //endregion

    //region Help
    @Test
    public void help() throws Exception {
        CommandHelp help = new CommandHelp(this.collectionManager);
        //System.out.println(help.Execute());
        this.ThrowNotImplemented();
    }
    //endregion

}
