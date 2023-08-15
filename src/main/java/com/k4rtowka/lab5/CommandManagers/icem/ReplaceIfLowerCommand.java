package com.k4rtowka.lab5.CommandManagers.icem;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CollectionManagers.IdManager;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.ModeManager;
import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

public class ReplaceIfLowerCommand extends Command {

    private ModeManager<SpaceMarine> handler;

    public ReplaceIfLowerCommand(){
        super(true);
    }

    public ReplaceIfLowerCommand(ModeManager<SpaceMarine> handler){
        super(true);
        this.handler = handler;
    }

    @Override
    public String getName() {
        return "replace_if_lower";
    }

    @Override
    public String getDescr() {
        return "Replace if lower than specific element | 1 arg";
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
            if(spaceMarine != null) {
                SpaceMarine spaceMarineNew = handler.buildObject();
                if(spaceMarineNew.compareTo(manager.getSpaceMarineMap().get(id)) < 0){
                    spaceMarineNew.setId(id);
                    manager.getSpaceMarineMap().replace(id, spaceMarineNew);
                    System.out.println("Changed!");
                }else System.out.println("Didn't changed, because new element lower >= old");
            }
            else {
                System.out.println("Element with this id doesn't exist!");
            }
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null){
            System.out.println("remove_if_lower has 1 argument!");
            return false;
        }return true;
    }
}
