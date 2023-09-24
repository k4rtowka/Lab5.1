package Commands;

import Models.CollectionManager;

public class CommandExit extends Command {
    public CommandExit(CollectionManager collectionManager) {
        super(Titles.exit, Descriptions.exit, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (params[0] != null && params[0] instanceof Thread) {
            Thread currentThread = (Thread) params[0];
            currentThread.interrupt();
        }
        return "Выход из программы!";
    }
}
