package Commands;

import IO.InputReader;
import Models.AstartesCategory;
import Models.CollectionManager;
import Models.SpaceMarine;

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
    }
    //endregion

    //region Методы

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

    public Object Execute(String commandName) throws Exception {
        return this.Execute(commandName, "");
    }


    /**
     * Выполняет команду с несколькими параметрами в виде строк
     * @param commandName имя команды
     * @param params параметры команды
     * @return объект, который возвращает команда, после выполнения
     * @throws Exception
     */
    public Object Execute(String commandName, String params) throws Exception {
        Command currentCommand = this.commandHelp.GetCommand(commandName);

        //region Ничего не возвращают
        if (commandName.equals(Command.Titles.save)) {
            Object result = currentCommand.Execute(null);
            if (result == null)
                return "Не удалось сохранить коллекцию";
            else
                return "Коллекция сохранена";
        }
        if (commandName.equals(Command.Titles.update)) {
            this.UpdateReader();
            Object result = currentCommand.Execute(
                    new Object[]{
                            params,
                            this.inputReader.GetSpaceMarine()
                    });
            if (result == null)
                return "Не удалось обновить указанный элемент";
            else
                return "Объект успешно обновлен";
        }
        if (commandName.equals(Command.Titles.clear) || commandName.equals(Command.Titles.exit)) {
            return currentCommand.Execute(null);
        }
        if (commandName.equals(Command.Titles.executeScript)) {
            currentCommand.Execute(params);
            return "Команда выполнена!";
        }
        //endregion
        //region Возвращают строку
        if (commandName.equals(Command.Titles.info) || commandName.equals(Command.Titles.help)
                || commandName.equals(Command.Titles.show) || commandName.equals(Command.Titles.printDescending))
            return currentCommand.Execute(null);
        //endregion
        //region Возвращают число
        if (commandName.equals(Command.Titles.insert)) {
            this.UpdateReader();
            Object result = currentCommand.Execute(this.inputReader.GetSpaceMarine());
            if (result == null)
                return "Не удалось добавить объект";
            else
                return String.format("Объект добавлен, его ID в коллекции:%d", (Integer) result);
        }
        if (commandName.equals(Command.Titles.countByHeartCount)) {
            Object result = currentCommand.Execute(params);
            if (result == null)
                return "Не удалось найти значение";
            else
                return String.format("Число объектов с указанным здоровьем:%d", (Long) result);
        }
        //endregion
        //region Возвращают булевское значение
        if (commandName.equals(Command.Titles.removeGreaterKey)) {
            Object result = currentCommand.Execute(params);
            if (result == null || !(boolean) result)
                return "Не удалось удалить указанный элемент";
            else
                return "Объект успешно удален";
        }
        if (commandName.equals(Command.Titles.removeKey)) {
            Object result = currentCommand.Execute(params);
            if (result == null || !(boolean) result)
                return "Не удалось удалить указанный элемент";
            else
                return "Объект успешно удален";
        }
        if (commandName.equals(Command.Titles.removeLower)) {
            Object result = currentCommand.Execute(this.inputReader.GetSpaceMarine());
            if (result == null || !(boolean) result)
                return "Не удалось удалить указанный элемент";
            else
                return "Объект успешно удален";
        }
        if (commandName.equals(Command.Titles.replaceIfLower)) {
            this.UpdateReader();
            Object result = currentCommand.Execute(
                    new Object[]{
                            params,
                            this.inputReader.GetSpaceMarine()
                    });
            if (result == null || !(boolean) result)
                return "Не удалось добавить указанный элемент";
            else
                return "Объект успешно добавлен";
        }
        //endregion
        //region Возвращает список
        if (commandName.equals(Command.Titles.filterByCategory)) {
            Object list = currentCommand.Execute(this.inputReader.GetEnumValue(AstartesCategory.class, false));
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
     * Выполняет команду с несколькими параметрами в виде объектов
     *
     * @param commandName имя команды
     * @param params      параметры
     * @return Объект с результатом
     * @throws Exception Ошибка
     */
    public Object Execute(String commandName, Object[] params) throws Exception {
        throw new Exception("Метод не реализован");
    }

    public void Start() {
        try {
            while (true) {
                this.Print("Введите команду:", !this.isReadFromFile);
                if (!scanner.hasNext()) {
                    break;
                }
                String command = scanner.nextLine();
                String[] splitCommand = command.split(" ", 2);
                try {
                    System.out.println(Execute(splitCommand[0], splitCommand.length > 1 ? splitCommand[1] : null));
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
