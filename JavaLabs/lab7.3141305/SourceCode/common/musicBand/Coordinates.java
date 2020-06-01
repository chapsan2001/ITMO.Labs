package com.lab.common.musicBand;

import com.lab.common.musicBand.exceptions.ValidationException;

import java.io.Serializable;

/**
 * Данный класс необходим для заполнения поля Координаты у объекта класса MusicBand. {@link
 * MusicBand} Представляет из себя реализацию пары чисел (целого и дробного).
 */
public class Coordinates implements Serializable {
    /**
     * Поле координаты x. Значение не может быть null и должно быть больше -492.
     */
    private Long x;
    /**
     * Поле координаты y. Значение не может быть null и должно быть меньше 19.
     */
    private float y;

    /**
     * Конструктор класса Координаты - создание нового объекта с заданными параметрами.
     *
     * @param x Координата x.
     * @param y Координата y.
     * @throws ValidationException если передано неверное значение
     * @see Coordinates#Coordinates()
     */
    public Coordinates(Long x, float y) throws ValidationException {
        setX(x);
        setY(y);
    }

    /**
     * Конструктор класса Координаты - создание нового объекта.
     */
    public Coordinates() {
    }

    /**
     * Геттер поля координаты X класса Координаты {@link Coordinates#x}
     *
     * @return Возвращает координату X
     * @see Coordinates#setX(Long)
     */
    public Long getX() {
        return x;
    }

    /**
     * Сеттер поля координаты X класса Координаты {@link Coordinates#x}
     *
     * @param x Координата X
     * @throws ValidationException если передано неверное значение
     * @see Coordinates#getX()
     */
    public void setX(Long x) throws ValidationException {
        if (x == null) {
            throw new ValidationException("Значение x не может быть null.");
        }
        if (x <= -492) {
            throw new ValidationException("Значение x должно быть больше -492.");
        }

        this.x = x;
    }

    /**
     * Геттер поля координаты Y класса Координаты {@link Coordinates#y}
     *
     * @return Возвращает координату Y
     * @see Coordinates#setY(float)
     */
    public Float getY() {
        return y;
    }

    /**
     * Сеттер поля координаты Y класса Координаты {@link Coordinates#y}
     *
     * @param y Координата Y
     * @throws ValidationException если передано неверное значение
     * @see Coordinates#getY()
     */
    public void setY(float y) throws ValidationException {
        if (y >= 19) {
            throw new ValidationException("Значение y должно быть меньше 19.");
        }

        this.y = y;
    }
}
