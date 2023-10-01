package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;
import Models.SpaceMarine;

public class CommandInsert extends Command {
    public CommandInsert(CollectionManager collectionManager) {
        super(Titles.insert, Descriptions.insert, collectionManager, 1);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if (this.CheckType(data.params[0], SpaceMarine.class)) {
            SpaceMarine marine = (SpaceMarine) data.params[0];
            Integer userId = (Integer) data.params[1];
            marine.setUserId(userId);
            return collectionManager.insert(marine);
        }
        return null;
    }
}
