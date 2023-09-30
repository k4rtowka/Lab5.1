package Commands;

import Models.CollectionManager;
import Models.CollectionManagerToFile;
import Models.SpaceMarine;

import java.net.InetAddress;

public class CommandInsert extends Command {
    public CommandInsert(CollectionManager collectionManager) {
        super(Titles.insert, Descriptions.insert, collectionManager, 2);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckType(params[0], SpaceMarine.class) && this.CheckType(params[1], Integer.class)) {
            SpaceMarine marine = (SpaceMarine) params[0];
            Integer userId = (Integer) params[1];
            marine.setUserId(userId);
            return collectionManager.insert(marine);
        }
        return null;
    }
}
