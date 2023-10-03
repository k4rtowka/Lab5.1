package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;


public class CommandRegister extends Command {
    CommandRegister(CollectionManager collectionManager) {
        super(Titles.register, Descriptions.register, collectionManager, 2);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if (this.CheckType(data.getParams(0), String.class) &&
                this.CheckType(data.getParams(1), String.class))
            return collectionManager.Register(
                    (String) data.getParams(0),
                    (String) data.getParams(1));
        else
            return null;
    }


}
