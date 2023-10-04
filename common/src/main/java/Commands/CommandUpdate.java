package Commands;

import Common.Strings;
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
            if (data.getUserInfo() == null) throw new Exception(Strings.Errors.Commands.missedObjectOwnerInfo);
            return collectionManager.update(Integer.parseInt(
                            data.getParams(0).toString()),
                    (SpaceMarine) data.getParams(1),
                    data.getUserInfo().getId()
            );
        }
        return null;
    }
}
