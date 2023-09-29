package Commands;

import Models.CollectionManagerToFile;

public class CommandShow extends Command {
    public CommandShow(CollectionManagerToFile collectionManager) {
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
