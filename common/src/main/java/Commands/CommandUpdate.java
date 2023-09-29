package Commands;

import Models.CollectionManagerToFile;
import Models.SpaceMarine;

public class CommandUpdate extends Command {
    public CommandUpdate(CollectionManagerToFile collectionManager) {
        super(Titles.update, Descriptions.update, collectionManager, 2);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 2)
                && this.CheckType(params[0], Integer.class)
                && this.CheckType(params[1], SpaceMarine.class)
        ) {
            return collectionManager.update(Integer.parseInt(params[0].toString()), (SpaceMarine) params[1]);
        }
        return null;
    }
}
