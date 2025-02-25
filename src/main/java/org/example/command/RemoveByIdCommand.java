package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.exceptions.ElementNotFoundException;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;

import java.util.ArrayList;

/**
 * RemoveByIdCommand - класс команды для удаления элемента из коллекции по его id
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class RemoveByIdCommand extends UserCommand {
    private CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     * @param out класс для работы с вводом-выводом
     */
    public RemoveByIdCommand(CollectionManager collectionManager, IOManager out) {
        super("remove_by_id", "remove_by_id id : удалить элемент из коллекции по его id", out);

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
        if(args.size() != 1) {
            throw new CommandArgumentExcetpion("Неверное количество аргументов");
        }

        String idString = args.get(0);

        int id;
        try {
            id = Integer.parseInt(idString);
        }
        catch (NumberFormatException e) {
            throw new CommandArgumentExcetpion("id должно быть числом");
        }

        try {
            collectionManager.removeMusicBandById(id);
        }
        catch (ElementNotFoundException e) {
            ioManager.writeError("MusicBand с id " + id + " не найден в коллекции");
            return;
        }

        this.ioManager.writeLine(String.format("MusicBand с id %d успешно удален из коллекции", id));

    }
}
