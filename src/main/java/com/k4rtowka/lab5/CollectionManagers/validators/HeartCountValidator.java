package com.k4rtowka.lab5.CollectionManagers.validators;

public class HeartCountValidator implements Validator<Long>{

    @Override
    public String getDescr() {
        return "value between 1 and 3";
    }

    @Override
    public boolean validate(Long value) {
        return value > 0 && value <= 3;
    }
}
