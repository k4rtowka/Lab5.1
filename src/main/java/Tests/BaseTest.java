package Tests;

import Models.*;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class BaseTest {
    public CollectionManagerToFile collectionManager;
    public String savePath;

    //region Генерация морпехов
    private static final List<String> NAMES = Arrays.asList("John", "Doe", "James", "Alex", "Chris", "Michael", "David", "Martin");
    private static final List<Category> CATEGORIES = Arrays.asList(Category.values());
    private static final List<WeaponType> MELEE_WEAPONS = Arrays.asList(WeaponType.values());

    public Timestamp GetCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public SpaceMarine GenerateRandomSpaceMarine(int id) throws Exception {
        SpaceMarine marine = new SpaceMarine();

        marine.setId(id);
        marine.setName(NAMES.get(ThreadLocalRandom.current().nextInt(NAMES.size())));
        marine.setCoordinate(new Coordinate(
                ThreadLocalRandom.current().nextDouble(-100, 101),
                ThreadLocalRandom.current().nextInt(-50, 51)
        ));
        marine.setCreationDate(this.GetCurrentTimestamp());
        marine.setHealth(ThreadLocalRandom.current().nextInt(1, 101));
        marine.setHeartCount(ThreadLocalRandom.current().nextInt(1, 4));
        marine.setAstartes(new Astartes(CATEGORIES.get(ThreadLocalRandom.current().nextInt(CATEGORIES.size()))));
        marine.setWeapon(new Weapon(MELEE_WEAPONS.get(ThreadLocalRandom.current().nextInt(MELEE_WEAPONS.size()))));
        // Не устанавливаем главу (chapter), так как у нас нет информации о том, как генерировать её

        return marine;
    }

    public List<SpaceMarine> GenerateSpaceMarines(int count) {
        List<SpaceMarine> marines = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            try {
                marines.add(GenerateRandomSpaceMarine(i));
            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
        return marines;
    }
    //endregion

    public void InitCollection(String fileName, Integer size) {
        try {
            savePath = "./src/main/java/Tests/Data/" + fileName;
            File dataFile = new File(savePath);
            if (dataFile.exists())
                dataFile.delete();
            collectionManager = new CollectionManagerToFile(savePath);
            List<SpaceMarine> marines = GenerateSpaceMarines(size);
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
