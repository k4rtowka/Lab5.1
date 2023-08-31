package Tests;

import Models.*;
import jdk.jshell.spi.ExecutionControl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BaseTest {
    public CollectionManager collectionManager;
    public String savePath;

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

    public void InitCollection(String fileName,Integer size) {
        try {
            savePath = "./src/main/java/Tests/Data/" + fileName;
            File dataFile = new File(savePath);
            if (dataFile.exists())
                dataFile.delete();
            collectionManager = new CollectionManager(savePath);
            List<SpaceMarine> marines = generateSpaceMarines(size);
            for (SpaceMarine marine : marines) {
                collectionManager.insert(marine);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void ThrowNotImplemented() throws Exception {
        throw new Exception("Тест не реализован");
    }
}
