package Commands;

import Models.CollectionManager;

public class CommandExecuteScript extends Command {
    public CommandExecuteScript(CollectionManager collectionManager) {
        super(Titles.executeScript, Descriptions.executeScript, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        return null;
    }
}
