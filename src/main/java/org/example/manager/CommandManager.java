package org.example.manager;

import org.example.command.*;
import org.example.exceptions.CommandArgumentExcetpion;
import org.example.exceptions.UnknownCommandException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * CommandManager - класс для управления вызовом команд
 *
 * @author Starikov Arseny
 * @version 1.0
 */
public class CommandManager {
    private HashMap<String, UserCommand> commands;
    private CommandHistoryManager commandHistoryManager;

    /**
     * Конструктор класса
     */
    public CommandManager() {
        this.commands = new HashMap<>();
        this.commandHistoryManager = new CommandHistoryManager();
    }

    /**
     * Метод для добавления новой команды
     * @param command новая исполняемая команда
     */
    public void addCommand(UserCommand command) {
        addCommand(command.getName(), command);
    }

    /**
     * Метод для добавления новой команды
     * @param name имя команды, по которой будет происходить её вызов
     * @param command новая исполняемая команда
     */
    public void addCommand(String name, UserCommand command) {
        this.commands.put(name, command);
    }

    /**
     * Метод для исполнения команды
     * @param input строка с именем команды и её аргументами
     * @throws UnknownCommandException если не удалось определить команду
     */
    public void execute(String input) {
        var arguments = parsingInputCommand(input);

        String commandName = arguments.get(0);
        arguments.remove(0);

        UserCommand executingCommand = this.commands.get(commandName);
        if(executingCommand == null) {
            System.err.println("Неопознанная команда");
            return;
        }

        try {
            executingCommand.execute(arguments);
            this.commandHistoryManager.addCommand(executingCommand);
        }
        catch (CommandArgumentExcetpion e) {
            System.err.println("ОШИБКА исполнения команды : " + e.getMessage());
        }
    }

    /**
     * Метод для парсинга входного значения
     * @param input входное значение
     * @return список, где первый элемент - имя команды, а остальные - аргументы к этой команде
     */
    private ArrayList<String> parsingInputCommand(String input) {
        input = input.trim();

        ArrayList<String> commands = new ArrayList<>();
        for(var part : input.split(" ")) {
            commands.add(part.trim());
        }

        return commands;
    }

    /**
     * Получение всех команд в отсортированном по имени порядке
     * @return коллекция команд
     */
    public Collection<UserCommand> getCommands() {
        ArrayList<UserCommand> sortedCommands = new ArrayList<>(this.commands.values());
        Collections.sort(sortedCommands);
        return sortedCommands;
    }

    /**
     * Получение истории команд
     * @return история команд
     */
    public CommandHistoryManager getCommandHistoryManager() {
        return this.commandHistoryManager;
    }
}
