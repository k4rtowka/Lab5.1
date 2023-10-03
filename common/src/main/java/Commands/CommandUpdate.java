package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;
import Models.SpaceMarine;

public class CommandUpdate extends Command {
    public CommandUpdate(CollectionManager collectionManager) {
        super(Titles.update, Descriptions.update, collectionManager, 2);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if (this.CheckType(data.getParams(0), Integer.class)
                && this.CheckType(data.getParams(1), SpaceMarine.class)
        ) {
            return collectionManager.update(Integer.parseInt(
                    data.getParams(0).toString()),
                    (SpaceMarine) data.getParams(1));
        }
        return null;
    }
}
