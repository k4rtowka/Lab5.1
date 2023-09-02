package Commands;

import Models.AstartesCategory;
import Models.CollectionManager;

public class CommandRemoveGreaterKey extends Command {
    public CommandRemoveGreaterKey(CollectionManager collectionManager) {
        super(Titles.removeGreaterKey, Descriptions.removeGreaterKey, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1) && this.CheckType(params[0], Integer.class)) {
            collectionManager.removeGreaterKey((Integer) params[0]);
        }
        return null;
    }
}
