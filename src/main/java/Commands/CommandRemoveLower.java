package Commands;

import Models.CollectionManager;
import Models.SpaceMarine;

public class CommandRemoveLower extends Command {
    public CommandRemoveLower(CollectionManager collectionManager) {
        super(Titles.removeLower, Descriptions.removeLower, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1) && this.CheckType(params[0], SpaceMarine.class)) {
            collectionManager.removeLower((SpaceMarine) params[0]);
        }
        return null;
    }
}
