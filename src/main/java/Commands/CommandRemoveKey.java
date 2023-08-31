package Commands;

import Models.CollectionManager;

public class CommandRemoveKey extends Command {
    public CommandRemoveKey(CollectionManager collectionManager) {
        super(Titles.removeKey, Descriptions.removeKey, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if(params[0] == null)
            throw new Exception("Не указан ID!");
        return collectionManager.removeKey((Integer) params[0]);
    }
}
