package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;

import java.util.ArrayList;

/**
 * ClearUserCommand - класс команды для очищения коллекции.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ClearUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класс
     * @param collectionManager класс менеджера коллекции
     * @param ioManager класс для работы с вводом-выводом
     */
    public ClearUserCommand(CollectionManager collectionManager, IOManager ioManager) {
        super("clear", "clear : очистить коллекцию", ioManager);
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

        this.collectionManager.clear();
        this.ioManager.writeLine("Коллекция очищена");
    }
}
