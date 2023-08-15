package com.k4rtowka.lab5.CommandManagers.icem;

import com.k4rtowka.lab5.CommandManagers.Command;

public class ExitCommand extends Command {

    public ExitCommand(){
        super(false);
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescr() {
        return "End of program without save | No args";
    }

    @Override
    public void execute() {
        if(checkArgument(getArgument())){
            System.out.println("Closing program...");
            System.exit(0);
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null)
            return true;
        else{
            System.out.println("Command \"exit\" doesn't have arguments!");
            return false;
        }
    }
}
