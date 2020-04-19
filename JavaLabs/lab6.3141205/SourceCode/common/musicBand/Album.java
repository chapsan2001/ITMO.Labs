package com.lab.common.musicBand;

import com.lab.common.musicBand.exceptions.ValidationException;

import java.io.Serializable;

public class Album implements Serializable {
    private String name;
    private int tracks;
    private float sales;
    public Album(String name, int tracks, float sales) throws ValidationException {
        setName(name);
        setTracks(tracks);
        setSales(sales);
    }
    public Album() {}
    public String getName() {
        return name;
    }
    public void setName(String name) throws ValidationException {
        if (name == null) {
            throw new ValidationException("Имя не может быть null.");
        }
        if (name.equals("")) {
            throw new ValidationException("Имя не может быть пустой строкой.");
        }
        this.name = name;
    }
    public float getSales() {
        return sales;
    }
    public void setSales(float sales) throws ValidationException {
        if (tracks < 0) {
            throw new ValidationException("Количество продаж не может быть меньше 0.");
        }

        this.sales = sales;
    }
    public int getTracks() {
        return tracks;
    }
    public void setTracks(int tracks) throws ValidationException {
        if (tracks < 0) {
            throw new ValidationException("Количество треков в альбоме не может быть меньше 0.");
        }
        this.tracks = tracks;
    }
}
