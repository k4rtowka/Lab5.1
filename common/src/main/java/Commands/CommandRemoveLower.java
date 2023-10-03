package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;
import Models.SpaceMarine;

public class CommandRemoveLower extends Command {
    public CommandRemoveLower(CollectionManager collectionManager) {
        super(Titles.removeLower, Descriptions.removeLower, collectionManager, 1);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if (this.CheckType(data.getParams(0), SpaceMarine.class)) {
            return collectionManager.removeLower((SpaceMarine) data.getParams(0));
        }
        return null;
    }
}
