package Commands;

import Models.CollectionManager;
import Models.SpaceMarine;

public class CommandReplaceIfLower extends Command {
    public CommandReplaceIfLower(CollectionManager collectionManager) {
        super(Titles.replaceIfLower, Descriptions.replaceIfLower, collectionManager, 2);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (params[0] == null)
            throw new Exception("Не указан ID!");
        if (params[1] == null)
            throw new Exception("Не указан Морпех!");
        collectionManager.replaceIfLower((Integer) params[0], (SpaceMarine) params[1]);
        return null;
    }
}
