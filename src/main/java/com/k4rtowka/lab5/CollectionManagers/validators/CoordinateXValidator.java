package com.k4rtowka.lab5.CollectionManagers.validators;

public class CoordinateXValidator implements Validator<Double>{

    @Override
    public String getDescr() {
        return "\"X\" > -877 (type: Double)";
    }

    @Override
    public boolean validate(Double value) {
        return value > -877 && value <= Double.MAX_VALUE;
    }
}
