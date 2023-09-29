package Commands;

import Models.Category;
import Models.CollectionManager;
import Models.CollectionManagerToFile;

public class CommandFilterByCategory extends Command {
    public CommandFilterByCategory(CollectionManager collectionManager) {
        super(Titles.filterByCategory, Descriptions.filterByCategory, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1) && this.CheckType(params[0], Category.class)) {
            return collectionManager.filterByCategory((Category) params[0]);
        }
        return null;
    }
}
