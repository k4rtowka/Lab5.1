package Server;

import Commands.Command;
import Commands.CommandReader;
import Models.CollectionManager;
import Models.SpaceMarine;

import java.io.InputStream;

public class CommandReaderServer extends CommandReader {

    //region Конструкторы

    /**
     * Конструктор CommandReader.
     *
     * @param collectionManager менеджер коллекции
     * @param inputStream       входной поток для чтения команд
     */
    public CommandReaderServer(CollectionManager collectionManager, InputStream inputStream) {
        super(collectionManager, inputStream);
    }
    //endregion

    //region Методы

    /**
     * Выполняет команду с несколькими параметрами в виде строк
     *
     * @param commandName имя команды
     * @param params      параметры команды
     * @return объект, который возвращает команда, после выполнения
     * @throws Exception
     */
    @Override
    public Object Execute(String commandName, Object[] params) throws Exception {
        Command currentCommand = this.commandHelp.GetCommand(commandName);
        if (currentCommand == null) {
            throw new Exception("Получена несуществующая команда");
        }
        if(commandName.equals(Command.Titles.help) || commandName.equals(Command.Titles.show)){
            return  currentCommand.Execute(null);
        }
        if(commandName.equals(Command.Titles.insert)){
            return currentCommand.Execute(params[0]);
        }
//        //region Ничего не возвращают
//        if (commandName.equals(Command.Titles.clear) || commandName.equals(Command.Titles.removeHead) || commandName.equals(Command.Titles.exit))
//            currentCommand.Execute(null);
//        if (commandName.equals(Command.Titles.executeScript)) {
//            if (params == null || params.length == 0)
//                throw new Exception("Передано неверное число аргументов");
//            Thread thread = new Thread(() -> {
//                try {
//                    currentCommand.Execute(params);
//                } catch (Exception e) {
//                    Print(e.getMessage());
//                }
//            });
//            thread.start();
//        }
        return null;
    }

    /**
     * Выполняет команду с несколькими параметрами в виде объектов
     *
     * @param commandName имя команды
     * @param params      параметры
     * @return Объект с результатом
     * @throws Exception Ошибка
     */
    @Override
    public Object Execute(String commandName, String params) throws Exception {
        return null;
    }
    //endregion

}
