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
        if (data.getParams(0) != null && data.getParams(0) instanceof Thread) {
            Thread currentThread = (Thread) data.getParams(0);
            currentThread.interrupt();
            System.exit(0);
        }
        return "Выход из программы!";
    }
}
