package Commands;

import Models.CollectionManager;
import Models.SpaceMarine;

public class CommandRemoveLower extends Command {
    public CommandRemoveLower(CollectionManager collectionManager) {
        super(Titles.removeLower, Descriptions.removeLower, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if(params[0] == null)
            throw new Exception("Не указан Морпех!");
        collectionManager.removeLower((SpaceMarine) params[0]);
        return null;
    }
}
