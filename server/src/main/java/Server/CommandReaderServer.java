package Server;

import Commands.Command;
import Commands.CommandReader;
import Models.CollectionManager;
import Models.CollectionManagerToFile;
import Models.Data;

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
     * @param data параметры команды
     * @return объект, который возвращает команда, после выполнения
     * @throws Exception
     */
    @Override
    public Object Execute(Data data) throws Exception {
        try {
            String commandName = data.getCommand().getName();
            Command currentCommand = this.commandHelp.GetCommand(commandName);
            if (currentCommand == null) {
                throw new Exception("Получена несуществующая команда");
            }
            if (commandName.equals(Command.Titles.exit)) {
                return currentCommand.Execute(this.currentThread);
            } else if (commandName.equals(Command.Titles.wait)) {
                data.setParams(new Object[]{this.currentThread, data.getParams(0)});
                return currentCommand.Execute(data);
            } else {
                return currentCommand.Execute(data);
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    //endregion

}
