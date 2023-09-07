package Models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "spaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarine implements Comparable<SpaceMarine> {

    //region Поля
    /**
     * Идентификационный номер.
     * <p>
     * Поле не может быть null.
     * Значение поля должно быть больше 0.
     * Значение этого поля должно быть уникальным.
     * Значение этого поля должно генерироваться автоматически.
     * </p>
     */
    @XmlElement(required = true)
    private Integer id;

    /**
     * Название.
     * <p>
     * Поле не может быть null.
     * Строка не может быть пустой.
     * </p>
     */
    @XmlElement(required = true)
    private String name;

    /**
     * Координаты местоположения.
     * <p>
     * Поле не может быть null.
     * </p>
     */
    @XmlElement(required = true)
    private Coordinates coordinates;

    /**
     * Дата создания.
     * <p>
     * Поле не может быть null.
     * Значение этого поля должно генерироваться автоматически.
     * </p>
     */
    @XmlElement(required = true)
    private Date creationDate;

    /**
     * Здоровье.
     * <p>
     * Поле может быть null.
     * Значение поля должно быть больше 0.
     * </p>
     */
    @XmlElement(required = true)
    private Integer health;

    /**
     * Количество сердец.
     * <p>
     * Значение поля должно быть больше 0.
     * Максимальное значение поля: 3.
     * </p>
     */
    @XmlElement(required = true)
    private long heartCount;

    /**
     * Категория Astartes.
     * <p>
     * Поле не может быть null.
     * </p>
     */
    @XmlElement(required = true)
    private AstartesCategory category;

    /**
     * Оружие ближнего боя.
     * <p>
     * Поле может быть null.
     * </p>
     */
    @XmlElement(required = true)
    private MeleeWeapon meleeWeapon;

    /**
     * Глава.
     * <p>
     * Поле может быть null.
     * </p>
     */
    @XmlElement(required = true)
    private Chapter chapter;
    //endregion

    //region Конструкторы

    /**
     * Конструктор SpaceMarine без параметров.
     */
    public SpaceMarine() {
    }

    /**
     * Конструктор SpaceMarine со всеми параметрами.
     *
     * @param id           идентификационный номер
     * @param name         название
     * @param coordinates  координаты
     * @param creationDate дата создания
     * @param health       здоровье
     * @param heartCount   количество сердец
     * @param category     категория Astartes
     * @param meleeWeapon  оружие ближнего боя
     * @param chapter      глава
     */
    public SpaceMarine(int id, String name, Coordinates coordinates, Date creationDate,
                       Integer health, long heartCount, AstartesCategory category, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.setId(id);
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setCreationDate(creationDate);
        this.setHealth(health);
        this.setHeartCount(heartCount);
        this.setCategory(category);
        this.setMeleeWeapon(meleeWeapon);
        this.setChapter(chapter);
    }
    //endregion

    //region Сеттеры

    /**
     * Устанавливает идентификационный номер.
     *
     * @param id идентификационный номер
     * @throws IllegalArgumentException если id равен null или меньше или равен 0
     */
    public void setId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID не может быть null или меньше или равен 0.");
        }
        this.id = id;
    }

    /**
     * Устанавливает имя.
     *
     * @param name имя
     * @throws IllegalArgumentException если имя равно null или пустое
     */
    public void setName(String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Имя не может быть null или пустым.");
        }
        this.name = name;
    }

    /**
     * Устанавливает координаты.
     *
     * @param coordinates координаты
     * @throws IllegalArgumentException если координаты равны null
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Координаты не могут быть null.");
        }
        this.coordinates = coordinates;
    }

    /**
     * Устанавливает дату создания.
     *
     * @param creationDate дата создания
     * @throws IllegalArgumentException если creationDate равна null
     */
    public void setCreationDate(Date creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Дата создания не может быть null.");
        }
        this.creationDate = creationDate;
    }

    /**
     * Устанавливает здоровье.
     *
     * @param health здоровье
     * @throws IllegalArgumentException если здоровье не равно null и меньше или равно 0
     */
    public void setHealth(Integer health) {
        if (health != null && health <= 0) {
            throw new IllegalArgumentException("Здоровье, если указано, должно быть больше 0.");
        }
        this.health = health;
    }

    /**
     * Устанавливает количество сердец.
     *
     * @param heartCount количество сердец
     * @throws IllegalArgumentException если количество сердец меньше 1 или больше 3
     */
    public void setHeartCount(long heartCount) {
        if (heartCount < 1 || heartCount > 3) {
            throw new IllegalArgumentException("Количество сердец должно быть от 1 до 3 включительно.");
        }
        this.heartCount = heartCount;
    }

    /**
     * Устанавливает категорию Astartes.
     *
     * @param category категория Astartes
     * @throws IllegalArgumentException если категория равна null
     */
    public void setCategory(AstartesCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("Категория Astartes не может быть null.");
        }
        this.category = category;
    }

    /**
     * Устанавливает оружие ближнего боя.
     *
     * @param meleeWeapon оружие ближнего боя
     */
    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    /**
     * Устанавливает главу.
     *
     * @param chapter глава
     */
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    //endregion

    //region Геттеры

    /**
     * Возвращает идентификационный номер.
     *
     * @return идентификационный номер
     */
    public Integer getId() {
        return id;
    }

    /**
     * Возвращает количество сердец.
     *
     * @return количество сердец
     */
    public long getHeartCount() {
        return heartCount;
    }

    /**
     * Возвращает категорию Astartes.
     *
     * @return категория Astartes
     */
    public AstartesCategory getCategory() {
        return category;
    }

    /**
     * Возвращает здоровье.
     *
     * @return здоровье или null, если здоровье не установлено
     */
    public Integer getHealth() {
        return health;
    }

    /**
     * Возвращает имя.
     *
     * @return имя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает дату создания.
     *
     * @return дата создания
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * Возвращает координаты.
     *
     * @return координаты
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает оружие ближнего боя.
     *
     * @return оружие ближнего боя или null, если оружие не установлено
     */
    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    /**
     * Возвращает главу.
     *
     * @return главу или null, если глава не установлена
     */
    public Chapter getChapter() {
        return chapter;
    }

    //endregion

    //region Методы

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("\"SpaceMarine id\": ").append(id).append(", ");
        result.append("\"SpaceMarine name\": \"").append(name).append("\", ");
        result.append("\"SpaceMarine coordinates\": ").append(coordinates).append(", ");
        result.append("\"SpaceMarine creation date\": \"").append(creationDate).append("\", \n");
        result.append("\"SpaceMarine health\": ").append((health == null) ? "\"not currently set\"" : health).append(", ");
        result.append("\"SpaceMarine heartCount\": ").append(heartCount).append(", ");
        result.append("\"SpaceMarine AstartesCategory\": \"").append(category).append("\", \n");
        result.append("\"SpaceMarine MeleeWeapon\": ").append((meleeWeapon == null) ? "\"not currently set\"" : "\"" + meleeWeapon + "\"").append(", ");
        result.append("\"SpaceMarine Chapter\": ").append((chapter == null) ? "\"not currently set\"" : "\"" + chapter + "\"");
        result.append("}");
        return result.toString();
    }


    @Override
    public int compareTo(SpaceMarine o) {
        // Сравнение по количеству сердец
        int result = Long.compare(heartCount, o.heartCount);
        if (result != 0) return result;

        // Сравнение по здоровью
        if (health != null && o.health != null) {
            result = Integer.compare(health, o.health);
        } else if (health != null) {
            return 1;  // текущий объект имеет здоровье, а другой объект - нет
        } else if (o.health != null) {
            return -1;  // другой объект имеет здоровье, а текущий объект - нет
        }
        if (result != 0) return result;

        // Сравнение по координатам
        result = this.coordinates.compareTo(o.coordinates);
        if (result != 0) return result;

        // Сравнение по дате создания
        result = creationDate.compareTo(o.creationDate);
        if (result != 0) return result;

        // Сравнение по имени
        result = this.name.compareTo(o.name);
        if (result != 0) return result;

        // Сравнение по категории Astartes
        result = this.category.compareTo(o.category);
        if (result != 0) return result;

        // Сравнение по оружию ближнего боя
        if (meleeWeapon != null && o.meleeWeapon != null) {
            result = meleeWeapon.compareTo(o.meleeWeapon);
        } else if (meleeWeapon != null) {
            return 1;
        } else if (o.meleeWeapon != null) {
            return -1;
        }
        if (result != 0) return result;

        // Сравнение по главе
        if (chapter != null && o.chapter != null) {
            result = chapter.compareTo(o.chapter);
        } else if (chapter != null) {
            return 1;
        } else if (o.chapter != null) {
            return -1;
        }
        if (result != 0) return result;

        return 0;  // Все поля равны
    }

    //endregion

}
