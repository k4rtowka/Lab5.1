package com.k4rtowka.lab5.CommandManagers.icem;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

public class PrintDescendingCommand extends Command {

    public PrintDescendingCommand(){
        super(false);
    }

    @Override
    public String getName() {
        return "print_descending";
    }

    @Override
    public String getDescr() {
        return "print_descending show the elements in descending order | No args";
    }

    @Override
    public void execute() throws BuildObjectException {
        if(checkArgument(this.getArgument())) {
            CollectionManager cm = CollectionManager.getInstance();
            if (cm.getSpaceMarineMap() != null) {
                if(cm.getSpaceMarineMap().size() > 0){
                    for (SpaceMarine spaceMarine : cm.getSpaceMarineMap().descendingMap().values()) {
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
            System.out.println("Command \"print_descending\" doesn't have arguments!");
            return false;
        }
    }
}
