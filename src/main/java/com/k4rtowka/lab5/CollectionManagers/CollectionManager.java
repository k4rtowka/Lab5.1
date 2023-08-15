package com.k4rtowka.lab5.CollectionManagers;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.noUserMode.XMLParser;

import javax.xml.bind.annotation.*;
import java.util.NavigableMap;
import java.util.TreeMap;

@XmlRootElement(name = "Models")
public class CollectionManager{

    private static CollectionManager singletonPattern;

    private NavigableMap<Integer, SpaceMarine> spaceMarineMap;

    private static String pathToDataFile;

    public static CollectionManager getInstance() {
        if (singletonPattern == null)
            singletonPattern = new CollectionManager();
        return singletonPattern;
    }



    public void add(SpaceMarine s){
        if(spaceMarineMap != null){
            spaceMarineMap.put(s.getId(), s);
        }else{
         TreeMap<Integer, SpaceMarine> spaceMarines = new TreeMap<>();
         spaceMarines.put(s.getId(), s);
         CollectionManager.getInstance().setSpaceMarineMap(spaceMarines);
        }
    }

    @XmlElement
    @XmlElementWrapper(name = "SpaceMarines")
    public NavigableMap<Integer, SpaceMarine> getSpaceMarineMap() {
        return spaceMarineMap;
    }

    public void setSpaceMarineMap(NavigableMap<Integer, SpaceMarine> spaceMarineMap){
        this.spaceMarineMap = spaceMarineMap;
    }

    public static void loadCollection(){
        XMLParser parser = new XMLParser();
        singletonPattern = parser.unmarshal("./test.xml");
        System.out.println("Collection loaded");
    }


    @Override
    public String toString() {
        return  "Collection type: TreeMap\n" +
                "Number of elements: " + spaceMarineMap.size() +
                "\nFields:[id,name,coordinates,creationDate,health,heartCount,category,meleeWeapon,chapter]";
    }

}
