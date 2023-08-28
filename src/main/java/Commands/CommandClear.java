package Commands;

import Models.CollectionManager;

public class CommandClear extends Command {
    public CommandClear(CollectionManager collectionManager) {
        super(Titles.clear, Descriptions.clear, collectionManager, 0);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        collectionManager.clear();
        return true;
    }
}
