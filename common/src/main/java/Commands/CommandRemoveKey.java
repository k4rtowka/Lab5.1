package Commands;

import Common.Strings;
import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;

public class CommandRemoveKey extends Command {
    public CommandRemoveKey(CollectionManager collectionManager) {
        super(Titles.removeKey, Descriptions.removeKey, collectionManager, 1);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if (this.CheckType(data.getParams(0), Integer.class)) {
            if (data.getUserInfo() == null) throw new Exception(Strings.Errors.Commands.missedObjectOwnerInfo);
            return collectionManager.removeKey(
                    Integer.parseInt(data.getParams(0).toString()),
                    data.getUserInfo().getId()
            );
        }
        return null;
    }
}
