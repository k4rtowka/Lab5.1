package com.k4rtowka.lab5.CommandManagers.icem;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CommandManagers.Command;

public class ShowCommand extends Command {

    /**
     * Constructs a new command with the specified argument requirement.
     */
    public ShowCommand() {
        super(false);
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescr() {
        return "Show elements in collection | No args";
    }

    @Override
    public void execute() {
        if(checkArgument(this.getArgument())) {
            CollectionManager cm = CollectionManager.getInstance();
            if (cm.getSpaceMarineMap() != null) {
                if(cm.getSpaceMarineMap().size() > 0){
                    for (SpaceMarine spaceMarine : cm.getSpaceMarineMap().values()) {
                        System.out.println(spaceMarine);
                    }
                }else System.out.println("Collection is empty");
            } else System.out.println("Collection doesn't exist");
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null) return true;
        else{
            System.out.println("Command \"show\" doesn't have arguments!");
            return false;
        }
    }
}
