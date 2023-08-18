package com.k4rtowka.lab5.CommandManagers;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.ModeManager;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.userMode.SpaceMarineCLIManager;
import com.k4rtowka.lab5.CommandManagers.icem.*;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;
import com.k4rtowka.lab5.Exceptions.UnknownCommand;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Scanner;

public class CommandManager {
    LinkedHashMap<String, Command> commandMap;

    public CommandManager(Scanner scanner, CommandMode mode){
        commandMap = new LinkedHashMap<>();
        commandMap.put("help", new HelpCommand());
        commandMap.put("info", new InfoCommand());
        commandMap.put("show", new ShowCommand());
        commandMap.put("remove_key", new RemoveKeyCommand());
        commandMap.put("clear", new ClearCommand());
        commandMap.put("save", new SaveCommand());
        commandMap.put("execute_script", new Zaglushka());
        commandMap.put("exit", new ExitCommand());
        commandMap.put("remove_greater_key", new RemoveGreaterKeyCommand());
        commandMap.put("count_by_heart_count", new CountByHeartCountCommand());
        commandMap.put("filter_by_category", new FilterByCategoryCommand());
        commandMap.put("print_descending", new PrintDescendingCommand());
        ModeManager<SpaceMarine> handler = null;
        switch (mode){
            case CLI_UserMode -> handler = new SpaceMarineCLIManager();
            case NonUserMode -> System.out.println("change mode");
        }
        commandMap.put("insert", new AddCommand(handler));
        commandMap.put("update", new UpdateCommand(handler));
        commandMap.put("remove_lower", new RemoveLowerCommand(handler));
        commandMap.put("replace_if_lower", new ReplaceIfLowerCommand(handler));
    }

    public CommandManager(){
        commandMap = new LinkedHashMap<>();
        commandMap.put("help", new HelpCommand());
        commandMap.put("info", new InfoCommand());
        commandMap.put("show", new ShowCommand());
        commandMap.put("insert", new AddCommand());
        commandMap.put("update", new UpdateCommand());
        commandMap.put("remove_key", new RemoveKeyCommand());
        commandMap.put("clear", new ClearCommand());
        commandMap.put("save", new SaveCommand());
        commandMap.put("execute_script", new Zaglushka());
        commandMap.put("exit", new ExitCommand());
        commandMap.put("remove_lower", new RemoveLowerCommand());
        commandMap.put("replace_if_lower", new ReplaceIfLowerCommand());
        commandMap.put("remove_grater_key", new RemoveGreaterKeyCommand());
        commandMap.put("count_by_heart_count", new CountByHeartCountCommand());
        commandMap.put("filter_by_category", new FilterByCategoryCommand());
        commandMap.put("print_descending", new PrintDescendingCommand());
    }

    public void executeCommand(String[] args){
        try {
            Optional.ofNullable(commandMap.get(args[0])).orElseThrow(() -> new UnknownCommand("UnknownCommand. Use \"help\" to check list commands")).setArgument(null);
            if (args.length > 1)
                Optional.ofNullable(commandMap.get(args[0])).orElseThrow().setArgument(args[1]);
            //Optional.ofNullable(commandMap.get(args[0])).orElseThrow().execute();
            commandMap.get(args[0]).execute();
        }catch (UnknownCommand | BuildObjectException e ){
            System.err.println(e.getMessage());
        }
    }

    public LinkedHashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
