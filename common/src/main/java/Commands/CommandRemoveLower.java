package Commands;

import Common.Strings;
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
            if (data.getUserInfo() == null) throw new Exception(Strings.Errors.Commands.missedObjectOwnerInfo);
            return collectionManager.removeLower(
                    (SpaceMarine) data.getParams(0),
                    data.getUserInfo().getId()
            );
        }
        return null;
    }
}
