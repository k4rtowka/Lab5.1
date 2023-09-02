package Commands;

import Models.AstartesCategory;
import Models.CollectionManager;

public class CommandRemoveKey extends Command {
    public CommandRemoveKey(CollectionManager collectionManager) {
        super(Titles.removeKey, Descriptions.removeKey, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1) && this.CheckType(params[0], Integer.class)) {
            return collectionManager.removeKey((Integer) params[0]);
        }
        return null;
    }
}
