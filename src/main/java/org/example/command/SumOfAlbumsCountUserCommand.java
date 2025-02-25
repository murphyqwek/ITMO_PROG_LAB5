package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.manager.CollectionManager;
import org.example.manager.iomanager.IOManager;

import java.util.ArrayList;

/**
 * SumOfAlbumsCountUserCommand - класс команды для вывода суммы значений поля albumsCount для всех элементов коллекции
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class SumOfAlbumsCountUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     * @param out класс для работы с вводом-выводом
     */
    public SumOfAlbumsCountUserCommand(CollectionManager collectionManager, IOManager out) {
        super("sum_of_albums_count", "sum_of_albums_count: вывести сумму значений поля albumsCount для всех элементов коллекции", out);

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
        if (!args.isEmpty()) {
            throw new CommandArgumentExcetpion("Команда не принимает никаких аргументов");
        }

        long result = 0;
        for(var mb : this.collectionManager.getCollection()) {
            result += mb.getAlbumsCount();
        }

        this.ioManager.writeLine("Суммарное значение поля albumsCount: " + result);
    }
}
