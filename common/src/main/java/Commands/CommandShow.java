package Commands;

import Models.CollectionManager;

public class CommandShow extends Command {
    public CommandShow(CollectionManager collectionManager) {
        super(Titles.show, Descriptions.show, collectionManager, 0);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if(this.CheckParams(params, 0)){
            return collectionManager.show();

        }
        return null;
    }
}
