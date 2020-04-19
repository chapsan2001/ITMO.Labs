package com.lab.server.collection;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

public interface CollectionInterface {
    void clear();
    int getSize();
    Collection<?> values();
    Date getInitDate();
    Set<?> keySet();
    Set<?> getEntrySet();
    void remove(int key);
    Object get(int key);
    String getCollectionType();
    boolean containsKey(int key);
    void put(int key, Object value);
    void replace(int key, Object value);
}
