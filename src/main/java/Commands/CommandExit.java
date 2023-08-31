package Commands;

import Models.CollectionManager;

public class CommandExit extends Command {
    public CommandExit(CollectionManager collectionManager) {
        super(Titles.exit, Descriptions.exit, collectionManager, 0);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        if (this.CheckParams(params, 0)) {
            System.out.println("Выход из программы!");
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
