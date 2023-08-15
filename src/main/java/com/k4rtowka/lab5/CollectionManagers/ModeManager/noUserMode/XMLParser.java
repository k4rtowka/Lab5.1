package com.k4rtowka.lab5.CollectionManagers.ModeManager.noUserMode;

import com.k4rtowka.lab5.CollectionManagers.CollectionManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

public class XMLParser {

    public CollectionManager unmarshal(String path){
        try {
            File file = new File(path);
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (CollectionManager) unmarshaller.unmarshal(file);
        } catch (JAXBException | IllegalArgumentException e) {
            System.err.println("Ops.. " + e);
            return null;
        }
    }
}
