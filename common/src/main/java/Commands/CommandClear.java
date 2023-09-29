package Commands;

import Models.CollectionManagerToFile;

public class CommandClear extends Command {
    public CommandClear(CollectionManagerToFile collectionManager) {
        super(Titles.clear, Descriptions.clear, collectionManager, 0);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 0)) {
            collectionManager.clear();
            return "Коллекция очищена!";
        }
        return null;
    }
}
