package org.example.exceptions;

/**
 * InterruptedInputException - исключение, когда пользователь решил прервать ввод значения
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class InterruptedInputException extends RuntimeException {
    public InterruptedInputException(String message) {
        super(message);
    }
}
