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
        if (this.CheckType(data.getParams(0), SpaceMarine.class)) {
            SpaceMarine marine = (SpaceMarine) data.getParams(0);
            if (data.getUserInfo() == null)
                throw new Exception("Не указана информация о владельце объекта!");
            marine.setUserId(data.getUserInfo().getId());
            return collectionManager.insert(marine);
        }
        return null;
    }
}
