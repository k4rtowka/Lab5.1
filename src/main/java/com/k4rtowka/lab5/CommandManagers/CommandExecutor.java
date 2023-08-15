package com.k4rtowka.lab5.CommandManagers;

import java.io.InputStream;
import java.util.Scanner;

public class CommandExecutor {

    public void startExecuting(InputStream input, CommandMode mode) {
        Scanner cmdScanner = new Scanner(input);
        CommandManager commandManager = new CommandManager(cmdScanner, mode);
        System.out.println();
        while (cmdScanner.hasNext()) {
            String line = cmdScanner.nextLine().trim();
            if (line.isEmpty()) continue;
            commandManager.executeCommand(line.split(" ", 2));
        }
    }
}
