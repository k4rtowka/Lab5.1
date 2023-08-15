package com.k4rtowka.lab5.CommandManagers.icem;

import Models.SpaceMarine;
import com.k4rtowka.lab5.Collection.*;
import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.ModeManager;
import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;



public class AddCommand extends Command {
    ModeManager<SpaceMarine> handler;


    public AddCommand(){
        super(false);
    }

    public AddCommand(ModeManager<SpaceMarine> handler){
        super(false);
        this.handler = handler;
    }

    @Override
    public String getDescr() {
        return "Add new element to collection | No args";
    }

    @Override
    public void execute() throws BuildObjectException {

        if(checkArgument(this.getArgument())){
            CollectionManager collectionManager = CollectionManager.getInstance();
            collectionManager.add(handler.buildObject());
            System.out.println("New item has been added to the collection");
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if (inputArgument == null)
            return true;
        else {
            System.out.println("Add has no arguments!");
            return false;
        }
    }
}
