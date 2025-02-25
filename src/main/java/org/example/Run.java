package org.example;

import org.example.exceptions.*;
import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandManager;
import org.example.manager.CommandManagerSetuper;
import org.example.manager.iomanager.IOManager;
import org.example.manager.iomanager.StandartIOManager;
import org.example.parser.CsvParser;

import java.io.IOException;

/**
 * Run - описание класса.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class Run {
    private CollectionManager collectionManager;
    private CommandManager commandManager;
    private IOManager ioManager;
    private String filepath;
    private FileManager fileManager;

    /**
     * Конструктор класса
     * @param filepath путь до файла с сохраненной коллекцией
     */
    public Run(String filepath) {
        this.collectionManager = new CollectionManager();
        this.commandManager = new CommandManager();
        this.filepath = filepath;
        this.ioManager = new StandartIOManager();
        setup();
    }

    private void uploadCollection() {
        try {
            this.fileManager = new FileManager(filepath);
        } catch (IOException e) {
            ioManager.writeError("Ошибка загрузки коллекции: открыть файл или создать файл");
            System.exit(0);
        }

        String data = "";
        try {
            data = this.fileManager.readAll();
        } catch (IOException e) {
            ioManager.writeError("Ошибка загрузки коллекции: " + e.getMessage());
            System.exit(0);
        }

        try {
            var collection = CsvParser.deserialize(data);
            for(var mb : collection) {
                this.collectionManager.addNewMusicBand(mb);
            }
        }
        catch (DeserializationException | IdAlreadyExistsException e) {
            ioManager.writeError("Ошибка загрузки коллекции: файл поврежден");
            System.exit(0);
        }
    }

    /**
     * Метод для подготовки программы
     */
    private void setup() {
        uploadCollection();
        ioManager.writeLine("Коллекция успешно загружена!");
        CommandManagerSetuper.SetupCommandManager(this.ioManager, this.collectionManager, this.commandManager, this.fileManager);
    }

    /**
     * Запускает программу
     */
    public void run() {
        while(true) {
            cycle();
        }
    }

    /**
     * Метод цикла программы
     */
    private void cycle() {
        ioManager.writeLine("Введите команду: ");
        String command = ioManager.readLine();

        commandManager.execute(command);
    }
}
