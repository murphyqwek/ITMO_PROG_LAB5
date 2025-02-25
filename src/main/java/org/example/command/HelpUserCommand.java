package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;
import org.example.manager.CommandManager;
import org.example.manager.iomanager.IOManager;

import java.util.ArrayList;

/**
 * HelpCommand - класс команды для вывода справки по доступным командам
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class HelpUserCommand extends UserCommand {
    private final CommandManager commandManager;

    /**
     * Конструктор класса
     * @param commandManager класс для управления командами
     * @param out класс для работы с вводом-выводом
     */
    public HelpUserCommand(CommandManager commandManager, IOManager out) {
        super("help", "help: вывести справку по доступным командам", out);

        this.commandManager = commandManager;
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

        for(UserCommand command : commandManager.getCommands()) {
            this.ioManager.writeLine(command.getDescription());
        }
    }
}
