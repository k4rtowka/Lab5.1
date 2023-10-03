package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;


public class CommandLogin extends Command {
    public CommandLogin(CollectionManager collectionManager) {
        super(Titles.login, Descriptions.login, collectionManager, 2);
    }


    @Override
    protected Object execute(Data data) throws Exception {
        if (this.CheckType(data.getParams(0), String.class) &&
                this.CheckType(data.getParams(1), String.class))
            return collectionManager.Login((String) data.getParams(0), (String) data.getParams(1));
        else
            return null;
    }


}
