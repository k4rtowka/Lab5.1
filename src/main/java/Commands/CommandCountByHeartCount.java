package Commands;

import Models.CollectionManager;

public class CommandCountByHeartCount extends Command {
    public CommandCountByHeartCount(CollectionManager collectionManager) {
        super(Titles.countByHeartCount, Descriptions.countByHeartCount, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 1)) {
            if (params[0] == null)
                throw new Exception("Не указано количество сердец!");
            return collectionManager.countByHeartCount((Integer) params[0]);
        }
        return null;
    }
}
