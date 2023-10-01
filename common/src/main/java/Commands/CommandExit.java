package Commands;

import Common.UserInfo;
import Models.CollectionManager;
import Models.Data;

public class CommandExit extends Command {
    public CommandExit(CollectionManager collectionManager) {
        super(Titles.exit, Descriptions.exit, collectionManager, 1);
    }

    @Override
    protected Object execute(Data data) throws Exception {
        if (data.params[0] != null && data.params[0] instanceof Thread) {
            Thread currentThread = (Thread) data.params[0];
            currentThread.interrupt();
        }
        return "Выход из программы!";
    }
}
