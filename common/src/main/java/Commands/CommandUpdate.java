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
        if (this.CheckParams(data.params, 2)
                && this.CheckType(data.params[0], Integer.class)
                && this.CheckType(data.params[1], SpaceMarine.class)
        ) {
            return collectionManager.update(Integer.parseInt(data.params[0].toString()), (SpaceMarine) data.params[1]);
        }
        return null;
    }
}
