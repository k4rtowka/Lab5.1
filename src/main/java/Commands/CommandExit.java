package Commands;

import Models.CollectionManager;

public class CommandExit extends Command {
    public CommandExit(CollectionManager collectionManager) {
        super(Titles.exit, Descriptions.exit, collectionManager, 0);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        System.out.println("Выход из программы!");
        //System.exit(0);
        Thread.currentThread().interrupt();
        return null;
    }
}
