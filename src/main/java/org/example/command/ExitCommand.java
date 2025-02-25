package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.manager.iomanager.IOManager;

import java.util.ArrayList;

/**
 * ExitCommand - класс команды для выхода из программы без сохранения в файл.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ExitCommand extends UserCommand{

    /**
     * Конструктор класса
     * @param ioManager класс для работы с вводом-выводом
     */
    public ExitCommand(IOManager ioManager) {
        super("exit", "exit: завершить программу (без сохранения в файл)", ioManager);
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

        ioManager.writeLine("Завершение программы");
        System.exit(0);
    }
}
