package Commands;

import Models.AstartesCategory;
import Models.CollectionManager;

public class CommandFilterByCategory extends Command {
    public CommandFilterByCategory(CollectionManager collectionManager) {
        super(Titles.filterByCategory, Descriptions.filterByCategory, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if(params[0] == null)
            throw new Exception("Не указана категория!");
        return collectionManager.filterByCategory((AstartesCategory) params[0]);
    }
}
