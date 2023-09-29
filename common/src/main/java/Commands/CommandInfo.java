package Commands;

import Models.CollectionManagerToFile;

public class CommandInfo extends Command {

    public CommandInfo(CollectionManagerToFile collectionManager) {
        super(Titles.info, Descriptions.info, collectionManager, 0);
    }

    /**
     * Выполняет команду с хранилищем объектов
     *
     * @param params параметры команды
     * @return результат выполнения команды
     */
    @Override
    protected Object execute(Object[] params) throws Exception {
        if(this.CheckParams(params,0)){
            return this.collectionManager.info();
        }
        return null;
    }
}
