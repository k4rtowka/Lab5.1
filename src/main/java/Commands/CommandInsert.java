package Commands;

import Models.AstartesCategory;
import Models.CollectionManager;
import Models.SpaceMarine;

public class CommandInsert extends Command {
    public CommandInsert(CollectionManager collectionManager) {
        super(Titles.insert, Descriptions.insert, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1) && this.CheckType(params[0], SpaceMarine.class)) {
            return collectionManager.insert((SpaceMarine) params[0]);
        }
        return null;
    }
}
