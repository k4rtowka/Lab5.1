package Server;

import Commands.Command;
import Commands.CommandReader;
import Models.CollectionManager;
import Models.SpaceMarine;

import java.io.InputStream;

public class CommandReaderServer extends CommandReader {

    //region Поля
    Thread currentThread;
    //endregion

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

    //region Сеттеры/геттеры
    Thread GetCurrentThread() {
        return this.currentThread;
    }

    void SetCurrentThread(Thread thread) {
        this.currentThread = thread;
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
        try {
            Command currentCommand = this.commandHelp.GetCommand(commandName);
            if (currentCommand == null) {
                throw new Exception("Получена несуществующая команда");
            }
            if (commandName.equals(Command.Titles.exit)) {
                return currentCommand.Execute(this.currentThread);
            }
            if (commandName.equals(Command.Titles.wait)) {
                return currentCommand.Execute(new Object[]{this.currentThread, params[0]});
            }
            if (commandName.equals(Command.Titles.help) || commandName.equals(Command.Titles.show)
                    || commandName.equals(Command.Titles.clear) || commandName.equals(Command.Titles.info)
                    || commandName.equals(Command.Titles.printDescending)) {
                this.collectionManager.save();
                return currentCommand.Execute(null);
            }
            if (commandName.equals(Command.Titles.insert) || commandName.equals(Command.Titles.removeKey)
                    || commandName.equals(Command.Titles.removeGreaterKey) || commandName.equals(Command.Titles.removeLower)
                    || commandName.equals(Command.Titles.countByHeartCount)) {
                this.collectionManager.save();
                return currentCommand.Execute(params[0]);
            }
            if (commandName.equals(Command.Titles.replaceIfLower) || commandName.equals(Command.Titles.update)) {
                this.collectionManager.save();
                return currentCommand.Execute(params);
            }
            if (commandName.equals(Command.Titles.save)) {
                return currentCommand.Execute();
            }

            if (commandName.equals(Command.Titles.executeScript)) {
//            Thread thread = new Thread(() -> {
//                try {
//                    Object obj = currentCommand.Execute(params);
//                } catch (Exception e) {
//                    Print(e.getMessage());
//                }
//            });
//            thread.start();
                this.collectionManager.save();
                return currentCommand.Execute(params);
            }
        }catch (Exception e){
            return e.getMessage();
        }

        return "null";
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
