package Commands;

import Models.CollectionManager;

public class CommandRemoveGreaterKey extends Command {
    public CommandRemoveGreaterKey(CollectionManager collectionManager) {
        super(Titles.removeGreaterKey, Descriptions.removeGreaterKey, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if(params[0] == null)
            throw new Exception("Не указан ID!");
        collectionManager.removeGreaterKey((Integer) params[0]);
        return null;
    }
}
