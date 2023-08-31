package Commands;

import Models.CollectionManager;

public class CommandInfo extends Command {

    public CommandInfo(CollectionManager collectionManager) {
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
        return this.collectionManager.info();
    }
}
