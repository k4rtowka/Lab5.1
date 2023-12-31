package Models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "chapter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Chapter implements Comparable<Chapter>, Serializable {

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
        int result = 0;
        if (this.name != null & o.name != null) {
            result = name.compareTo(o.getName());
        }else if(this.name == null & o.name != null){
            result = -1;
        }else if(this.name != null)
            result = 1;

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
