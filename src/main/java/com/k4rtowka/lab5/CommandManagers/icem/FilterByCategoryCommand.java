package com.k4rtowka.lab5.CommandManagers.icem;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.userMode.CategoryCLIManager;
import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

public class FilterByCategoryCommand extends Command {

    public FilterByCategoryCommand(){
        super(false);
    }

    @Override
    public String getName() {
        return "filter_by_category";
    }

    @Override
    public String getDescr() {
        return "Outputs elements equal to the selected category | No args";
    }

    @Override
    public void execute() throws BuildObjectException {
        if(checkArgument(this.getArgument())){
            CollectionManager cm = CollectionManager.getInstance();
            if(cm.getSpaceMarineMap() == null){
                System.out.println("This command doesn't work right now");
                return;
            }
            boolean flag = false;
            CategoryCLIManager categoryCLIManager = new CategoryCLIManager();
            for(SpaceMarine el : cm.getSpaceMarineMap().values()){
                if(categoryCLIManager.buildObject().equals(el.getCategory())){
                    flag = true;
                    System.out.println(el);
                }
            }
            if(!flag) System.out.println("There are no elements with the specified value");
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null) return true;
        else{
            System.out.println("Command \"filter_by_category\" doesn't have arguments!");
            return false;
        }
    }
}
