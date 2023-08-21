package Models;


public class Coordinates implements Comparable<Coordinates> {

    //region Поля
    /**
     * Координата Х.
     * <p>
     * Значение поля должно быть больше -877.
     * </p>
     */
    private Double x;

    /**
     * Координата Y.
     * <p>
     * Поле не может быть null.
     * </p>
     */
    private Integer y;
    //endregion

    //region Конструкторы
    /**
     * Создает координаты с указанными параметрами.
     *
     * @param x координата Х
     * @param y координата Y
     */
    public Coordinates(Double x, Integer y) {
        this.setX(x);
        this.setY(y);
    }

    public Coordinates() {}
    //endregion

    //region Сеттеры

    /**
     * Устанавливает координату X.
     *
     * @param x координата X
     * @throws IllegalArgumentException если X равен null или меньше -877
     */
    public void setX(Double x) {
        if(x == null || x < -877)
            throw new IllegalArgumentException("X не может быть null или меньше -877");
        this.x = x;
    }

    /**
     * Устанавливает координату Y.
     *
     * @param y координата Y
     * @throws IllegalArgumentException если Y равен null
     */
    public void setY(Integer y) {
        if(y == null)
            throw new IllegalArgumentException("Y не может быть null");
        this.y = y;
    }
    //endregion

    //region Геттеры

    /**
     * Возвращает координату Х.
     *
     * @return координата Х
     */
    public Double getX() {
        return x;
    }

    /**
     * Возвращает координату Y.
     *
     * @return координата Y
     */
    public Integer getY() {
        return y;
    }
    //endregion

    //region Методы
    @Override
    public String toString() {
        return "Coordinates: " +
                "x = " + x +
                ", y = " + y;
    }

    @Override
    public int compareTo(Coordinates o) {
        //Сравнение по координате Х
        int result = Double.compare(this.x, o.x);
        if (result != 0) return result;

        //Сравнение по координате Y
        return Integer.compare(this.y, o.y);
    }
    //endregion
}

