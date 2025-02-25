package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * PrintAscendingUserCommand - класс команды для вывода элементов коллекции в порядке возрастания
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class PrintAscendingUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     * @param out класс для работы с вводом-выводом
     */
    public PrintAscendingUserCommand(CollectionManager collectionManager, IOManager out) {
        super("print_ascending", "print_ascending : вывести элементы коллекции в порядке возрастания", out);

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

        var collection = collectionManager.getCollection();

        Collections.reverse(collection);

        for(var mb : collection) {
            this.ioManager.writeLine(mb);
        }
    }
}
