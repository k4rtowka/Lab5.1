package Commands;

import Common.UserInfo;
import Models.Category;
import Models.CollectionManager;
import Models.Data;

public class CommandFilterByCategory extends Command {
    public CommandFilterByCategory(CollectionManager collectionManager) {
        super(Titles.filterByCategory, Descriptions.filterByCategory, collectionManager, 1);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if (this.CheckType(data.params[0], Category.class)) {
            return collectionManager.filterByCategory((Category) data.params[0]);
        }
        return null;
    }
}
