package com.k4rtowka.lab5.CommandManagers.icem;

import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CommandManagers.Command;

public class InfoCommand extends Command {

    public InfoCommand(){
        super(false);
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescr() {
        return "Show info about collection | No args";
    }

    @Override
    public void execute() {
        if(checkArgument(getArgument())){
            CollectionManager cm = CollectionManager.getInstance();
            if(cm.getSpaceMarineMap() != null) System.out.println(cm.toString());
            else System.out.println("Collection doesn't exist.");
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null) return true;
        else{
            System.out.println("Command \"info\" doesn't have arguments!");
            return false;
        }
    }
}
