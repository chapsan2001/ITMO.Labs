package com.lab.server.collection;

import com.lab.common.musicBand.MusicBand;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public final class Collection implements CollectionInterface {
    private Date initDate;
    private Hashtable<Integer, MusicBand> collection;
    public Collection() {
        initDate = new Date();
        collection = new Hashtable<>();
    }
    @Override
    public void clear() {
        collection.clear();
    }
    @Override
    public int getSize() {
        return collection.size();
    }
    @Override
    public java.util.Collection<MusicBand> values() {
        return collection.values();
    }
    @Override
    public Date getInitDate() {
        return initDate;
    }
    @Override
    public Set<Integer> keySet() {
        return collection.keySet();
    }
    @Override
    public Set<Map.Entry<Integer, MusicBand>> getEntrySet() {
        return collection.entrySet();
    }
    @Override
    public void remove(int key) {
        collection.remove(key);
    }
    @Override
    public MusicBand get(int key) {
        return collection.get(key);
    }
    @Override
    public String getCollectionType() {
        return collection.getClass().getName();
    }
    @Override
    public boolean containsKey(int key) {
        return collection.containsKey(key);
    }
    @Override
    public void put(int key, Object value) {
        collection.put(key, (MusicBand) value);
    }
    @Override
    public void replace(int key, Object value) {
        collection.replace(key, (MusicBand) value);
    }
}
