package org.example.command;

import org.example.exceptions.CommandArgumentExcetpion;

import java.util.ArrayList;

/**
 * Executable - интерфейс для исполнения команд.
 *
 * @author Starikov Arseny
 * @version 1.0
 */
public interface Executable {
    /**
     * Метод для выполнения команд
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    public void execute(ArrayList<String> args) throws CommandArgumentExcetpion;
}
