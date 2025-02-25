package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.exceptions.DamageScriptException;
import org.example.exceptions.RecursionException;
import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;
import org.example.parser.CsvCollectionManager;
import org.example.script.ScriptManager;

import java.util.ArrayList;

/**
 * ExecuteScriptUserCommand - класс команды для исполнения скриптов.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ExecuteScriptUserCommand extends UserCommand {
    private CollectionManager collectionManager;
    private FileManager fileManager;
    private CsvCollectionManager csvCollectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     * @param fileManager класс для взаимодействия с файлом коллекции
     * @param ioManager класс для работы с вводом-вывода
     * @param csvCollectionManager класс для работы с файлом коллекции
     */
    public ExecuteScriptUserCommand(CollectionManager collectionManager, FileManager fileManager, IOManager ioManager, CsvCollectionManager csvCollectionManager) {
        super("execute_script", "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.", ioManager);
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.csvCollectionManager = csvCollectionManager;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(ArrayList<String> args) throws CommandArgumentExcetpion {
        if(args.size() != 1) {
            throw new CommandArgumentExcetpion(getName() + " ожидает получить 1 аргумент");
        }

        ScriptManager scriptManager = new ScriptManager(args.get(0), collectionManager, fileManager, csvCollectionManager);

        try {
            scriptManager.runScript();
        }
        catch (DamageScriptException | RecursionException e) {
            if(scriptManager.getRecursionDepth() == 0) {
                if (e instanceof DamageScriptException) {
                    ioManager.writeError("Ошибка исполнения скрипта - скрипт поврежден");
                }
                else if (e instanceof RecursionException) {
                    ioManager.writeError("Обнаружена рекурсия. Выполнение скрипта приостановлена");
                }
                return;
            }

            throw e;
        }

        ioManager.writeLine("Скрипт успешно выполнен");
    }
}
