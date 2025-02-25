package org.example.manager;

import org.example.command.*;
import org.example.file.FileManager;
import org.example.manager.iomanager.IOManager;
import org.example.parser.CsvCollectionManager;

/**
 * CommandManagerSetuper - класс для загрузки всех необходимых команд в CommandManager.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class CommandManagerSetuper {
    /**
     * Метод для загрузки в CommandManger стандартный набор команд
     * @param ioManager класс для работы с вводом-выводом
     * @param collectionManager класс для управления коллекцией
     * @param commandManager класс менеджера команд
     * @param csvCollectionManager класс для работы с файлом коллекции
     * @param fileManager класс для работы с файлами
     */
    public static void SetupCommandManager(IOManager ioManager, CollectionManager collectionManager, CommandManager commandManager, FileManager fileManager, CsvCollectionManager csvCollectionManager) {
        commandManager.addCommand(new AddUserCommand(collectionManager, ioManager));
        commandManager.addCommand(new ClearUserCommand(collectionManager, ioManager));
        commandManager.addCommand(new FilterContainsNameUserCommand(collectionManager, ioManager));
        commandManager.addCommand(new HelpUserCommand(commandManager, ioManager));
        commandManager.addCommand(new HistoryUserCommand(commandManager.getCommandHistoryManager(), ioManager));
        commandManager.addCommand(new InfoUserCommand(collectionManager, ioManager));
        commandManager.addCommand(new PrintAscendingUserCommand(collectionManager, ioManager));
        commandManager.addCommand(new RemoveByIdCommand(collectionManager, ioManager));
        commandManager.addCommand(new SumOfAlbumsCountUserCommand(collectionManager, ioManager));
        commandManager.addCommand(new ExitCommand(ioManager));
        commandManager.addCommand(new ShowUserCommand(collectionManager, ioManager));
        commandManager.addCommand(new UpdateUserCommand(collectionManager, ioManager));
        commandManager.addCommand(new RemoveLowerUserCommand(collectionManager, ioManager));
        commandManager.addCommand(new AddIfMaxUserCommand(collectionManager, ioManager));
        commandManager.addCommand(new SaveUserCommand(collectionManager, csvCollectionManager, ioManager));
        commandManager.addCommand(new ExecuteScriptUserCommand(collectionManager, fileManager, ioManager, csvCollectionManager));
    }
}
