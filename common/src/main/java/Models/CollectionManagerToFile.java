package Models;

import Common.UserInfo;
import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.*;

@XmlRootElement(name = "CollectionManagerToFile")
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionManagerToFile extends CollectionManager
//        implements Comparable<CollectionManagerToFile>
{

    //region Поля
    /**
     * Файл с данными
     */
    @XmlElement
    private File dataFile;
    //endregion

    //region Конструкторы

    /**
     * Конструктор с параметром.
     *
     * @param filename путь к файлу
     * @throws Exception если не правильный путь к файлу.
     */
    public CollectionManagerToFile(String filename) throws Exception {
        super();
        dataFile = new File(filename);
        if (dataFile.exists() && dataFile.length() != 0L)
            Load();
    }

    public CollectionManagerToFile() throws Exception {
        super();
        dataFile = null;
    }
    //endregion

    //region Методы

    /**
     * Сохранить коллекцию в файл
     */
    public boolean Save() throws Exception {
        try {
            JAXBContext context = JAXBContext.newInstance(CollectionManagerToFile.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Для красивого форматирования XML
            // Удаляем файл, если он существует
            if (this.dataFile.exists()) {
                this.dataFile.delete();
            }

            marshaller.marshal(this, dataFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Загрузка из файла
     */
    public boolean Load() throws Exception {
        try {
            JAXBContext context = JAXBContext.newInstance(CollectionManagerToFile.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Загружаем данные из файла
            CollectionManagerToFile loadedManager = (CollectionManagerToFile) unmarshaller.unmarshal(dataFile);
            // Копирование данных из загруженного объекта в текущий
            this.marines = loadedManager.marines;
            this.initializationDate = loadedManager.initializationDate;
            this.lastId = loadedManager.lastId;
            this.dataFile = loadedManager.dataFile;
            this.executedScripts = loadedManager.executedScripts;

        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Ошибка при загрузке данных из XML файла.");
        }
        return true;
    }

    /**
     * Аутентифицирует пользователя по заданному логину и паролю.
     *
     * @param login    Логин пользователя.
     * @param password Пароль пользователя.
     * @return Аутентифицированный пользователь.
     * @throws Exception Если произошла ошибка в процессе аутентификации.
     */
    @Override
    public UserInfo Login(String login, String password) throws Exception {
        return null;
    }

    /**
     * Регистрирует нового пользователя с заданным логином и паролем.
     *
     * @param login    Логин пользователя.
     * @param password Пароль пользователя.
     * @return Зарегистрированный пользователь.
     * @throws Exception Если произошла ошибка в процессе регистрации.
     */
    @Override
    public UserInfo Register(String login, String password) throws Exception {
        return null;
    }

//    @Override
//    public int compareTo(CollectionManagerToFile other) {
//        int result = initializationDate.compareTo(other.initializationDate);
//        if (result != 0) return result;
//        result = dataFile.compareTo(other.dataFile);
//        if (result != 0) return result;
//        result = Integer.compare(lastId, other.lastId);
//        if (result != 0) return result;
//        // Сравнение коллекций (по содержимому)
//        Iterator<Map.Entry<Integer, SpaceMarine>> thisIterator = this.marines.entrySet().iterator();
//        Iterator<Map.Entry<Integer, SpaceMarine>> otherIterator = other.marines.entrySet().iterator();
//        while (thisIterator.hasNext() && otherIterator.hasNext()) {
//            Map.Entry<Integer, SpaceMarine> thisEntry = thisIterator.next();
//            Map.Entry<Integer, SpaceMarine> otherEntry = otherIterator.next();
//            // Сравниваем ключи (ID)
//            result = thisEntry.getKey().compareTo(otherEntry.getKey());
//            if (result != 0) {
//                return result;
//            }
//            // Сравниваем значения (SpaceMarines)
//            result = thisEntry.getValue().compareTo(otherEntry.getValue());
//            if (result != 0) {
//                return result;
//            }
//        }
//        // Если одна из коллекций длиннее другой
//        if (thisIterator.hasNext()) {
//            return 1;
//        } else if (otherIterator.hasNext()) {
//            return -1;
//        }
//        return 0;
//    }


    //endregion

}
