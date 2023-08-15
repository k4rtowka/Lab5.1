package com.k4rtowka.lab5.CommandManagers.icem;


import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;

public class SaveCommand extends Command {

    public SaveCommand() {
        super(false);
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescr() {
        return "save";
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        return false;
    }

    @Override
    public void execute() throws BuildObjectException {
        CollectionManager collectionManager = CollectionManager.getInstance();
        File file = new File("./test.xml");
        /*XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            mapper.writeValue(file, collectionManager.getSpaceMarineMap().values());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        try {
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(collectionManager, file);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Saved");
    }
}
