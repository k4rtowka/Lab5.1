package Client;

import Commands.Command;
import Commands.CommandReader;
import Common.UserInfo;
import Models.Category;
import Models.Data;

import java.io.InputStream;

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
     * @param data параметры команды
     * @return объект, который возвращает команда, после выполнения
     * @throws Exception
     */
    @Override
    public Object Execute(Data data) throws Exception {
        String commandName = data.getCommand().getName();
        Command currentCommand = this.commandHelp.GetCommand(commandName);
        Data result = new Data(this.userInfo, currentCommand, data.getParams());
        if (commandName.equals(Command.Titles.login) || commandName.equals(Command.Titles.register)) {
            //this.UpdateReader();
            result.Add(this.inputReader.GetValue("Введите логин", String.class, false));
            result.Add(this.inputReader.GetValue("Введите пароль", String.class, false));
        }
        if (commandName.equals(Command.Titles.update)) {
            this.UpdateReader();
            result.Add(this.inputReader.GetValue("Введите ID объекта, который хотите обновить", Integer.class, false));
            result.Add(this.inputReader.GetSpaceMarine());
        }
        if (commandName.equals(Command.Titles.insert) || commandName.equals(Command.Titles.replaceIfLower) ||
                commandName.equals(Command.Titles.removeLower)) {
//            this.UpdateReader();
            result.Add(this.inputReader.GetSpaceMarine());
        }
        if (commandName.equals(Command.Titles.filterByCategory)) {
            //this.UpdateReader();
            result.Add(this.inputReader.GetEnumValue(Category.class, false));
        }
        return result;
    }

    //endregion

}
