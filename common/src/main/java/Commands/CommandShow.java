package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;

public class CommandShow extends Command {
    public CommandShow(CollectionManager collectionManager) {
        super(Titles.show, Descriptions.show, collectionManager, 0);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        return collectionManager.show();
    }
}
