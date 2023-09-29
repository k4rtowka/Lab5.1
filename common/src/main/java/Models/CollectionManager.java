package Models;


import Common.Strings;
import Tests.Data.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@XmlRootElement(name = "CollectionManagerToFile")
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionManager implements Comparable<CollectionManager> {

    //region Поля
    /**
     * Коллекция объектов
     */
    @XmlElement(name = "marines")
    protected TreeMap<Integer, SpaceMarine> marines;
    /**
     * Дата инициализации
     */
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    protected LocalDate initializationDate;
    /**
     * Последний вставленный ID
     */
    @XmlElement
    protected int lastId = 0;
    /**
     * Скрипты выполняемые в данный момента командами execute_script
     */
    @XmlElement(name = "executedScripts")
    protected HashSet<String> executedScripts;
    //endregion

    //region Конструкторы
    public CollectionManager() throws Exception {
        executedScripts = new HashSet<>();
        marines = new TreeMap<>();
        initializationDate = LocalDate.now();
    }
    //endregion

    //region Геттеры/Сеттеры


    //region Initialization Date
    public LocalDate getInitializationDate() {
        return this.initializationDate;
    }

    public void setInitializationDate(LocalDate initializationDate) {
        this.initializationDate = initializationDate;
    }
    //endregion

    //region Marines
    public void setMarines(TreeMap<Integer, SpaceMarine> marines) {
        this.marines = marines;
    }

    public TreeMap<Integer, SpaceMarine> getMarines() {
        return marines;
    }
    //endregion

    //region Last Id
    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public int getLastId() {
        return this.lastId;
    }
    //endregion

    //region Executed Scripts
    public void setExecutedScripts(HashSet<String> executedScripts) {
        this.executedScripts = executedScripts;
    }

    public HashSet<String> getExecutedScripts() {
        return this.executedScripts;
    }
    //endregion

    //endregion

    //region Методы

    //region Исполняемые скрипты
    public void AddExecuteScript(String fileName) {
        this.executedScripts.add(fileName);
    }

    public void RemoveExecuteScript(String fileName) {
        this.executedScripts.remove(fileName);
    }

    public void ClearExecuteScripts() {
        this.executedScripts.clear();
    }

    public boolean CheckExecuteScript(String fileName) {
        return this.executedScripts.contains(fileName);
    }
    //endregion

    //region По заданию
    public Integer GetSize() {
        if (this.marines == null)
            return 0;
        return this.marines.size();
    }

    /**
     * Добавить новый элемент с заданным ключом
     *
     * @param marine готовый объект SpaceMarine
     */
    public Integer insert(SpaceMarine marine) {
        if (marine != null) {
            lastId++;  // увеличиваем ID на 1
            marines.put(lastId, marine);
            marine.setId(lastId);
        } else {
            throw new IllegalArgumentException("SpaceMarine не может быть null.");
        }
        return lastId;
    }

    /**
     * Обновить значение элемента коллекции, id которого равен заданному
     *
     * @param id     ID элемента, который нужно обновить
     * @param marine Новый объект SpaceMarine для замены текущего
     * @throws NoSuchElementException если элемента с таким id не существует
     */
    public Boolean update(int id, SpaceMarine marine) {
        if (marine == null) {
            throw new IllegalArgumentException("SpaceMarine не может быть null.");
        }
        if (marines.containsKey(id)) {
            marines.put(id, marine);
            marine.setId(id);
            return true;
        } else {
            throw new NoSuchElementException("Элемент с ID " + id + " не найден в коллекции.");
        }
    }

    /**
     * Удалить элемент из коллекции по его ключу
     *
     * @param id ID элемента, который нужно удалить
     * @throws NoSuchElementException если элемента с таким id не существует
     */
    public Boolean removeKey(int id) {
        if (marines.containsKey(id)) {
            marines.remove(id);
            return true;
        } else {
            throw new NoSuchElementException("Элемент с ID " + id + " не найден в коллекции.");
        }
    }

    /**
     * Очистить коллекцию
     */
    public void clear() {
        this.marines.clear();
        this.lastId = 0;
    }

    /**
     * Удалить из коллекции все элементы, меньшие, чем заданный
     *
     * @param marine элемент для сравнения
     */
    public Boolean removeLower(SpaceMarine marine) {
        return this.marines.entrySet().removeIf(x -> x.getValue().compareTo(marine) < 0);
    }


    /**
     * Заменить значение по ключу, если новое значение меньше старого
     *
     * @param key       ключ элемента, который нужно заменить
     * @param newMarine новое значение
     */
    public Boolean replaceIfLower(Integer key, SpaceMarine newMarine) {
        SpaceMarine currentMarine = marines.get(key);
        if (currentMarine != null && newMarine.compareTo(currentMarine) < 0) {
            marines.put(key, newMarine);
            return true;
        }
        return false;
    }

    /**
     * Удалить из коллекции все элементы, ключ которых превышает заданный
     *
     * @param key ключ элемента, который нужно заменить
     */
    public Boolean removeGreaterKey(Integer key) {
        return this.marines.entrySet().removeIf(entry -> entry.getKey() > key);
    }

    /**
     * Возвращает количество элементов в коллекции, у которых значение поля {@code heartCount} равно заданному.
     *
     * @param heartCount Значение поля {@code heartCount} для сравнения.
     * @return Количество элементов, соответствующих заданному условию.
     */
    public Long countByHeartCount(Integer heartCount) {
        return marines.values().stream().filter(marine -> marine.getHeartCount() == heartCount).count();
    }

    /**
     * Возвращает список элементов в коллекции, у которых значение поля {@code category} равно заданному.
     *
     * @param category Значение поля {@code category} для фильтрации.
     * @return Список элементов, соответствующих заданному условию.
     */
    public List<SpaceMarine> filterByCategory(Category category) {
        return marines.values().stream().filter(marines -> {
            try {
                return marines.getCategory() == category;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    /**
     * Возвращает информацию о коллекции (тип, дата инициализации,
     * количество элементов и т.д.) в виде строки.
     *
     * @return строка с информацией о коллекции.
     */
    public String info() {
        return "Информация о коллекции:"
                + "\nТип коллекции: " + marines.getClass().getSimpleName()
                + "\nДата инициализации: " + initializationDate
                + "\nКоличество элементов: " + marines.size()
                + "\nПоследний вставленный ID: " + lastId;
    }


    /**
     * Возвращает все элементы коллекции в строковом представлении.
     *
     * @return строка со всеми элементами коллекции.
     */
    public String show() {
        if (marines.size() == 0)
            return Strings.Messages.Collection.emptyCollection;
        else {
            StringBuilder result = new StringBuilder("Элементы коллекции:\n");
            for (SpaceMarine marine : this.marines.values()) {
                result.append(marine.toString()).append("\n");
            }
            return result.toString();
        }
    }

    /**
     * Вывести элементы коллекции в порядке убывания
     */
    public String printDescending() {
        if (marines.size() == 0) {
            return Strings.Messages.Collection.emptyCollection;
        } else {
            return marines.values().stream()
                    .sorted(Comparator.reverseOrder()).map(SpaceMarine::toString)
                    .collect(Collectors.joining("\n"));
        }
    }

    /**
     * Сохранить коллекцию в файл
     */
    public boolean Save() throws Exception {
        throw new Exception("Метод не реализован");
    }

    /**
     * Загрузка из файла
     */
    public boolean Load() throws Exception {
        throw new Exception("Метод не реализован");
    }

    public boolean Load(int idUser) throws Exception {
        throw new Exception("Метод не реализован");
    }

    @Override
    public int compareTo(CollectionManager other) {
        int result = initializationDate.compareTo(other.initializationDate);
        if (result != 0) return result;
        result = Integer.compare(lastId, other.lastId);
        if (result != 0) return result;
        // Сравнение коллекций (по содержимому)
        Iterator<Map.Entry<Integer, SpaceMarine>> thisIterator = this.marines.entrySet().iterator();
        Iterator<Map.Entry<Integer, SpaceMarine>> otherIterator = other.marines.entrySet().iterator();
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            Map.Entry<Integer, SpaceMarine> thisEntry = thisIterator.next();
            Map.Entry<Integer, SpaceMarine> otherEntry = otherIterator.next();
            // Сравниваем ключи (ID)
            result = thisEntry.getKey().compareTo(otherEntry.getKey());
            if (result != 0) {
                return result;
            }
            // Сравниваем значения (SpaceMarines)
            result = thisEntry.getValue().compareTo(otherEntry.getValue());
            if (result != 0) {
                return result;
            }
        }
        // Если одна из коллекций длиннее другой
        if (thisIterator.hasNext()) {
            return 1;
        } else if (otherIterator.hasNext()) {
            return -1;
        }
        return 0;
    }
    //endregion

    //endregion
}
