package Tests.Data;

import Common.Settings;
import Models.Chapter;
import Models.CollectionManagerToFile;
import Models.CollectionManagerToSQL;
import Models.SpaceMarine;
import Tests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollectionManagerToSQLTests extends BaseTest {
    //region SQL настройки
    public String DB_URL = Settings.DB_URL;
    public String DB_USERNAME = Settings.DB_USERNAME;
    public String DB_PASSWORD = Settings.DB_PASSWORD;
    //endregion
    CollectionManagerToSQL manager = null;

    @BeforeEach
    public void SetUp() {
        try {
            this.manager = new CollectionManagerToSQL(DB_URL, DB_USERNAME, DB_PASSWORD, 14);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void saveLoadTest() throws Exception {
        manager.clear();
        manager.Save();
        SpaceMarine marine = this.GenerateRandomSpaceMarine(1);
        marine.setUserId(14);
        marine.setChapter(new Chapter(13, "Chapter1", 1));
        manager.insert(marine);
        manager.Save();
        manager.clear();
        manager.Load();

        assertEquals(1, manager.getMarines().size());
    }


}
