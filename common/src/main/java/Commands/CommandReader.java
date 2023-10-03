package Commands;

import Common.UserInfo;
import IO.InputReader;
import Models.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class CommandReader {
    //region Поля
    /**
     * Объект для управления коллекцией
     */
    protected CollectionManager collectionManager;
    /**
     * Сканер для считывания ввода из заданного потока данных.
     */
    protected Scanner scanner;

    /**
     * Читатель пользовательского ввода.
     */
    protected InputReader inputReader;

    /**
     * Команда "помощь"
     */
    protected CommandHelp commandHelp;

    /**
     * Чтение из файла или нет
     */
    protected boolean isReadFromFile;

    /**
     * Буфер для хранения ответов команд
     */
    private String commandsBuffer;

    /**
     * Текущие данные о клиенте
     */
    protected UserInfo userInfo;
    //endregion

    //region Конструкторы

    /**
     * Конструктор CommandReader.
     *
     * @param collectionManager менеджер коллекции
     * @param inputStream       входной поток для чтения команд
     */
    public CommandReader(CollectionManager collectionManager, InputStream inputStream) {
        this.collectionManager = collectionManager;
        this.isReadFromFile = inputStream instanceof FileInputStream;
        this.scanner = new Scanner(inputStream);
        this.inputReader = new InputReader(collectionManager, inputStream, true);
        this.commandHelp = new CommandHelp(this.collectionManager);
        this.commandsBuffer = "";
    }

    //endregion

    //region Сеттеры/Геттеры
    public void SetClientInfo(UserInfo clientInfo) {
        this.userInfo = clientInfo;
    }

    public UserInfo GetClientInfo() {
        return this.userInfo;
    }
    //endregion

    //region Методы

    /**
     * Очистка вывода команд
     */
    void ClearCommandsBuffer() {
        this.commandsBuffer = "";
    }

    /**
     * Получает строку с логом последних выполненных команд
     *
     * @return строка с логом последних выполненных команд
     */
    String GetCommandsBuffer() {
        return commandsBuffer;
    }

    /**
     * Выводит указанное сообщение
     *
     * @param message      сообщение
     * @param isShowPrompt выводить в консоль или нет (отключается при работе с файлами)
     */
    public void Print(Object message, boolean isShowPrompt) {
        if (isShowPrompt)
            System.out.println(message);
    }

    /**
     * Выводит указанное сообщение
     *
     * @param message сообщение
     */
    public void Print(Object message) {
        this.Print(message, this.isReadFromFile);
    }

    /**
     * Обновляет сканер для чтения, нужен для работы с файлами,
     * иначе мы теряем возможность считывать с файла, поток теряется
     */
    public void UpdateReader() {
        this.inputReader = new InputReader(this.collectionManager, this.scanner, this.isReadFromFile, !this.isReadFromFile);
    }

    /**
     * Выполняет команду с несколькими параметрами в виде строк
     *
     * @param data параметры команды
     * @return объект, который возвращает команда, после выполнения
     * @throws Exception
     */
    public Object Execute(Data data) throws Exception {
        String commandName = data.getCommand().getName();
        Command currentCommand = this.commandHelp.GetCommand(commandName);

        //region Ничего не возвращают
        if (commandName.equals(Command.Titles.save)) {
            Object result = currentCommand.Execute(data);
            if (result == null)
                return "Не удалось сохранить коллекцию";
            else
                return "Коллекция сохранена";
        }
        if (commandName.equals(Command.Titles.update)) {
            this.UpdateReader();
            data.Add(this.inputReader.GetSpaceMarine());
            Object result = currentCommand.Execute(data);
            if (result == null)
                return "Не удалось обновить указанный элемент";
            else
                return "Объект успешно обновлен";
        }
        if (commandName.equals(Command.Titles.clear) || commandName.equals(Command.Titles.exit)) {
            return currentCommand.Execute(data);
        }
        if (commandName.equals(Command.Titles.executeScript)) {
            currentCommand.Execute(data);
            String buffer = this.commandsBuffer;
            this.ClearCommandsBuffer();
            return buffer;
        }
        //endregion
        //region Возвращают строку
        if (commandName.equals(Command.Titles.info) || commandName.equals(Command.Titles.help)
                || commandName.equals(Command.Titles.show) || commandName.equals(Command.Titles.printDescending)) {

            Object result = currentCommand.Execute(data);
            if (this.isReadFromFile)
                this.commandsBuffer += result.toString();
            return result;
        }
        //endregion
        //region Возвращают число
        if (commandName.equals(Command.Titles.insert)) {
            this.UpdateReader();
            data.Add(this.inputReader.GetSpaceMarine());
            Object result = currentCommand.Execute(data);
            if (result == null)
                return "Не удалось добавить объект";
            else
                return String.format("Объект добавлен, его ID в коллекции:%d", (Integer) result);
        }
        if (commandName.equals(Command.Titles.countByHeartCount)) {
            Object result = currentCommand.Execute(data);
            if (result == null)
                return "Не удалось найти значение";
            else
                return String.format("Число объектов с указанным здоровьем:%d", (Long) result);
        }
        //endregion
        //region Возвращают булевское значение
        if (commandName.equals(Command.Titles.removeGreaterKey)) {
            Object result = currentCommand.Execute(data);
            if (result == null || !(boolean) result)
                return "Не удалось удалить указанный элемент";
            else
                return "Объект успешно удален";
        }
        if (commandName.equals(Command.Titles.removeKey)) {
            Object result = currentCommand.Execute(data);
            if (result == null || !(boolean) result)
                return "Не удалось удалить указанный элемент";
            else
                return "Объект успешно удален";
        }
        if (commandName.equals(Command.Titles.removeLower)) {
            data.Add(this.inputReader.GetSpaceMarine());
            Object result = currentCommand.Execute(data);
            if (result == null || !(boolean) result)
                return "Не удалось удалить указанный элемент";
            else
                return "Объект успешно удален";
        }
        if (commandName.equals(Command.Titles.replaceIfLower)) {
            this.UpdateReader();
            data.Add(this.inputReader.GetSpaceMarine());
            Object result = currentCommand.Execute(data);
            if (result == null || !(boolean) result)
                return "Не удалось добавить указанный элемент";
            else
                return "Объект успешно добавлен";
        }
        //endregion
        //region Возвращает список
        if (commandName.equals(Command.Titles.filterByCategory)) {
            data.Add(this.inputReader.GetEnumValue(Category.class, false));
            Object list = currentCommand.Execute(data);
            if (list instanceof List<?>) {
                List<SpaceMarine> marines = (List<SpaceMarine>) list;
                StringBuilder result = new StringBuilder();
                for (SpaceMarine marine : marines) {
                    result.append(marine).append("\n");
                }
                return result.toString();
            } else {
                throw new Exception("Команда не вернул список морпехов!");
            }
        }
        //endregion

        return "Неизвестная команда!";
    }


    /**
     * Запускает менеджер команд на чтение и выполнение команд
     */
    public void Start() {
        try {
            while (true) {
                this.UpdateReader();
                this.Print("Введите команду:", !this.isReadFromFile);
                if (!scanner.hasNext()) {
                    break;
                }
                String command = scanner.nextLine();
                String[] splitCommand = command.split(" ", 2);
                try {
                    Data data = new Data(
                            this.userInfo,
                            this.commandHelp.GetCommand(splitCommand[0]),
                            splitCommand.length > 1 ? splitCommand[1] : null
                    );
                    System.out.println(Execute(data));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //endregion


}
