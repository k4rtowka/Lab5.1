package Models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "chapter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Chapter implements Comparable<Chapter> {

    //region Поля
    /**
     * Название части.
     * <p>
     * Поле не может быть null.
     * Строка не может быть пустой.
     * </p>
     */
    @XmlElement(required = true)
    private String name;

    /**
     * Количество marines.
     * <p>
     * Поле не может быть null.
     * Значение поля должно быть больше 0.
     * Максимальное значение: 1000
     * </p>
     */
    @XmlElement(required = true)
    private long marinesCount;
    //endregion

    /**
     * Создает главу с указанными параметрами
     *
     * @param name         Имя главы
     * @param marinesCount Количество морпехов
     */
    //region Конструкторы
    public Chapter(String name, long marinesCount) {
        this.setName(name);
        this.setMarinesCount(marinesCount);
    }

    public Chapter() {
    }
    //endregion

    //region Сеттеры

    /**
     * Устанавливает название части.
     *
     * @param name название части.
     * @throws IllegalArgumentException если name равно null или пустое.
     */
    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Имя не может быть null или пустым.");
        this.name = name;
    }

    /**
     * Устанавливает количество marines.
     *
     * @param marinesCount количество marines.
     * @throws IllegalArgumentException если marinesCount меньше 1 или больше 1000
     */
    public void setMarinesCount(long marinesCount) {
        if (marinesCount < 1 || marinesCount > 1000)
            throw new IllegalArgumentException("Marines");
        this.marinesCount = marinesCount;
    }
    //endregion

    //region Геттеры

    /**
     * Возвращает название части.
     *
     * @return название части.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает количество marines
     *
     * @return количество marines.
     */
    public long getMarinesCount() {
        return marinesCount;
    }
    //endregion

    //region Методы
    @Override
    public int compareTo(Chapter o) {
        int result = name.compareTo(o.getName());
        if (result != 0)
            return result;
        return Long.compare(marinesCount, o.getMarinesCount());
    }

    @Override
    public String toString() {
        return "Chapter: " +
                "name = " + name +
                ", marinesCount = " + marinesCount;
    }
    //endregion
}
