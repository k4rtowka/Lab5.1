package Commands;

import Models.CollectionManager;
import Models.SpaceMarine;

public class CommandUpdate extends Command {
    public CommandUpdate(CollectionManager collectionManager) {
        super(Titles.update, Descriptions.update, collectionManager);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if(this.CheckParams(params, 2)){
            if (params[0] == null)
                throw new Exception("Не указан ID!");
            if (params[1] == null)
                throw new Exception("Не указан Морпех!");
            return collectionManager.update((Integer) params[0], (SpaceMarine) params[1]);
        }else {
            return null;
        }
    }
}
