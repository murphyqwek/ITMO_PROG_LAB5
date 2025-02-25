package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;

import java.util.ArrayList;

/**
 * HistoryUserCommand - класс команды для вывода последних 10 команд (без их аргументов)
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class HistoryUserCommand extends UserCommand {
    private CommandHistoryManager commandHistoryManager;

    /**
     * Конструктор класса
     * @param commandHistoryManager класс для управления историей команд
     * @param out класс для работы с вводом-выводом
     */
    public HistoryUserCommand(CommandHistoryManager commandHistoryManager, IOManager out) {
        super("history", "history: вывести последние 10 команд (без их аргументов)", out);

        this.commandHistoryManager = commandHistoryManager;
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

        for(var command : commandHistoryManager.getCommandHistory(10)) {
            this.ioManager.writeLine(command.getName());
        }
    }
}
