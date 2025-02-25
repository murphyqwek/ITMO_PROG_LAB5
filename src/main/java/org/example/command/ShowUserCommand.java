package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;

import java.util.ArrayList;

/**
 * ShowUserCommand - класс команды для отображения всех элементов коллекции в строковом виде.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ShowUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для работы с коллекцией
     * @param ioManager класс для работы с вводом-выводом
     */
    public ShowUserCommand(CollectionManager collectionManager, IOManager ioManager) {
        super("show", "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении", ioManager);
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
            throw new CommandArgumentExcetpion(getName() + " не принимает никаких аргументов");
        }

        var collection = collectionManager.getCollection();

        if(collection.isEmpty()) {
            ioManager.writeLine("Коллекция пуста");
            return;
        }


        for(var musicBand : collection) {
            ioManager.writeLine(musicBand);
        }
    }
}
