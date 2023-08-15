package com.k4rtowka.lab5.CommandManagers.icem;

import com.k4rtowka.lab5.CommandManagers.Command;

public class Zaglushka extends Command {
    public Zaglushka(){
        super(false);
    }

    @Override
    public String getName() {
        return "null";
    }

    @Override
    public String getDescr() {
        return "null";
    }

    @Override
    public void execute() {
        System.out.println("Не сделал:(");
    }

    @Override
    public boolean checkArgument(Object inputArgument) {
        if(inputArgument == null)
            return true;
        else{
            System.out.println("Exit hasn't arguments");
            return false;
        }
    }
}
