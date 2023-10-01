package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;

public class CommandWait extends Command {
    public CommandWait(CollectionManager collectionManager) {
        super(Titles.wait, Descriptions.wait, collectionManager, 2);
    }

    /**
     * Выполняет команду с хранилищем объектов
     *
     * @param params параметры команды
     * @return результат выполнения команды
     */
    @Override
    protected Object execute(Data data) throws Exception {
        if (data.params[0] != null && data.params[0] instanceof Thread && this.CheckType(data.params[1], Integer.class)) {
            //Thread currentThread = (Thread) params[0];
            //currentThread.wait(Integer.parseInt(params[1].toString()));
            Thread.sleep(Integer.parseInt(data.params[1].toString()));
        }
        return String.format("Программа была в ожидании %d миллисекунд", Integer.parseInt(data.params[1].toString()));
    }
}
