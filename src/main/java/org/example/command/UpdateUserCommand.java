package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.fieldReader.MusicBandFieldReader;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;
import org.example.models.MusicBand;

import java.util.ArrayList;

/**
 * UpdateUserCommand - класс команды для модифицирования элементов коллекции по id.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class UpdateUserCommand extends UserCommand {
    private CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для работы с коллекцией
     * @param ioManager класс для работы с вводом-выводом
     */
    public UpdateUserCommand(CollectionManager collectionManager, IOManager ioManager) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному", ioManager);

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
            throw new CommandArgumentExcetpion(getName() + " принимает только один однострочный аргумент - id элемента");
        }

        int id;
        try {
            id = Integer.parseInt(args.get(0));
        }
        catch (NumberFormatException e) {
            ioManager.writeError("Ошибка исполнения команды: id должно быть целым числом");
            return;
        }


        if(collectionManager.getMusicBandById(id) == null) {
            ioManager.writeError("Нет элемента с id " + id + " в коллекции");
            return;
        }

        try {
            MusicBand mb = new MusicBandFieldReader(this.ioManager).executeMusicBand(id);
            collectionManager.updateMusicBand(mb);

            ioManager.writeLine("Элемент с id " + id + " был успешно обновлен");
        } catch (InterruptedException e) {
            ioManager.writeError("Ввод прерван");
        }
    }
}
