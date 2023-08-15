package com.k4rtowka.lab5.CommandManagers.icem;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CollectionManagers.IdManager;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.ModeManager;
import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

public class UpdateCommand extends Command {

    ModeManager<SpaceMarine> handler;

    public UpdateCommand(){
        super(true);
    }

    public UpdateCommand(ModeManager<SpaceMarine> handler){
        super(true);
        this.handler = handler;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescr() {
        return "Update element by id | Has 1 arg";
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
            if(spaceMarine != null) manager.getSpaceMarineMap().remove(id);
            else {
                System.out.println("Element with this id doesn't exist!");
                return;
            }

            SpaceMarine spaceMarineNew = handler.buildObject();
            spaceMarineNew.setId(id);

            manager.add(spaceMarineNew);

            System.out.println("Updated!");
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null){
            System.out.println("Update has 1 argument!");
            return false;
        }return true;
    }
}
