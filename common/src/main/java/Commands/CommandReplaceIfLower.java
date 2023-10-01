package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;
import Models.SpaceMarine;

public class CommandReplaceIfLower extends Command {
    public CommandReplaceIfLower(CollectionManager collectionManager) {
        super(Titles.replaceIfLower, Descriptions.replaceIfLower, collectionManager, 2);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if(this.CheckType(data.params[0], Integer.class) && this.CheckType(data.params[1], SpaceMarine.class)){
            return collectionManager.replaceIfLower(Integer.parseInt(data.params[0].toString()), (SpaceMarine) data.params[1]);
        }
        return null;
    }
}
