package com.k4rtowka.lab5.CommandManagers.icem;

import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.CommandManagers.CommandManager;

public class HelpCommand extends Command
{
    public HelpCommand(){
        super(false);
    }

    @Override
    public String getName() {
        return "Help";
    }

    @Override
    public String getDescr() {
        return "List of available commands | No args";
    }

    @Override
    public void execute() {
        CommandManager manager = new CommandManager();
        if(checkArgument(this.getArgument())){
            manager.getCommandMap().forEach((name, command) -> System.out.println(name + ": " + command.getDescr()));
        }
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null) return true;
        else{
            System.out.println("Command \"help\" doesn't have arguments!");
            setArgument(null);
            return false;
        }
    }
}

