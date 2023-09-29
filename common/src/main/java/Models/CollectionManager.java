package Models;


import Tests.Data.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.TreeMap;

@XmlRootElement(name = "CollectionManagerToFile")
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionManager {
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
     * Файл с данными
     */
    @XmlElement
    protected File dataFile;
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
}
