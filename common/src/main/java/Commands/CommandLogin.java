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
        if (this.CheckType(data.params[0], String.class) && this.CheckType(data.params[1], String.class))
            return collectionManager.Login((String) data.params[0], (String) data.params[1]);
        else
            return null;
    }


}
