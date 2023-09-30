package Models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.sql.Timestamp;

@XmlRootElement(name = "spaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {

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

    private Integer userId;

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
    private Coordinate coordinate;

    /**
     * Дата создания.
     * <p>
     * Поле не может быть null.
     * Значение этого поля должно генерироваться автоматически.
     * </p>
     */
    //@XmlElement(required = true)
    private Timestamp creationDate;

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
     * Глава.
     * <p>
     * Поле может быть null.
     * </p>
     */
    @XmlElement(required = true)
    private Chapter chapter;

    /**
     * Категория Astartes SpaceMarine.
     */
    @XmlElement(required = true)
    private Astartes astartes;

    /**
     * Тип оружия SpaceMarine.
     */
    @XmlElement(required = true)
    private Weapon weapon;
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
     * @param weaponType   оружие ближнего боя
     * @param chapter      глава
     */
    public SpaceMarine(int id, String name, Coordinate coordinates, Timestamp creationDate,
                       Integer health, long heartCount, Category category,
                       WeaponType weaponType, Chapter chapter) throws Exception {
        this.setId(id);
        this.setName(name);
        this.setCoordinate(coordinates);
        this.setCreationDate(creationDate);
        this.setHealth(health);
        this.setHeartCount(heartCount);
        this.setAstartes(new Astartes(category));
        this.setWeapon(new Weapon(weaponType));
        this.setChapter(chapter);
    }

    public SpaceMarine(int id, int userId, String name, Coordinate coordinates,
                       Timestamp creationDate,
                       Integer health, long heartCount,
                       Category category,
                       WeaponType weaponType, Chapter chapter) throws Exception {
        this.setId(id);
        this.setUserId(userId);
        this.setName(name);
        this.setCoordinate(coordinates);
        this.setCreationDate(creationDate);
        this.setHealth(health);
        this.setHeartCount(heartCount);
        this.setAstartes(new Astartes(category));
        this.setWeapon(new Weapon(weaponType));
        this.setChapter(chapter);
    }

    public SpaceMarine(int id, int userId, String name, Coordinate coordinates,
                       Timestamp creationDate,
                       Integer health, long heartCount,
                       Astartes astartes,
                       Weapon weapon, Chapter chapter) throws Exception {
        this.setId(id);
        this.setUserId(userId);
        this.setName(name);
        this.setCoordinate(coordinates);
        this.setCreationDate(creationDate);
        this.setHealth(health);
        this.setHeartCount(heartCount);
        this.setAstartes(astartes);
        this.setWeapon(weapon);
        this.setChapter(chapter);
    }
    //endregion

    //region Сеттеры и геттеры

    //region ID
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID не может быть null или меньше или равен 0.");
        }
        this.id = id;
    }
    //endregion

    //region ID user
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    //endregion

    //region Name
    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name == null || name.isEmpty() || name.isBlank())
            throw new IllegalArgumentException("Имя не может быть пустым");
        this.name = name;
    }
    //endregion

    //region Coordinates
    public Coordinate getCoordinates() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) throws Exception {
        if (coordinate == null) {
            throw new IllegalArgumentException("Координаты не могут быть null.");
        }
        this.coordinate = coordinate;
    }
    //endregion

    //region CreationDate
    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Timestamp creationDate) throws Exception {
        if (creationDate == null) {
            throw new IllegalArgumentException("Дата создания не может быть null.");
        }
        this.creationDate = creationDate;
    }
    //endregion

    //region Health
    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        if (health != null && health <= 0) {
            throw new IllegalArgumentException("Здоровье, если указано, должно быть больше 0.");
        }
        this.health = health;
    }
    //endregion

    //region Hearts count

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
     * Возвращает количество сердец.
     *
     * @return количество сердец
     */
    public long getHeartCount() {
        return heartCount;
    }
    //endregion

    //region AstartesCategory

    public Category getCategory() throws Exception {
        if (this.astartes == null)
            throw new Exception("Не указан класс категории!");
        return this.astartes.getCategory();
    }

    public Astartes getAstartes() {
        return astartes;
    }

    public void setAstartes(Astartes astartes) throws Exception {
        if (astartes == null)
            throw new Exception("Категория Astartes не может быть null.");
        this.astartes = astartes;
    }
    //endregion

    //region WeaponType

//    public void setWeaponType(WeaponType meleeWeapon) {
//        this.meleeWeapon = meleeWeapon;
//    }

    public WeaponType getWeaponType() {
        return weapon.getType();
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    //endregion

    //region Chapter
    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
    //endregion

    //endregion

    //region Методы

    public String toString() {
        StringBuilder result = new StringBuilder();
        try {
            result.append("{");
            result.append("\"SpaceMarine id\": ").append(id).append(", ");
            result.append("\"User id\": ").append(this.userId).append(", ");
            result.append("\"SpaceMarine name\": \"").append(name).append("\", ");
            result.append("\"SpaceMarine coordinates\": ").append(coordinate).append(", ");
            result.append("\"SpaceMarine creation date\": \"").append(creationDate).append("\", \n");
            result.append("\"SpaceMarine health\": ").append((health == null) ? "\"not currently set\"" : health).append(", ");
            result.append("\"SpaceMarine heartCount\": ").append(heartCount).append(", ");
            result.append("\"SpaceMarine AstartesCategory\": \"").append(this.getCategory()).append("\", \n");
            result.append("\"SpaceMarine MeleeWeapon\": ").append((this.getWeaponType() == null) ? "\"not currently set\"" : "\"" + this.getWeaponType() + "\"").append(", ");
            result.append("\"SpaceMarine Chapter\": ").append((chapter == null) ? "\"not currently set\"" : "\"" + chapter + "\"");
            result.append("}");
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result.toString();
    }


    @Override
    public int compareTo(SpaceMarine o) {
        try {
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
            result = this.coordinate.compareTo(o.coordinate);
            if (result != 0) return result;

            // Сравнение по дате создания
            result = creationDate.compareTo(o.creationDate);
            if (result != 0) return result;

            // Сравнение по имени
            result = this.name.compareTo(o.name);
            if (result != 0) return result;

            // Сравнение по категории Astartes
            result = this.getCategory().compareTo(o.getCategory());
            if (result != 0) return result;

            // Сравнение по оружию ближнего боя
            if (this.getWeaponType() != null && o.getWeaponType() != null) {
                result = getWeaponType().compareTo(o.getWeaponType());
            } else if (getWeaponType() != null) {
                return 1;
            } else if (o.getWeaponType() != null) {
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
        } catch (Exception ex) {
            return -1;
        }
    }

    //endregion

}
