package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CollectionManagerTests {
    private CollectionManager collectionManager;
    String savePath;

    //region Генерация морпехов
    private static final List<String> NAMES = Arrays.asList("John", "Doe", "James", "Alex", "Chris", "Michael", "David", "Martin");
    private static final List<AstartesCategory> CATEGORIES = Arrays.asList(AstartesCategory.values());
    private static final List<MeleeWeapon> MELEE_WEAPONS = Arrays.asList(MeleeWeapon.values());

    public static SpaceMarine generateRandomSpaceMarine(int id) {
        SpaceMarine marine = new SpaceMarine();

        marine.setId(id);
        marine.setName(NAMES.get(ThreadLocalRandom.current().nextInt(NAMES.size())));
        marine.setCoordinates(new Coordinates(
                ThreadLocalRandom.current().nextDouble(-100, 101),
                ThreadLocalRandom.current().nextInt(-50, 51)
        ));
        marine.setCreationDate(new Date());
        marine.setHealth(ThreadLocalRandom.current().nextInt(1, 101));
        marine.setHeartCount(ThreadLocalRandom.current().nextInt(1, 4));
        marine.setCategory(CATEGORIES.get(ThreadLocalRandom.current().nextInt(CATEGORIES.size())));
        marine.setMeleeWeapon(MELEE_WEAPONS.get(ThreadLocalRandom.current().nextInt(MELEE_WEAPONS.size())));
        // Не устанавливаем главу (chapter), так как у нас нет информации о том, как генерировать её

        return marine;
    }
    public static List<SpaceMarine> generateSpaceMarines(int count) {
        List<SpaceMarine> marines = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            marines.add(generateRandomSpaceMarine(i));
        }
        return marines;
    }
    //endregion
    @BeforeEach
    public void SetUp() {
        try {
            savePath = "./src/main/java/Tests/Data/testData.xml";
            File dataFile = new File(savePath);
            if (dataFile.exists())
                dataFile.delete();
            collectionManager = new CollectionManager(savePath);
            List<SpaceMarine> marines =  generateSpaceMarines(100);
            for (SpaceMarine marine:marines) {
                collectionManager.insert(marine);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void saveLoadTest() throws Exception {
        collectionManager.save();

        CollectionManager loadedCollectionManager = new CollectionManager(savePath);

        assertEquals(0, collectionManager.compareTo(loadedCollectionManager));
    }

    @Test
    public void insertNewElement(){
        SpaceMarine testMarine = generateRandomSpaceMarine(101);
        collectionManager.insert(testMarine);
        assertEquals(0, collectionManager.getMarines().get(101).compareTo(testMarine));
    }

    @Test
    public void updateElement_ValidKey(){
        SpaceMarine testMarine = generateRandomSpaceMarine(100);
        collectionManager.update(100, testMarine);
        assertEquals(0, collectionManager.getMarines().get(100).compareTo(testMarine));
    }

    @Test
    public void updateElement_InvalidKey(){
        assertThrows(NoSuchElementException.class, () -> collectionManager.update(-100, new SpaceMarine()));
    }

    @Test
    public void removeElement_ValidKey(){
        collectionManager.removeKey(100);
        assertEquals(99, collectionManager.getMarines().size());
    }

    @Test
    public void removeElement_InvalidKey(){
        assertThrows(NoSuchElementException.class, () -> collectionManager.removeKey(-100));
    }

    @Test
    public void clearCollection(){
        collectionManager.clear();
        assertEquals(0,collectionManager.getMarines().size());
    }

    @Test
    public void removeLower(){
        collectionManager.clear();
        SpaceMarine lowerMarine = new SpaceMarine(1, "Lower", new Coordinates(3.4, 1),
                new Date(), 100, 1, AstartesCategory.ASSAULT, MeleeWeapon.POWER_BLADE, new Chapter());
        SpaceMarine higherMarine = new SpaceMarine(1, "Lower", new Coordinates(3.4, 1),
                new Date(), 100, 3, AstartesCategory.ASSAULT, MeleeWeapon.POWER_BLADE, new Chapter());
        collectionManager.insert(lowerMarine);
        collectionManager.insert(higherMarine);
        collectionManager.removeLower(higherMarine);
        assertEquals(1, collectionManager.getMarines().size());
    }

    @Test
    public void replaceIfLower(){
        SpaceMarine lowerMarine = new SpaceMarine(1, "Lower", new Coordinates(3.4, 1),
                new Date(), 100, 1, AstartesCategory.ASSAULT, MeleeWeapon.POWER_BLADE, new Chapter());
        SpaceMarine higherMarine = new SpaceMarine(1, "Lower", new Coordinates(3.4, 1),
                new Date(), 100, 3, AstartesCategory.ASSAULT, MeleeWeapon.POWER_BLADE, new Chapter());
        collectionManager.insert(higherMarine);
        collectionManager.replaceIfLower(101, lowerMarine);
        assertEquals(0, collectionManager.getMarines().get(101).compareTo(lowerMarine));
    }

    @Test
    public void removeGreaterKey(){
        collectionManager.removeGreaterKey(50);
        assertEquals(50, collectionManager.getMarines().size());
    }

    @Test
    public void countByHeartCount(){
        int count = 0;
        for(SpaceMarine marine: collectionManager.getMarines().values()){
            if(marine.getHeartCount() == 2) count++;
        }
        assertEquals(count, collectionManager.countByHeartCount(2));
    }

    @Test
    public void filterByCategory(){
        List<SpaceMarine> testMarines = new ArrayList<>();
        for(SpaceMarine marine: collectionManager.getMarines().values()){
            if(marine.getCategory() == AstartesCategory.ASSAULT) testMarines.add(marine);
        }
        assertEquals(collectionManager.filterByCategory(AstartesCategory.ASSAULT), testMarines);
    }



}
