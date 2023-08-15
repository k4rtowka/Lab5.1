package com.k4rtowka.lab5.CollectionManagers.validators;


public class InputValidator {

    boolean canBeNull = false;

    public String getDescr(){
        return "Validates user input";
    }

    public boolean validate(String value){
        if(!canBeNull)
            return !value.isEmpty() && !value.isBlank();
        return true;
    }

    public void canBeNull(boolean canBeNull){

        this.canBeNull = canBeNull;
    }
}
