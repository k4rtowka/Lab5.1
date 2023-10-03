package Commands;

import Common.Strings;
import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;

public class CommandRemoveGreaterKey extends Command {
    public CommandRemoveGreaterKey(CollectionManager collectionManager) {
        super(Titles.removeGreaterKey, Descriptions.removeGreaterKey, collectionManager, 1);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if (this.CheckType(data.getParams(0), Integer.class)) {
            if (data.getUserInfo() == null) throw new Exception(Strings.Errors.Commands.missedObjectOwnerInfo);
            return collectionManager.removeGreaterKey(Integer.parseInt(data.getParams(0).toString()), data.getUserInfo().getId());
        }
        return null;
    }
}
