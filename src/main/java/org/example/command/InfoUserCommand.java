package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;

import java.util.ArrayList;

/**
 * InfoUserCommand - класс команды для вывода информации о коллекции (тип, дата инициализации, количество элементов и т.д.)
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class InfoUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     * @param out класс для работы с вводом-выводом
     */
    public InfoUserCommand(CollectionManager collectionManager, IOManager out) {
        super("info", "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", out);

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
            throw new CommandArgumentExcetpion("Команда не принимает никаких аргументов");
        }

        var collection = this.collectionManager.getCollection();
        String collectionType = collection.getClass().toString();
        String date = this.collectionManager.getCollectionCreationDate().toString();
        int countElements = collection.size();

        this.ioManager.writeLine(String.format("Тип коллекции: %s\nДата создания: %s\nКол-во элементов: %d", collectionType, date, countElements));
    }
}
