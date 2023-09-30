package Commands;

import Models.CollectionManager;


public class CommandLogin extends Command {
    public CommandLogin(CollectionManager collectionManager) {
        super(Titles.login, Descriptions.login, collectionManager, 2);
    }


    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckType(params[0], String.class) && this.CheckType(params[1], String.class))
            return collectionManager.Login((String) params[0], (String) params[1]);
        else
            return null;
    }


}
