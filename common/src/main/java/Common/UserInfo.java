package Common;

import java.io.Serializable;

public class UserInfo implements Serializable {
    //region Поля
    private int id;
    private boolean isAuthorized;
    //endregion

    //region Конструкторы
    public UserInfo(int id, boolean isAuthorized) {
        this.id = id;
        this.isAuthorized = isAuthorized;
    }

    public UserInfo(int id) {
        this(id, false);
    }
    //endregion

    //region Сеттеры и геттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    //endregion
}
