package Commands;

import Models.CollectionManager;

public class CommandSave extends Command {
    public CommandSave(CollectionManager collectionManager) {
        super(Titles.save, Descriptions.save, collectionManager, 0);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if(this.CheckParams(params, 0)){
            collectionManager.save();
            return true;
        }
        return null;
    }
}
