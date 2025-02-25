package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;
import org.example.parser.CsvParser;

import java.io.IOException;
import java.util.ArrayList;

/**
 * SaveUserCommand - класс команды для сохранения коллекции в файл.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class SaveUserCommand extends UserCommand {
    private FileManager fileManager;
    private CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     * @param fileManager класс для работы с файлом, куда будет сохранена коллекция
     * @param ioManager класс для работы с вводом-выводом
     */
    public SaveUserCommand(CollectionManager collectionManager, FileManager fileManager, IOManager ioManager) {
        super("save", "save: сохранить коллекцию в файл", ioManager);

        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(ArrayList<String> args) throws CommandArgumentExcetpion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion(getName() + " не принимает никакие аргументы");
        }

        var collection = collectionManager.getCollection();
        String serializedCollection = CsvParser.serialize(collection);

        try {
            fileManager.writeAllToFile(serializedCollection);
            ioManager.writeLine("Коллекция успешно сохранена!");
        } catch (IOException e) {
            ioManager.writeLine("Не удалось сохранить коллекцию в файл. Проверьте, что он существует, что вы имеете доступ на модификацию файла и что он не открыт в другой программе");
        }
    }
}
