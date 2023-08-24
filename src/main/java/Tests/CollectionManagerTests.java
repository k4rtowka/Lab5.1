package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

        //assertEquals(0, collectionManager.getMarines().get(1).compareTo(testMarine1));
        assertEquals(0, collectionManager.compareTo(loadedCollectionManager));
    }

}
