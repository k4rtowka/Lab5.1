package Models;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

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
    private Integer id;

    /**
     * Название.
     * <p>
     * Поле не может быть null.
     * Строка не может быть пустой.
     * </p>
     */
    private String name;

    /**
     * Координаты местоположения.
     * <p>
     * Поле не может быть null.
     * </p>
     */
    private Coordinates coordinates;

    /**
     * Дата создания.
     * <p>
     * Поле не может быть null.
     * Значение этого поля должно генерироваться автоматически.
     * </p>
     */
    private ZonedDateTime creationDate;

    /**
     * Здоровье.
     * <p>
     * Поле может быть null.
     * Значение поля должно быть больше 0.
     * </p>
     */
    private Integer health;

    /**
     * Количество сердец.
     * <p>
     * Значение поля должно быть больше 0.
     * Максимальное значение поля: 3.
     * </p>
     */
    private long heartCount;

    /**
     * Категория Astartes.
     * <p>
     * Поле не может быть null.
     * </p>
     */
    private AstartesCategory category;

    /**
     * Оружие ближнего боя.
     * <p>
     * Поле может быть null.
     * </p>
     */
    private MeleeWeapon meleeWeapon;

    /**
     * Глава.
     * <p>
     * Поле может быть null.
     * </p>
     */
    private Chapter chapter;
    //endregion


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("SpaceMarine id: ").append(id).append('\n');
        result.append("SpaceMarine name: ").append(name).append('\n');
        result.append("SpaceMarine coordinates: ").append(coordinates).append('\n');
        result.append("SpaceMarine creation date: ").append(creationDate).append('\n');
        result.append("SpaceMarine health: ").append((health == null) ? "not currently set" : health).append('\n');
        result.append("SpaceMarine heartCount: ").append(heartCount).append('\n');
        result.append("SpaceMarine AstartesCategory: ").append(category).append('\n');
        result.append("SpaceMarine MeleeWeapon: ").append((meleeWeapon == null) ? "not currently set" : meleeWeapon).append('\n');
        result.append("SpaceMarine Chapter: ").append((chapter == null) ? "not currently set" : chapter).append('\n');

        return result.toString();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = ZonedDateTime.ofInstant(creationDate.toInstant(), ZoneId.systemDefault());
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public void setHeartCount(long heartCount) {
        this.heartCount = heartCount;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @XmlAttribute
    public Integer getId() {
        return id;
    }

    @XmlElement(required = true)
    public long getHeartCount() {
        return heartCount;
    }

    @XmlElement(required = true)
    public AstartesCategory getCategory() {
        return category;
    }

    @XmlElement
    public Integer getHealth() {
        return health;
    }

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    @XmlElement(name = "CreationDate")
    public Date getCreationDate() {
        return Date.from(creationDate.toInstant());
    }

    @XmlElement(required = true)
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @XmlElement(defaultValue = "Nothing")
    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    @XmlElement(defaultValue = "Nothing")
    public Chapter getChapter() {
        return chapter;
    }

    @Override
    public int compareTo(SpaceMarine o) {
        int result = Long.compare(heartCount, o.heartCount);
        if (health != null && o.health != null) {
            result = Integer.compare(health, o.health);
        } else if (health != null) result = 1;
        else if (result == 0) result = -1;
        if (result == 0) result = this.coordinates.compareTo(o.coordinates);
        if (result == 0) result = creationDate.compareTo(o.creationDate);
        return result;
    }
}
