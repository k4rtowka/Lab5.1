package com.k4rtowka.lab5.CollectionManagers.validators;

public class NameValidator implements Validator<String>{

    @Override
    public String getDescr() {
        return "Validates name field";
    }

    @Override
    public boolean validate(String value) {
        return !value.isEmpty() && !value.isBlank();
    }
}
