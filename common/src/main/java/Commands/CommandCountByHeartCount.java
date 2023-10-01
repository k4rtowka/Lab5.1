package Commands;

import Models.CollectionManager;
import Models.Data;

public class CommandCountByHeartCount extends Command {
    public CommandCountByHeartCount(CollectionManager collectionManager) {
        super(Titles.countByHeartCount, Descriptions.countByHeartCount, collectionManager, 1);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if (this.CheckType(data.params[0], Integer.class)) {
            return collectionManager.countByHeartCount(Integer.parseInt(data.params[0].toString()));
        }
        return null;
    }
}
