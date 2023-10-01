package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;

public class CommandPrintDescending extends Command {
    public CommandPrintDescending(CollectionManager collectionManager) {
        super(Titles.printDescending, Descriptions.printDescending, collectionManager, 0);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        return collectionManager.printDescending();
    }
}
