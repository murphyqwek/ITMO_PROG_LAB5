package org.example.script;

import org.example.exceptions.DamageScriptException;
import org.example.exceptions.RecursionException;
import org.example.file.FileManager;
import org.example.file.FileReaderIterator;
import org.example.manager.CollectionManager;
import org.example.manager.CommandManager;
import org.example.manager.CommandManagerSetuper;
import org.example.manager.iomanager.FileIOManager;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

/**
 * ScriptManager - класс для запуска скриптов и мониторинга рекрусии.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ScriptManager {
    private static final HashSet<String> recursionSet;
    private final String filepath;
    private CollectionManager collectionManager;
    private FileManager fileManager;
    private CommandManager commandManager;

    static {
        recursionSet = new HashSet<>();
    }

    /**
     * Конструктор класса
     * @param filepath путь к скрипту
     * @param collectionManager класс для управления коллекцией
     * @param fileManager класс для взаимодействия с файлом коллекции
     */
    public ScriptManager(String filepath, CollectionManager collectionManager, FileManager fileManager) {
        this.filepath = filepath;
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.commandManager = new CommandManager();
    }

    /**
     * Метод для запуска исполнения скриптов
     * @throws RecursionException если возникла рекурсия
     * @throws DamageScriptException если файл скрипта поврежден и не может быть корректно выполенен
     */
    public void runScript() throws RecursionException, DamageScriptException {
        if(recursionSet.contains(new File(filepath).getAbsolutePath())) {
            throw new RecursionException();
        }

        recursionSet.add(new File(filepath).getAbsolutePath());

        FileReaderIterator fileReaderIterator;
        try {
            fileReaderIterator = new FileReaderIterator(filepath);
        } catch (IOException e) {
            recursionSet.remove(filepath);
            throw new DamageScriptException();
        }

        CommandManagerSetuper.SetupCommandManager(new FileIOManager(fileReaderIterator), collectionManager, commandManager, fileManager);

        while(fileReaderIterator.hasNext()) {
            String line = fileReaderIterator.next();
            line = line.replace("\n", "").replace("\r", "");

            try {
                commandManager.execute(line);
            }
            catch(Exception e) {
                recursionSet.remove(filepath);
                fileReaderIterator.close();
                throw new DamageScriptException();
            }
        }

        recursionSet.remove(filepath);
    }

    /**
     * Метод для получения текущей глубины рекурсии
     */
    public int getRecursionDepth() {
        return recursionSet.size();
    }

}
