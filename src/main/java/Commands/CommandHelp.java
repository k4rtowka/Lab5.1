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
        commands.add(new CommandExecuteScript(this.collectionManager));
        commands.add(new CommandExit(this.collectionManager));
        commands.add(new CommandWait(this.collectionManager));
        commands.add(new CommandRemoveLower(this.collectionManager));
        commands.add(new CommandReplaceIfLower(this.collectionManager));
        commands.add(new CommandRemoveGreaterKey(this.collectionManager));
        commands.add(new CommandCountByHeartCount(this.collectionManager));
        commands.add(new CommandFilterByCategory(this.collectionManager));
        commands.add(new CommandPrintDescending(this.collectionManager));
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
        if (this.CheckParams(params, 0)) {
            for (Command command : this.commands) {
                stringBuilder.append(command);
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
