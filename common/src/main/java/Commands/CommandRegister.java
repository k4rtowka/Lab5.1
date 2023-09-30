package Commands;

import Models.CollectionManager;


public class CommandRegister extends Command {
    CommandRegister(CollectionManager collectionManager) {
        super(Titles.login, Descriptions.login, collectionManager, 2);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckType(params[0], String.class) && this.CheckType(params[1], String.class))
            return collectionManager.Register((String) params[0], (String) params[1]);
        else
            return null;
    }


}
