package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.fieldReader.MusicBandFieldReader;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;
import org.example.models.MusicBand;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * RemoveLowerUserCommand - класс команды для удаления из коллекции всех элементов, меньшие, чем заданный.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class RemoveLowerUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для работы с коллекцией
     * @param ioManager класс для работы с вводом-выводом
     */
    public RemoveLowerUserCommand(CollectionManager collectionManager, IOManager ioManager) {
        super("remove_lower", "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный", ioManager);

        this.collectionManager = collectionManager;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список аргументов типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(ArrayList<String> args) throws CommandArgumentExcetpion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion(getName() + " не принимает никаких однострочных аргументов");
        }

        MusicBand userMusicBand;
        try {
            int id = collectionManager.generateId();
            userMusicBand = new MusicBandFieldReader(ioManager).executeMusicBand(id);
        } catch (InterruptedException e) {
            ioManager.writeError("Ввод прерван");
            return;
        }

        LinkedList<MusicBand> musicBandsToDelete = new LinkedList<>();
        for(var musicBand : collectionManager.getCollection()) {
            if(musicBand.compareTo(userMusicBand) < 0) {
                musicBandsToDelete.add(musicBand);
            }
        }

        for(var mb : musicBandsToDelete) {
            collectionManager.removeMusicBandById(mb.getId());
        }

        ioManager.writeLine("Команда успешно выполнилась");
    }
}
