package Models;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CollectionManager {
    //region Поля
    /**
     * Коллекция объектов
     */
    TreeMap<Integer, SpaceMarine> marines;
    /**
     * Дата инициализации
     */
    private LocalDate initializationDate;
    /**
     * Файл с данными
     */
    private File dataFile;
    /**
     * Последний вставленный ID
     */
    private int lastId = 0;
    /**
     * Скрипты выполняемые в данный момента командами execute_script
     */
    private HashSet<String> executedScripts;
    //endregion

    //region Конструкторы
    public CollectionManager(String filename) throws Exception {
        executedScripts = new HashSet<>();
        marines  = new TreeMap<>();
        dataFile = new File(filename);
        initializationDate = LocalDate.now();
        loadFromFile();
    }
    //endregion

    //region Методы
    public void add(SpaceMarine s){
        if(spaceMarineMap != null){
            spaceMarineMap.put(s.getId(), s);
        }else{
            TreeMap<Integer, SpaceMarine> spaceMarines = new TreeMap<>();
            spaceMarines.put(s.getId(), s);
            com.k4rtowka.lab5.CollectionManagers.CollectionManager.getInstance().setSpaceMarineMap(spaceMarines);
        }
    }
    /**
     * Загрузка из файла
     */
    private void loadFromFile() throws Exception {
//        try (Reader reader = new InputStreamReader(new FileInputStream(dataFile))) {
//            marines = gson.fromJson(reader, new TypeToken<LinkedList<SpaceMarine>>() {
//            }.getType());
//
//            //region Генерация ID и проверка на дубликаты
//            for (SpaceMarine marine : marines) {
//                if (marine.getId() <= lastId) {
//                    marine.setId(++lastId);
//                } else {
//                    lastId = marine.getId();
//                }
//            }
//            //endregion
//
//            //region Проверка полей на правильность заполнения
//            LinkedList<SpaceMarine> marinesTemp = new LinkedList<SpaceMarine>();
//            for (SpaceMarine marine : marines) {
//                try {
//                    marinesTemp.add(new SpaceMarine(marine));
//                } catch (Exception ex) {
//                    System.out.println(String.format("Элемент не был загружен, его описание:'%s'", marine));
//                }
//            }
//            //endregion
//
//        } catch (IOException e) {
//            System.err.println("Не удалось загрузить файл: " + e.getMessage());
//        }
    }
    //endregion
}
