package org.example;

import org.example.exceptions.*;
import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandManager;
import org.example.manager.CommandManagerSetuper;
import org.example.manager.iomanager.IOManager;
import org.example.manager.iomanager.StandartIOManager;
import org.example.models.MusicBand;
import org.example.parser.CsvCollectionManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

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
    private CsvCollectionManager csvCollectionManager;

    /**
     * Конструктор класса
     * @param filepath путь до файла с сохраненной коллекцией
     */
    public Run(String filepath) {
        this.collectionManager = new CollectionManager();
        this.commandManager = new CommandManager();
        this.filepath = filepath;
        this.ioManager = new StandartIOManager();
        this.csvCollectionManager = new CsvCollectionManager(filepath);
        setup();
    }

    private void uploadCollection() {
        try {
            Collection<MusicBand> collection = csvCollectionManager.uploadCollection();

            for(var mb : collection) {
                this.collectionManager.addNewMusicBand(mb);
            }
        } catch (IOException e) {
            ioManager.writeError("Не удалось загрузить файл, так как он не существует или уже открыт");
            System.exit(0);
        } catch (DeserializationException e) {
            ioManager.writeError("Не удалось загрузить коллекцию, так как файл поврежден");
            System.exit(0);
        }
    }

    /**
     * Метод для подготовки программы
     */
    private void setup() {
        uploadCollection();
        ioManager.writeLine("Коллекция успешно загружена!");
        CommandManagerSetuper.SetupCommandManager(this.ioManager, this.collectionManager, this.commandManager, this.fileManager, this.csvCollectionManager);
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

        String command = "";
        try {
            command = ioManager.readLine();
        }
        catch (NoSuchElementException e) {
            System.exit(0);
        }
        commandManager.execute(command);
    }
}
