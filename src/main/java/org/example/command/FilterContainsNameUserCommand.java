package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;

import java.util.ArrayList;

/**
 * FilterContainsNameUserCommand - класс команды для нахождения элементов, у которых поле name содержит заданную подстроку
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class FilterContainsNameUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     * @param out класс для работы с вводом-выводом
     */
    public FilterContainsNameUserCommand(CollectionManager collectionManager, IOManager out) {
        super("filter_contains_name", "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку", out);

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

        String name = args.get(0);

        ioManager.writeLine("Поиск совпадений: ");
        for(var mb : collectionManager.getCollection()) {
            if(name.contains(mb.getName())) {
                this.ioManager.writeLine(mb);
            }
        }
    }
}
