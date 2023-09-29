package Commands;

import Models.CollectionManagerToFile;

public class CommandWait extends Command {
    public CommandWait(CollectionManagerToFile collectionManager) {
        super(Titles.wait, Descriptions.wait, collectionManager, 2);
    }

    /**
     * Выполняет команду с хранилищем объектов
     *
     * @param params параметры команды
     * @return результат выполнения команды
     */
    @Override
    protected Object execute(Object[] params) throws Exception {
        if (params[0] != null && params[0] instanceof Thread && this.CheckType(params[1], Integer.class)) {
            //Thread currentThread = (Thread) params[0];
            //currentThread.wait(Integer.parseInt(params[1].toString()));
            Thread.sleep(Integer.parseInt(params[1].toString()));
        }
        return String.format("Программа была в ожидании %d миллисекунд", Integer.parseInt(params[1].toString()));
    }
}
