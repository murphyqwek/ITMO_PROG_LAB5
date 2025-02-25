package org.example.utils;

/**
 * Validatable - интерфейс объектов, которые поддерживают валидацию
 *
 * @author Starikov Arseny
 * @version 1.0
 */
public interface Validatable {
    /**
     * Метод для проверки валидации объекта
     * @return true, если объект проходит валидацию, false, если не проходит валидацию
     */
    public boolean isValid();
}
