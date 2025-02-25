package org.example.exceptions;

/**
 * ElementNotFoundException - исключение, когда элемент не был найден в коллекции
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String message) {
        super(message);
    }
}
