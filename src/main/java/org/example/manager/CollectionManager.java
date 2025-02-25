package org.example.manager;

import org.example.exceptions.ElementNotFoundException;
import org.example.exceptions.IdAlreadyExistsException;
import org.example.models.MusicBand;
import org.example.utils.Sortable;

import java.util.*;

/**
 * CollectionManager - класс для управления коллекцией
 *
 * @author Starikov Arseny
 * @version 1.0
 */
public class CollectionManager implements Sortable {
    private final ArrayList<MusicBand> collection;
    private int lastId;
    private final Date collectionCreationDate;

    /**
     * Конструктор класса
     */
    public CollectionManager() {
        this.collection = new ArrayList<>();
        this.lastId = 1;
        this.collectionCreationDate = new Date();
    }

    /**
     * Метод для получения даты создания коллекции
     * @return дату создания класса
     */
    public Date getCollectionCreationDate() {
        return collectionCreationDate;
    }

    /**
     * Методы для получения коллекции
     * @return неизменяемую коллекцию
     */
    public List<MusicBand> getCollection() {
        return Collections.unmodifiableList(this.collection);
    }

    /**
     * Метод для получения элемента коллекции по полю id
     * @param id поле класса MusicBand
     * @return элемент коллекции с соответствующим полем id
     */
    public MusicBand getMusicBandById(int id) {
        for (MusicBand mb : collection) {
            if(mb.getId() == id) {
                return mb;
            }
        }

        return null;
    }

    /**
     * Метод для генерации id
     * @return id, который нет ещё ни у какого элемента в коллекции
     */
    public int generateId() {
        return this.lastId + 1;
    }

    /**
     * Метод для добавления нового элемента в коллекцию
     * @param mb новый элемент типа MusicBand
     * @throws IdAlreadyExistsException если в коллекции уже есть элемент с таким же полем id
     */
    public void addNewMusicBand(MusicBand mb) throws IdAlreadyExistsException {
        if(getMusicBandById(mb.getId()) != null) {
            throw new IdAlreadyExistsException(mb.getId());
        }

        this.lastId = Math.max(this.lastId, mb.getId());
        this.collection.add(mb);
        sort();
    }

    /**
     * Метод для удаления элемента с заданным id
     * @param id индикатор удаляемого элемента
     * @throws ElementNotFoundException если в коллекции нет элемента с заданным индикатором
     */
    public void removeMusicBandById(int id) throws ElementNotFoundException {
        var mb = this.getMusicBandById(id);

        if(mb == null) {
            throw new ElementNotFoundException("MusicBand с id " + id + " не найден");
        }

        removeMusicBand(mb);
    }

    /**
     * Метод для удаления элемента из коллекции
     * @param mb удаляемый элемент
     */
    private void removeMusicBand(MusicBand mb) {
        this.collection.remove(mb);
        sort();
    }

    /**
     * Метод для очищения коллекции
     */
    public void clear() {
        this.collection.clear();
    }

    /**
     * Метод для обновления элемента коллекции
     * @param newMusicBand новый элемент коллекции. Старый элемент коллекции находится по id нового элемента
     */
    public void updateMusicBand(MusicBand newMusicBand) {
        MusicBand mb = this.getMusicBandById(newMusicBand.getId());

        if (mb == null) {
            throw new ElementNotFoundException("Элемент не был найден в коллекции");
        }

        int index = this.collection.indexOf(mb);

        this.collection.set(index, newMusicBand);
    }

    /**
     * Метод для проверки id
     * @param id проверяемое значение
     * @return true, если в коллекции уже есть элемент с заданным id, false в противном случае
     */
    private boolean containsId(int id) {
        for (MusicBand mb : collection) {
            if(mb.getId() == id) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void sort() {
        this.collection.sort(new Comparator<MusicBand>() {
            @Override
            public int compare(MusicBand o1, MusicBand o2) {
                return o1.compareTo(o2);
            }
        });
    }
}
