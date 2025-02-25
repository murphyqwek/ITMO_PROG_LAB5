package org.example.command;

import org.example.manager.iomanager.IOManager;

/**
 * UserCommand - абстрактный класс команды, которые может вызывать пользователь
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public abstract class UserCommand implements Executable, Comparable<UserCommand> {
    private final String name;
    private final String description;
    protected IOManager ioManager;

    /**
     * Конструктор класса
     * @param name имя команды
     * @param description описание команды
     * @param ioManager класс для работы с вводом-выводом
     */
    public UserCommand(String name, String description, IOManager ioManager) {
        this.name = name;
        this.description = description;
        this.ioManager = ioManager;
    }

    /**
     * Метод для установки нового IOManager
     * @param ioManager новый IOManager
     */
    public void setIOManager(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    /**
     * Метод для получения IOManager
     * @return IOManager ioManager
     */
    public IOManager getIOManager() {
        return ioManager;
    }

    /**
     * Метод для получения имени команды
      * @return имя команды
     */
    public String getName() {
        return this.name;
    }

    /**
     * Метод для получения описания команды
     * @return описание
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Метод для сравнения двух команд. Сравнение происходит по имени
     * @param o the object to be compared.
     */
    @Override
    public int compareTo(UserCommand o) {
        return this.name.compareTo(o.name);
    }
}
