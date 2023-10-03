package Models;

import Commands.Command;
import Common.UserInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс Data содержит команды и данные для пересылки информации между сервером и клиентом.
 */
public class Data implements Serializable {
    //region Поля
    /**
     * Информация о пользователе
     */
    private UserInfo userInfo;
    /**
     * Команда, которая будет выполнена на сервере или клиенте.
     */
    private Command command;
    /**
     * Данные, связанные с командой.
     */
    private Object[] params;
    //endregion

    //region Конструкторы
    public Data(UserInfo userInfo, Command command, Object[] data) {
        this.userInfo = userInfo;
        this.command = command;
        this.params = data;
    }

    public Data(UserInfo userInfo, Command command, Object data) {
        this(userInfo, command, new Object[]{data});
    }
    //endregion

    //region Геттеры и сеттеры
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    /**
     * Получает объект из params по заданному индексу.
     *
     * @param index индекс объекта в массиве
     * @return объект из массива params
     */
    public Object getParams(int index) {
        if (params == null || index >= params.length || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index or params is empty.");
        }
        return params[index];
    }

    /**
     * Задает объект в params по заданному индексу.
     *
     * @param index индекс, по которому будет установлен объект
     * @param obj   объект для установки
     */
    public void getParams(int index, Object obj) {
        if (params == null || index >= params.length || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index or params is empty.");
        }
        params[index] = obj;
    }
    //endregion

    //region Методы

    /**
     * Добавляет новый объект в поле params.
     *
     * @param obj объект для добавления
     */
    public void Add(Object obj) {
        if (params == null) {
            params = new Object[]{obj};
            return;
        }

        Object[] newParams = new Object[params.length + 1];
        System.arraycopy(params, 0, newParams, 0, params.length);
        newParams[newParams.length - 1] = obj;
        params = newParams;
    }

    /**
     * Добавляет массив объектов в поле params.
     *
     * @param objects массив объектов для добавления
     */
    public void Add(Object[] objects) {
        if (params == null) {
            params = objects;
            return;
        }

        ArrayList<Object> combined = new ArrayList<>();
        combined.addAll(Arrays.asList(params));
        combined.addAll(Arrays.asList(objects));
        params = combined.toArray();
    }

    /**
     * Возвращает строковое представление объекта Data.
     *
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return "Data{" +
                "userInfo=" + userInfo +
                ", command=" + command +
                ", data=" + java.util.Arrays.toString(params) +
                '}';
    }
    //endregion
}
