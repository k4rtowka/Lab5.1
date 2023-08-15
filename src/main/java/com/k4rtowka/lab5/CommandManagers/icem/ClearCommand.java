package com.k4rtowka.lab5.CommandManagers.icem;

import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CommandManagers.Command;

public class ClearCommand extends Command {

    public ClearCommand(){
        super(false);
    }

    @Override
    public String getName() {
        return "Clear";
    }

    @Override
    public String getDescr() {
        return "Clear collection | No args";
    }

    @Override
    public void execute() {
        if(checkArgument(getArgument())){
            CollectionManager cm = CollectionManager.getInstance();
            if(cm.getSpaceMarineMap() != null){
                System.out.println("Collection clear");
                cm.getSpaceMarineMap().clear();
            }else
                System.out.println("Collection is empty");
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null) return true;
        else{
            System.out.println("Command \"clear\" doesn't have arguments!");
            return false;
        }
    }
}
