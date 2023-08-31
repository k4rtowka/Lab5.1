package Commands;

import Models.CollectionManager;
import Models.SpaceMarine;

public class CommandInsert extends Command {
    public CommandInsert(CollectionManager collectionManager) {
        super(Titles.insert, Descriptions.insert, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if(params[0] == null)
            throw new Exception("Не указан Морпех!");
        return collectionManager.insert((SpaceMarine) params[0]);
    }
}
