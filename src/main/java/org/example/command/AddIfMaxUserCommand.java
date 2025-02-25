package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.fieldReader.MusicBandFieldReader;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;
import org.example.models.MusicBand;

import java.util.ArrayList;
import java.util.Collections;

/**
 * AddIfMaxUserCommand - класс команды для добавления нового элемента в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class AddIfMaxUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     * @param ioManager класс для управления ввода-вывода
     */
    public AddIfMaxUserCommand(CollectionManager collectionManager, IOManager ioManager) {
        super("add_if_max", "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции", ioManager);
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
            throw new CommandArgumentExcetpion(getName() + " не принимает однострочных аргументов");
        }

        MusicBand userMusicBand;
        try {
            int id = collectionManager.generateId();
            userMusicBand = new MusicBandFieldReader(ioManager).executeMusicBand(id);
        } catch (InterruptedException e) {
            ioManager.writeError("Ввод прерван");
            return;
        }

        var maxElement = Collections.max(collectionManager.getCollection());

        if(maxElement.compareTo(userMusicBand) < 0) {
            ioManager.writeLine("Ваша группа больше наибольшего элемента в коллекции\nДобавляем её в коллекцию");
            collectionManager.addNewMusicBand(userMusicBand);
            ioManager.writeLine("Группа успешно добавлена в коллекцию");
        }
        else {
            ioManager.writeLine("Ваша группа не превышает наибольшего элемента в коллекции, поэтому она не будет туда добавлена");
        }
    }
}
