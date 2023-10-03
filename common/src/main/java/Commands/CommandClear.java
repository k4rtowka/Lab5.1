package Commands;

import Models.CollectionManager;
import Models.Data;

public class CommandClear extends Command {
    public CommandClear(CollectionManager collectionManager) {
        super(Titles.clear, Descriptions.clear, collectionManager, 0);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        collectionManager.clear();
        return "Коллекция очищена!";
    }
}
