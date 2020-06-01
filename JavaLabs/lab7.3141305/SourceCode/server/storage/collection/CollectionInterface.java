package com.lab.server.storage.collection;

import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.common.user.User;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Collection shell interface
 */
public interface CollectionInterface {
    /**
     * Returns size of the collection
     *
     * @return Size of collection
     */
    int getSize();

    /**
     * Returns values of the collection
     *
     * @return Values of collection
     */
    Collection<?> values();

    /**
     * Returns initialization time of the collection
     *
     * @return Initialization time of the collection
     */
    Date getInitDate();

    /**
     * Returns key set
     *
     * @return Key set
     */
    Set<?> keySet();

    /**
     * Returns entry set of the collection
     *
     * @return Entry set of the collection
     */
    Set<?> getEntrySet();

    /**
     * Remove element from collection by key
     *
     * @param key  key for element to remove
     * @param user concrete user.
     * @return True if remove is correct.
     */
    boolean remove(int key, User user);

    /**
     * Returns element by key
     *
     * @param key key for element to be returned
     * @return Element by key
     */
    MusicBand get(int key);

    /**
     * Returns collection type
     *
     * @return Collection type
     */
    String getCollectionType();

    /**
     * Returns true if collection contains key
     *
     * @param key key to be checked
     * @return True if collection contains key
     */
    boolean containsKey(int key);

    /**
     * Puts value with key
     *
     * @param key       key for value
     * @param musicBand musicBand with key
     * @throws ValidationException in case of validation error.
     */
    void put(int key, MusicBand musicBand) throws ValidationException;

    /**
     * Replaces value with key
     *
     * @param key          key for value
     * @param newMusicBand value with key
     * @param user         concrete user.
     * @return True if replace if correct.
     * @throws ValidationException in case of validation error.
     */
    boolean replace(int key, MusicBand newMusicBand, User user) throws ValidationException;
}
