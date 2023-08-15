package com.k4rtowka.lab5.Exceptions;

public class UnknownCommand extends Exception{

    public UnknownCommand(String message){
        super(message);
    }
}
