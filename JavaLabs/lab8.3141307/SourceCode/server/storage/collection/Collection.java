package com.lab.server.storage.collection;

import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.common.user.User;
import com.lab.server.storage.dao.daos.MusicBandDAO;
import com.lab.server.storage.dataSource.DataSource;

import java.time.LocalDate;
import java.util.*;

/** Implementation of CollectionInterface for Hashtable */
public final class Collection implements CollectionInterface {
  private final MusicBandDAO musicBandDAO;
  private final Date initDate;
  private final Map<Integer, MusicBand> collection;

  public Collection(DataSource dataSource) {
    this.musicBandDAO = new MusicBandDAO(dataSource);
    this.initDate = new Date();
    this.collection = Collections.synchronizedMap(new Hashtable<>());
    this.collection.putAll(musicBandDAO.getAll());
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
  public Map<Integer, MusicBand> getRootCollectionCopy() {
    return new Hashtable<>(collection);
  }

  @Override
  public boolean remove(int key, User user) {
    MusicBand musicBand = musicBandDAO.getByKey(key);
    if (musicBand.getOwnerId() == user.getId()) {
      musicBandDAO.delete(musicBand);

      collection.remove(key);

      return true;
    }

    return false;
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
  public void put(int key, MusicBand musicBand) throws ValidationException {
    musicBand.setKey(key);
    musicBand.setCreationDate(LocalDate.now());
    MusicBand insertedMusicBand = musicBandDAO.insert(musicBand);
    musicBand.setKey(key);
    musicBand.setId(insertedMusicBand.getId());

    collection.put(key, musicBand);
  }

  @Override
  public boolean replace(int key, MusicBand newMusicBand, User user) throws ValidationException {
    MusicBand musicBand = musicBandDAO.getByKey(key);

    if (musicBand.getOwnerId() == user.getId()) {
      newMusicBand.setCreationDate(LocalDate.now());
      newMusicBand.setKey(key);
      newMusicBand.setOwnerId(user.getId());
      newMusicBand.setId(musicBand.getId());

      musicBandDAO.update(newMusicBand);

      collection.replace(key, newMusicBand);

      return true;
    }

    return false;
  }
}
