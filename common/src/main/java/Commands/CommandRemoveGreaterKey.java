package Commands;

import Models.CollectionManagerToFile;

public class CommandRemoveGreaterKey extends Command {
    public CommandRemoveGreaterKey(CollectionManagerToFile collectionManager) {
        super(Titles.removeGreaterKey, Descriptions.removeGreaterKey, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1) && this.CheckType(params[0], Integer.class)) {
            return collectionManager.removeGreaterKey(Integer.parseInt(params[0].toString()));
        }
        return null;
    }
}
