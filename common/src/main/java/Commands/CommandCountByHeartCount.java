package Commands;

import Models.AstartesCategory;
import Models.CollectionManager;

public class CommandCountByHeartCount extends Command {
    public CommandCountByHeartCount(CollectionManager collectionManager) {
        super(Titles.countByHeartCount, Descriptions.countByHeartCount, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1) && this.CheckType(params[0], Integer.class)) {
            return collectionManager.countByHeartCount(Integer.parseInt(params[0].toString()));
        }
        return null;
    }
}
