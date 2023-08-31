package Commands;

import Models.CollectionManager;

public class CommandPrintDescending extends Command {
    public CommandPrintDescending(CollectionManager collectionManager) {
        super(Titles.printDescending, Descriptions.printDescending, collectionManager, 0);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        return collectionManager.printDescending();
    }
}
