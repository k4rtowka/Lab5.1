package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;


public class SpaceMarineTests extends  BaseTest {
    private SpaceMarine marine;

    @BeforeEach
    public void setUp() {
        marine = new SpaceMarine();
    }

    //region ID
    @Test
    public void setId_validId_setsId() {
        marine.setId(10);
        assertEquals(10, marine.getId());
    }

    @Test
    public void setId_noValidId_setsId() {
        assertThrows(IllegalArgumentException.class, () -> marine.setId(-20));
    }
    //endregion

    //region Name
    @Test
    public void setName_validName_setsName() {
        marine.setName("Space Ship");
        assertEquals("Space Ship", marine.getName());
    }

    @Test
    public void setName_nullName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> marine.setName(null));
    }

    @Test
    public void setName_emptyName_setsName() {
        assertThrows(IllegalArgumentException.class, () -> marine.setName(""));
    }

    @Test
    public void setName_blankName_setsName() {
        assertThrows(IllegalArgumentException.class, () -> marine.setName("  "));
    }
//endregion

    //region Coordinates
    @Test
    public void setCoordinate_ValidCoordinates() {
        Coordinates coordinates = new Coordinates(2.3, 4);
        marine.setCoordinates(coordinates);
        assertEquals(coordinates, marine.getCoordinates());
    }

    @Test
    public void setCoordinate_InvalidCoordinates() {
        Exception exception = assertThrows(Exception.class,
                () -> marine.setCoordinates(null));
        assertEquals("Координаты не могут быть null.", exception.getMessage());
    }
    //endregion

    //region Date
    @Test
    public void setDate_ValidDate() {
        Date date = new Date();
        marine.setCreationDate(date);
        assertEquals(date, marine.getCreationDate());
    }

    @Test
    public void setDate_InvalidDate() {
        Exception exception = assertThrows(Exception.class,
                () -> marine.setCreationDate(null));
        assertEquals("Дата создания не может быть null.", exception.getMessage());
    }
    //endregion

    //region Health
    @Test
    public void setHealth_ValidHealth() {
        marine.setHealth(100);
        assertEquals(100, marine.getHealth());
    }

    @Test
    public void setHealth_InvalidHealth() {
        Exception exception = assertThrows(Exception.class,
                () -> marine.setHealth(-100));
        assertEquals("Здоровье, если указано, должно быть больше 0.", exception.getMessage());
    }
    //endregion

    //region Heart Count
    @Test
    public void setHeartCount_ValidCount() {
        for (int i = 1; i < 4; i++) {
            marine.setHeartCount(i);
            assertEquals(i, marine.getHeartCount());
        }
    }

    @Test
    public void setHeartCount_InvalidCount() {
        for (int i = -100; i < 101; i++) {
            if (i > 0 && i < 4) continue;
            int finalI = i;
            Exception exception = assertThrows(Exception.class, () -> {
                marine.setHeartCount(finalI);
            });
            assertEquals("Количество сердец должно быть от 1 до 3 включительно.", exception.getMessage());
        }
    }

//endregion

    //region Category
    @Test
    public void setCategory_validCategory() {
        marine.setCategory(AstartesCategory.ASSAULT);
        assertEquals(AstartesCategory.ASSAULT, marine.getCategory());
    }

    @Test
    public void setCategory_InvalidCategory() {
        Exception exception = assertThrows(Exception.class,
                () -> marine.setCategory(null));
        assertEquals("Категория Astartes не может быть null.", exception.getMessage());
    }
    //endregion

    //region Weapon
    @Test
    public void setWeapon_validWeapon() {
        marine.setMeleeWeapon(MeleeWeapon.POWER_BLADE);
        assertEquals(MeleeWeapon.POWER_BLADE, marine.getMeleeWeapon());
    }

    //endregion

    //region Chapter
    @Test
    public void setChapter_validChapter() {
        Chapter chapter = new Chapter("Chap", 2);
        marine.setChapter(chapter);
        assertEquals(chapter, marine.getChapter());
    }
//endregion


}
