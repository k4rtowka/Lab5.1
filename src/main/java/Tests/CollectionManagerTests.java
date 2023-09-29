package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CollectionManagerTests extends BaseTest {

    @BeforeEach
    public void SetUp() {
        this.InitCollection("CollectionManagerTests.xml", 100);
    }


    @Test
    public void saveLoadTest() throws Exception {
        collectionManager.save();

        CollectionManagerToFile loadedCollectionManager = new CollectionManagerToFile(savePath);

        assertEquals(0, collectionManager.compareTo(loadedCollectionManager));
    }

    @Test
    public void insertNewElement() {
        SpaceMarine testMarine = null;
        try {
            testMarine = GenerateRandomSpaceMarine(101);
            collectionManager.insert(testMarine);
            assertEquals(0, collectionManager.getMarines().get(101).compareTo(testMarine));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateElement_ValidKey() {
        SpaceMarine testMarine = null;
        try {
            testMarine = GenerateRandomSpaceMarine(100);
            collectionManager.update(100, testMarine);
            assertEquals(0, collectionManager.getMarines().get(100).compareTo(testMarine));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateElement_InvalidKey() {
        assertThrows(NoSuchElementException.class, () -> collectionManager.update(-100, new SpaceMarine()));
    }

    @Test
    public void removeElement_ValidKey() {
        collectionManager.removeKey(100);
        assertEquals(99, collectionManager.getMarines().size());
    }

    @Test
    public void removeElement_InvalidKey() {
        assertThrows(NoSuchElementException.class, () -> collectionManager.removeKey(-100));
    }

    @Test
    public void clearCollection() {
        collectionManager.clear();
        assertEquals(0, collectionManager.getMarines().size());
    }

    @Test
    public void removeLower() {

        collectionManager.clear();
        SpaceMarine lowerMarine = null;
        try {
            lowerMarine = new SpaceMarine(1, "Lower", new Coordinate(3.4, 1),
                    this.GetCurrentTimestamp(), 100, 1, Category.ASSAULT, WeaponType.POWER_BLADE, new Chapter());
            SpaceMarine higherMarine = new SpaceMarine(1, "Lower", new Coordinate(3.4, 1),
                    this.GetCurrentTimestamp(), 100, 3, Category.ASSAULT, WeaponType.POWER_BLADE, new Chapter());
            collectionManager.insert(lowerMarine);
            collectionManager.insert(higherMarine);
            collectionManager.removeLower(higherMarine);
            assertEquals(1, collectionManager.getMarines().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void replaceIfLower() {
        SpaceMarine lowerMarine = null;
        try {
            lowerMarine = new SpaceMarine(1, "Lower", new Coordinate(3.4, 1),
                    this.GetCurrentTimestamp(), 100, 1, Category.ASSAULT, WeaponType.POWER_BLADE, new Chapter());
            SpaceMarine higherMarine = new SpaceMarine(1, "Lower", new Coordinate(3.4, 1),
                    this.GetCurrentTimestamp(), 100, 3, Category.ASSAULT, WeaponType.POWER_BLADE, new Chapter());
            collectionManager.insert(higherMarine);
            collectionManager.replaceIfLower(101, lowerMarine);
            assertEquals(0, collectionManager.getMarines().get(101).compareTo(lowerMarine));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void removeGreaterKey() {
        collectionManager.removeGreaterKey(50);
        assertEquals(50, collectionManager.getMarines().size());
    }

    @Test
    public void countByHeartCount() {
        int count = 0;
        for (SpaceMarine marine : collectionManager.getMarines().values()) {
            if (marine.getHeartCount() == 2) count++;
        }
        assertEquals(count, collectionManager.countByHeartCount(2));
    }

    @Test
    public void filterByCategory() {
        List<SpaceMarine> testMarines = new ArrayList<>();
        for (SpaceMarine marine : collectionManager.getMarines().values()) {
            try {
                if (marine.getCategory() == Category.ASSAULT) testMarines.add(marine);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals(collectionManager.filterByCategory(Category.ASSAULT), testMarines);
    }


}
