package com.k4rtowka.lab5.CollectionManagers.validators;

public class healthValidator implements Validator<Integer>{

    @Override
    public String getDescr() {
        return "value should be greater than 0 or null";
    }

    @Override
    public boolean validate(Integer value) {
        return value > 0 && value < Integer.MAX_VALUE;
    }
}
