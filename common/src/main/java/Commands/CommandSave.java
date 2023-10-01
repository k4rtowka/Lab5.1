package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;

public class CommandSave extends Command {
    public CommandSave(CollectionManager collectionManager) {
        super(Titles.save, Descriptions.save, collectionManager, 0);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        collectionManager.ClearExecuteScripts();
        collectionManager.Save();
        return true;
    }
}
