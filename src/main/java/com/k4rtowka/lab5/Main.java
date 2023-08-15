package com.k4rtowka.lab5;

import com.k4rtowka.lab5.CollectionManagers.CollectionManager;
import com.k4rtowka.lab5.CommandManagers.CommandExecutor;
import com.k4rtowka.lab5.CommandManagers.CommandMode;



public class Main {

    public static void main(String[] args) {
        CollectionManager.loadCollection();
        CommandExecutor commandExecutor = new CommandExecutor();
        CommandMode commandMode = CommandMode.CLI_UserMode;
        commandExecutor.startExecuting(System.in, commandMode);

    }
}


