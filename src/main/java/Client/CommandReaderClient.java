package Client;

import Commands.Command;
import Commands.CommandReader;
import Models.AstartesCategory;
import Models.CollectionManager;
import Models.Data;
import Models.SpaceMarine;

import java.io.InputStream;
import java.util.List;

/**
 * Менеджер команд для клиента
 */
public class CommandReaderClient extends CommandReader {

    //region Конструкторы

    /**
     * Конструктор CommandReader.
     *
     * @param inputStream входной поток для чтения команд
     */
    public CommandReaderClient(InputStream inputStream) {
        super(null, inputStream);
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


        if (commandName.equals(Command.Titles.exit)) {
            currentCommand.Execute();
            return null;
        }
        if (commandName.equals(Command.Titles.update)) {
            this.UpdateReader();
            return new Data(currentCommand, new Object[]{
                    this.inputReader.GetValue("Введите ID объекта, который хотите обновить",
                            Integer.class, false),
                    this.inputReader.GetSpaceMarine()}
            );
        }
        if (commandName.equals(Command.Titles.clear) || commandName.equals(Command.Titles.info)
                || commandName.equals(Command.Titles.help) || commandName.equals(Command.Titles.show) ||
                commandName.equals(Command.Titles.printDescending)) {
            return new Data(currentCommand, null);
        }
        if (commandName.equals(Command.Titles.executeScript) || commandName.equals(Command.Titles.countByHeartCount) ||
                commandName.equals(Command.Titles.removeGreaterKey) || commandName.equals(Command.Titles.removeKey) ||
                commandName.equals(Command.Titles.removeLower)

        ) {
            return new Data(currentCommand, params[0]);
        }
        if (commandName.equals(Command.Titles.insert) || commandName.equals(Command.Titles.replaceIfLower)) {
            this.UpdateReader();
            return new Data(currentCommand, this.inputReader.GetSpaceMarine());
        }
        if (commandName.equals(Command.Titles.filterByCategory)) {
            this.UpdateReader();
            return new Data(currentCommand, this.inputReader.GetEnumValue(AstartesCategory.class, false));
        }
        return new Data(currentCommand, null);
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
