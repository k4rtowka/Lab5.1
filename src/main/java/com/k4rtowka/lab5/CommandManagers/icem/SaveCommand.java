package com.k4rtowka.lab5.CommandManagers.icem;


import com.k4rtowka.lab5.CommandManagers.Command;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;


public class SaveCommand extends Command {

    public SaveCommand() {
        super(false);
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescr() {
        return "save";
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        return false;
    }

    @Override
    public void execute() throws BuildObjectException {
    }
}
