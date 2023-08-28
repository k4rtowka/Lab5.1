package Commands;

import Models.CollectionManager;

import java.util.ArrayList;

public class CommandHelp extends Command {

    private ArrayList<Command> commands;

    public CommandHelp(CollectionManager collectionManager) {
        super(
                Titles.help,
                Descriptions.help,
                collectionManager,
                0);
        this.commands = new ArrayList<>();
        commands.add(this);
        commands.add(new CommandInfo(this.collectionManager));
        commands.add(new CommandShow(this.collectionManager));
        commands.add(new CommandInsert(this.collectionManager));
        commands.add(new CommandUpdate(this.collectionManager));
        commands.add(new CommandRemoveKey(this.collectionManager));
        commands.add(new CommandClear(this.collectionManager));
        commands.add(new CommandSave(this.collectionManager));
        //commands.add(new CommandExecuteScript(this.collectionManager));
        commands.add(new CommandExit(this.collectionManager));
        commands.add(new CommandRemoveHead(this.collectionManager));
        commands.add(new CommandAddIfMin(this.collectionManager));
        commands.add(new CommandHistory(this.collectionManager));
        commands.add(new CommandCountLessThanHealth(this.collectionManager));
        commands.add(new CommandFilterContainsName(this.collectionManager));
        commands.add(new CommandPrintFieldDescendingWeaponType(this.collectionManager));
    }

    public ArrayList<Command> GetCommands() {
        return this.commands;
    }

    public Command GetCommand(String name) {
        return this.commands.stream()
                .filter(command -> command.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Выполняет команду с хранилищем объектов
     *
     * @param params параметры команды
     * @return результат выполнения команды
     */
    @Override
    protected Object execute(Object[] params) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : this.commands) {
            stringBuilder.append(command);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
