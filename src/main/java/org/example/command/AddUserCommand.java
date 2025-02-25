package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.exceptions.IdAlreadyExistsException;
import org.example.fieldReader.MusicBandFieldReader;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;
import org.example.models.MusicBand;

import java.util.ArrayList;

/**
 * AddUserCommand - класс команды для добавления нового элемента в коллекцию.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class AddUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     * @param ioManager класс для работы с вводом-выводом
     */
    public AddUserCommand(CollectionManager collectionManager, IOManager ioManager) {
        super("add", "add {element}: добавить новый элемент в коллекцию", ioManager);

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
            throw new CommandArgumentExcetpion(getName() + " не принимает никаких однострочных аргументов");
        }

        MusicBand mb;
        try {
            int id = collectionManager.generateId();
            mb = new MusicBandFieldReader(this.ioManager).executeMusicBand(id);
        } catch (InterruptedException e) {
            ioManager.writeError("Прерывание ввода");
            return;
        }

        collectionManager.addNewMusicBand(mb);
        ioManager.writeLine("Группа успешно добавлена в коллекцию");
    }
}
