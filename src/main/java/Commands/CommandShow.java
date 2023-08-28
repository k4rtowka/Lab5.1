package Commands;

import Models.CollectionManager;

public class CommandShow extends Command {
    public CommandShow(CollectionManager collectionManager) {
        super(Titles.show, Descriptions.show, collectionManager);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        return collectionManager.show();
    }
}
