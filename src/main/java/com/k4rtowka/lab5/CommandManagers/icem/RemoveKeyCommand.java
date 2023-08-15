package com.k4rtowka.lab5.CommandManagers.icem;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CollectionManagers.IdManager;
import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

public class RemoveKeyCommand extends Command {

    public RemoveKeyCommand(){
        super(true);
    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getDescr() {
        return "Remove element from collection | Has 1 arg";
    }

    @Override
    public void execute() throws BuildObjectException {
        if(checkArgument(this.getArgument())){
            CollectionManager manager = CollectionManager.getInstance();
            if(manager.getSpaceMarineMap() == null){
                System.out.println("This command doesn't work right now");
                return;
            }

            Integer id = IdManager.validateInput((String) this.getArgument());
            if(id == null) return;

            SpaceMarine spaceMarine = IdManager.checkById(id);
            if(spaceMarine != null){
                manager.getSpaceMarineMap().remove(id);
                System.out.println("Element with id \"" + id + "\" removed.");
            }else System.out.println("Element with this id doesn't exist!");
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null){
            System.out.println("Remove_key has 1 argument!");
            return false;
        }return true;
    }
}
