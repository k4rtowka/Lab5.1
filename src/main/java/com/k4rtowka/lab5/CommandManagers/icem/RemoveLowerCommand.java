package com.k4rtowka.lab5.CommandManagers.icem;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.ModeManager;
import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

import java.util.Iterator;

public class RemoveLowerCommand extends Command {

    private ModeManager<SpaceMarine> handler;

    public RemoveLowerCommand(){
        super(false);
    }

    public RemoveLowerCommand(ModeManager<SpaceMarine> handler){
        super(false);
        this.handler = handler;
    }

    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public String getDescr() {
        return "remove all elements lower than specific argument | No args";
    }

    @Override
    public void execute() throws BuildObjectException {
        if(checkArgument(this.getArgument())){
            CollectionManager cm = CollectionManager.getInstance();
            if(cm.getSpaceMarineMap() == null){
                System.out.println("This command doesn't work right now");
                return;
            }

            SpaceMarine spaceMarineNew = handler.buildObject();
            Iterator<SpaceMarine> iter = cm.getSpaceMarineMap().values().iterator();

            int count = 0;
            while(iter.hasNext()) {
                if (spaceMarineNew.compareTo(iter.next()) > 0) {
                    iter.remove();
                    count++;
                }
            }
            System.out.println(count + " objects removed");
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null) return true;
        else{
            System.out.println("Command \"remove_lower\" doesn't have arguments!");
            return false;
        }
    }
}
