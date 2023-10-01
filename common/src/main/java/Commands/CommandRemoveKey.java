package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;

public class CommandRemoveKey extends Command {
    public CommandRemoveKey(CollectionManager collectionManager) {
        super(Titles.removeKey, Descriptions.removeKey, collectionManager, 1);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if (this.CheckType(data.params[0], Integer.class)) {
            return collectionManager.removeKey(Integer.parseInt(data.params[0].toString()));
        }
        return null;
    }
}
