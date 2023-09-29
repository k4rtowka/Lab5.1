package Commands;

import Models.CollectionManager;
import Models.CollectionManagerToFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CommandExecuteScript extends Command {
    private String path = System.getenv("SCRIPT_PATH");

    public CommandExecuteScript(CollectionManager collectionManager) {
        super(Titles.executeScript, Descriptions.executeScript, collectionManager, 1);
    }

    @Override
    protected Object execute(Object[] params) throws Exception {
        String fileName = params[0].toString();
        File file = new File(path + fileName);
        if (!file.exists())
            throw new Exception("Файл не найден!");

        if (this.collectionManager.CheckExecuteScript(fileName)) {
            this.collectionManager.RemoveExecuteScript(fileName);
            throw new Exception("Обнаружен рекурсивный вызов");
        }

        this.collectionManager.AddExecuteScript(fileName);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            CommandReader commandReader = new CommandReader(this.collectionManager, fileInputStream);
            commandReader.Start();
            return commandReader.GetCommandsBuffer() + "\nКоманда выполнена!";
        } catch (IOException e) {
            throw new Exception("Ошибка при чтении файла!");
        } finally {
            this.collectionManager.RemoveExecuteScript(fileName);
        }
    }

}
