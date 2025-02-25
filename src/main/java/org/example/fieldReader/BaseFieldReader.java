package org.example.fieldReader;

import org.example.manager.iomanager.IOManager;

import java.util.NoSuchElementException;

/**
 * StringFieldReader - базовый класс для считывания полей
 *
 * @author Starikov Arseny
 * @version 1.0
 */
public abstract class BaseFieldReader {
    protected final IOManager ioManager;
    protected String fieldAskString;
    protected boolean isErrorMessagePrints = true;

    /**
     * Конструктор класса
     * @param ioManager класс для работы с потоками ввода-вывода
     * @param fieldAskString строка для запроса поля
     */
    public BaseFieldReader(IOManager ioManager, String fieldAskString) {
        this.ioManager = ioManager;
        this.fieldAskString = fieldAskString;
    }

    /**
     * Метод для получения поля, которое прошло проверку на тип, в виде поля
     * @return возвращает поле в виде String
     * @throws InterruptedException если пользователь решил прервать ввод
     */
    protected String getFieldString() throws InterruptedException {
        String output;

        while(true) {
            ioManager.writeLine(fieldAskString);

            try {
                output = ioManager.readLine();
            }
            catch (NoSuchElementException e) {
                throw new InterruptedException();
            }

            if(isInputCorrect(output)) {
                break;
            }

            if(this.isErrorMessagePrints) {
                ioManager.writeError("Некорректное введенное поле");
            }
        }

        return output;
    }

    /**
     * Метод для проверки вводимого значения на тип
     * @param input вводимое поле
     * @return true, если вводимое поле прошло проверку на тип, и false в противном случае
     */
    public abstract boolean isInputCorrect(String input);

    /**
     * Метод для установки fieldAskString
     * @param newFieldAskString новое значение fieldAskString
     */
    public void setFieldAskString(String newFieldAskString) {
        this.fieldAskString = newFieldAskString;
    }
}
