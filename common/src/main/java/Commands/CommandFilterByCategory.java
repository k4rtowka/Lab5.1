package Commands;

import Models.AstartesCategory;
import Models.CollectionManager;

public class CommandFilterByCategory extends Command {
    public CommandFilterByCategory(CollectionManager collectionManager) {
        super(Titles.filterByCategory, Descriptions.filterByCategory, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1) && this.CheckType(params[0], AstartesCategory.class)) {
            return collectionManager.filterByCategory((AstartesCategory) params[0]);
        }
        return null;
    }
}
