package com.k4rtowka.lab5.CommandManagers.icem;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CollectionManagers.validators.HeartCountValidator;
import com.k4rtowka.lab5.CollectionManagers.validators.Validator;
import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

public class CountByHeartCountCommand extends Command {

    public CountByHeartCountCommand(){
        super(true);
    }

    @Override
    public String getName() {
        return "count_by_heart_count";
    }

    @Override
    public String getDescr() {
        return "Outputs the number of elements with the specified parameter | Has 1 arg";
    }

    @Override
    public void execute() throws BuildObjectException {
        if(checkArgument(this.getArgument())){
            CollectionManager cm = CollectionManager.getInstance();
            if(cm.getSpaceMarineMap() == null){
                System.out.println("This command doesn't work right now");
                return;
            }

            try{
                Long heartCount = Long.parseLong((String) this.getArgument());
                Validator<Long> heartCountValidator = new HeartCountValidator();
                int count = 0;
                if(heartCountValidator.validate(heartCount)){
                    for(SpaceMarine el : cm.getSpaceMarineMap().values()){
                        if(heartCount.equals(el.getHeartCount())){
                            count++;
                        }
                    }
                    System.out.println("Count: " + count);
                }else{
                    System.out.println("Value violates restrictions for field! Try again.");
                    System.out.println("Restrictions: " + heartCountValidator.getDescr());
                }
            }catch (NumberFormatException e){
                System.out.println("Wrong input!");
            }

        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null){
            System.out.println("count_by_heart_count has 1 argument!");
            return false;
        }return true;
    }
}
