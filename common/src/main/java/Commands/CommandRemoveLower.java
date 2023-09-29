package Commands;

import Models.CollectionManagerToFile;
import Models.SpaceMarine;

public class CommandRemoveLower extends Command {
    public CommandRemoveLower(CollectionManagerToFile collectionManager) {
        super(Titles.removeLower, Descriptions.removeLower, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1) && this.CheckType(params[0], SpaceMarine.class)) {
            return collectionManager.removeLower((SpaceMarine) params[0]);
        }
        return null;
    }
}
