package Commands;

import Models.CollectionManager;
import Models.SpaceMarine;

public class CommandInsert extends Command {
    public CommandInsert(CollectionManager collectionManager) {
        super(Titles.insert, Descriptions.insert, collectionManager);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1)) {
            return collectionManager.insert((SpaceMarine) params[0]);
        } else {
            return null;
        }
    }
}
