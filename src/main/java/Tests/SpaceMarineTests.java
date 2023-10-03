package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;


public class SpaceMarineTests extends BaseTest {
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

    //region ID user
    @Test
    public void testSetAndGetUserId() {
        // Проверка значения по умолчанию (предположим, что это null)
        assertNull(marine.getUserId());

        // Установка и получение значения
        Integer expectedUserId = 42;
        marine.setUserId(expectedUserId);
        assertEquals(expectedUserId, marine.getUserId());

        // Установка и получение другого значения
        expectedUserId = 100;
        marine.setUserId(expectedUserId);
        assertEquals(expectedUserId, marine.getUserId());

        // Проверка на null
        marine.setUserId(null);
        assertNull(marine.getUserId());
    }
    //endregion

    //region Name
    @Test
    public void setName_validName_setsName() {
        try {
            marine.setName("Space Ship");
            assertEquals("Space Ship", marine.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        Coordinate coordinates = new Coordinate(2.3, 4);
        try {
            marine.setCoordinate(coordinates);
            assertEquals(coordinates, marine.getCoordinates());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void setCoordinate_InvalidCoordinates() {
        Exception exception = assertThrows(Exception.class,
                () -> marine.setCoordinate(null));
        assertEquals("Координаты не могут быть null.", exception.getMessage());
    }
    //endregion

    //region Date
    @Test
    public void setDate_ValidDate() {
        //Date date = new Date();
        Timestamp timestamp = this.GetCurrentTimestamp();
        try {
            marine.setCreationDate(timestamp);
            assertEquals(timestamp, marine.getCreationDate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            marine.setAstartes(new Astartes(Category.ASSAULT));
            assertEquals(Category.ASSAULT, marine.getCategory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void setCategory_InvalidCategory() {
        Exception exception = assertThrows(Exception.class,
                () -> marine.setAstartes(null));
        assertEquals("Категория Astartes не может быть null.", exception.getMessage());
    }
    //endregion

    //region Weapon
    @Test
    public void setWeapon_validWeapon() {
        marine.setWeapon(new Weapon(WeaponType.POWER_BLADE));
        assertEquals(WeaponType.POWER_BLADE, marine.getWeaponType());
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
