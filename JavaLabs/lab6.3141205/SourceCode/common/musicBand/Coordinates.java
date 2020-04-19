package com.lab.common.musicBand;

import com.lab.common.musicBand.exceptions.ValidationException;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Long x;
    private float y;
    public Coordinates(Long x, float y) throws ValidationException {
        setX(x);
        setY(y);
    }
    public Coordinates() {
    }
    public Long getX() {
        return x;
    }
    public void setX(Long x) throws ValidationException {
        if (x == null) {
            throw new ValidationException("Значение x не может быть null.");
        }
        if (x <= -492) {
            throw new ValidationException("Значение x должно быть больше -492.");
        }

        this.x = x;
    }
    public Float getY() {
        return y;
    }
    public void setY(float y) throws ValidationException {
        if (y >= 19) {
            throw new ValidationException("Значение y должно быть меньше 19.");
        }
        this.y = y;
    }
}
