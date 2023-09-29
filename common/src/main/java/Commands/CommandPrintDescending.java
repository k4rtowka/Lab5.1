package Commands;

import Models.CollectionManagerToFile;

public class CommandPrintDescending extends Command {
    public CommandPrintDescending(CollectionManagerToFile collectionManager) {
        super(Titles.printDescending, Descriptions.printDescending, collectionManager, 0);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if(this.CheckParams(params, 0)){
            return collectionManager.printDescending();
        }
        return null;
    }
}
