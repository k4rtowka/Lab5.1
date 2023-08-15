package com.k4rtowka.lab5.CollectionManagers.validators;

public interface Validator<T> {

    boolean canBeNull = false;

    String getDescr();

    boolean validate(T value);
}
