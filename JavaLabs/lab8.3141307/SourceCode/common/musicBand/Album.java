package com.lab.common.musicBand;

import com.lab.common.musicBand.exceptions.ValidationException;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Данный класс необходим для заполнения поля Лучший альбом у объекта класса MusicBand. {@link
 * MusicBand} Представляет из себя реализацию музыкального альбома с информацией о названии,
 * количестве треков и продажах.
 */
public class Album implements Serializable {
  /** Поле названия альбома. Значение не может быть null, не может быть пустым. */
  private String name;
  /** Поле количества треков в альбоме. Значение должно быть больше 0. */
  private int tracks;
  /** Поле продаж альбома. Значение должно быть больше 0. */
  private float sales;

  /**
   * Конструктор класса Альбом - создание нового объекта c заданными параметрами
   *
   * @param name Название альбома
   * @param sales Продажи альбома
   * @param tracks Количество треков в альбоме
   * @throws ValidationException если передано неверное значение
   * @see Album#Album()
   */
  public Album(String name, int tracks, float sales) throws ValidationException {
    setName(name);
    setTracks(tracks);
    setSales(sales);
  }

  /**
   * Конструктор класса Альбом - создание нового объекта
   *
   * @see Album#Album(String, int, float)
   */
  public Album() {}

  /**
   * Геттер поля имени класса Альбом {@link Album#name}
   *
   * @return возвращает название альбома
   * @see Album#setName(String)
   */
  public String getName() {
    return name;
  }

  /**
   * Сеттер поля названия класса Альбом {@link Album#name}
   *
   * @param name Название альбома
   * @throws ValidationException если передано неверное значение
   * @see Album#getName()
   */
  public void setName(String name) throws ValidationException {
    if (name == null) {
      throw new ValidationException(ResourceBundle.getBundle("Album").getString("wrongNameNull"));
    }
    if (name.isEmpty()) {
      throw new ValidationException(ResourceBundle.getBundle("Album").getString("wrongNameEmpty"));
    }

    this.name = name;
  }

  /**
   * Геттер поля продаж класса Альбом {@link Album#sales}
   *
   * @return возвращает количество продаж альбома
   * @see Album#setSales(float)
   */
  public float getSales() {
    return sales;
  }

  /**
   * Сеттер поля продаж класса Альбом {@link Album#sales}
   *
   * @param sales Продажи альбома
   * @throws ValidationException если передано неверное значение
   * @see Album#getSales()
   */
  public void setSales(float sales) throws ValidationException {
    if (tracks < 0) {
      throw new ValidationException(ResourceBundle.getBundle("Album").getString("wrongSales"));
    }

    this.sales = sales;
  }

  /**
   * Геттер поля количества треков класса Альбом {@link Album#tracks}
   *
   * @return возвращает количество треков в альбоме
   * @see Album#setTracks(int)
   */
  public int getTracks() {
    return tracks;
  }

  /**
   * Сеттер поля количества треков класса Альбом {@link Album#tracks}
   *
   * @param tracks Количество треков в альбоме
   * @throws ValidationException если передано неверное значение
   * @see Album#getTracks()
   */
  public void setTracks(int tracks) throws ValidationException {
    if (tracks < 0) {
      throw new ValidationException(ResourceBundle.getBundle("Album").getString("wrongTracks"));
    }

    this.tracks = tracks;
  }
}
