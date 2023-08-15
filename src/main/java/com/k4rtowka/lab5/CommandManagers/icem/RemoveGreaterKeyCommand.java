package com.k4rtowka.lab5.CommandManagers.icem;

import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CollectionManagers.IdManager;
import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

import java.util.Iterator;

public class RemoveGreaterKeyCommand extends Command {

    public RemoveGreaterKeyCommand(){
        super(true);
    }

    @Override
    public String getName() {
        return "remove_greater_key";
    }

    @Override
    public String getDescr() {
        return "remove all elements greater than specific argument | 1 args";
    }

    @Override
    public void execute() throws BuildObjectException {
        if(checkArgument(this.getArgument())){
            CollectionManager cm = CollectionManager.getInstance();
            if(cm.getSpaceMarineMap() == null){
                System.out.println("This command doesn't work right now");
                return;
            }

            Integer id = IdManager.validateInput((String) this.getArgument());
            if(id == null) return;

            Iterator<Integer> iter = cm.getSpaceMarineMap().keySet().iterator();
            int count = 0;
            while(iter.hasNext()){
                if(id < iter.next()){
                    iter.remove();
                    count++;
                }
            }
            System.out.println(count + " objects removed");
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null){
            System.out.println("remove_greater_key has 1 argument!");
            return false;
        }return true;
    }
}
