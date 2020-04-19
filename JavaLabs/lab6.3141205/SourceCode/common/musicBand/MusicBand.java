package com.lab.common.musicBand;

import com.lab.common.musicBand.exceptions.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class MusicBand implements Comparable<MusicBand>, Serializable {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private long numberOfParticipants;
    private MusicGenre genre;
    private Album bestAlbum;
    public MusicBand(
            String name,
            Coordinates coordinates,
            long numberOfParticipants,
            MusicGenre genre,
            Album bestAlbum)
            throws ValidationException {
        setName(name);
        setCoordinates(coordinates);
        setNumberOfParticipants(numberOfParticipants);
        setGenre(genre);
        setBestAlbum(bestAlbum);
    }
    public MusicBand() {
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("Значение id не может быть null.");
        }
        if (id <= 0) {
            throw new ValidationException("Значение id должно быть больше 0.");
        }
        this.id = id;
    }
    public String getName() {
        return this.name;
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
    public Coordinates getCoordinates() {
        return this.coordinates;
    }
    public void setCoordinates(Coordinates coordinates) throws ValidationException {
        if (coordinates == null) {
            throw new ValidationException("Значение coordinates не может быть null.");
        }
        this.coordinates = coordinates;
    }
    public LocalDate getCreationDate() {
        return this.creationDate;
    }
    public void setCreationDate(LocalDate creationDate) throws ValidationException {
        if (creationDate == null) {
            throw new ValidationException("Значение createDate не може быть null.");
        }

        this.creationDate = creationDate;
    }
    public long getNumberOfParticipants() {
        return this.numberOfParticipants;
    }
    public void setNumberOfParticipants(long numberOfParticipants) throws ValidationException {
        if (numberOfParticipants <= 0) {
            throw new ValidationException("Значение количества участиков должно юыть больше 0.");
        }

        this.numberOfParticipants = numberOfParticipants;
    }
    public MusicGenre getGenre() {
        return this.genre;
    }
    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }
    public Album getBestAlbum() {
        return this.bestAlbum;
    }
    public void setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate, numberOfParticipants, genre, bestAlbum);
    }
    @Override
    public int compareTo(MusicBand other) {
        return (this.name.length() - other.getName().length());
    }
    @Override
    public String toString() {
        if (this.bestAlbum != null && this.genre != null) {
            return ("ID: "
                    + this.id
                    + ", название исполнителя: "
                    + this.name
                    + ", координаты: ("
                    + this.coordinates.getX()
                    + ';'
                    + this.coordinates.getY()
                    + "), дата создания: "
                    + this.creationDate
                    + ", количество участников: "
                    + this.numberOfParticipants
                    + ", жанр: "
                    + this.genre.toString()
                    + ", лучший альбом: "
                    + this.bestAlbum.getName()
                    + " (количество треков: "
                    + this.bestAlbum.getTracks()
                    + ", продажи: "
                    + this.bestAlbum.getSales()
                    + ").");
        } else {
            if (this.bestAlbum == null && this.genre != null) {
                return ("ID: "
                        + this.id
                        + ", название исполнителя: "
                        + this.name
                        + ", координаты: ("
                        + this.coordinates.getX()
                        + ';'
                        + this.coordinates.getY()
                        + "), дата создания: "
                        + this.creationDate
                        + ", количество участников: "
                        + this.numberOfParticipants
                        + ", жанр: "
                        + this.genre.toString()
                        + ", лучший альбом: неизвестен.");
            } else {
                if (this.bestAlbum != null && this.genre == null) {
                    return ("ID: "
                            + this.id
                            + ", название исполнителя: "
                            + this.name
                            + ", координаты: ("
                            + this.coordinates.getX()
                            + ';'
                            + this.coordinates.getY()
                            + "), дата создания: "
                            + this.creationDate
                            + ", количество участников: "
                            + this.numberOfParticipants
                            + ", жанр: неизвестен, лучший альбом: "
                            + this.bestAlbum.getName()
                            + " (количество треков: "
                            + this.bestAlbum.getTracks()
                            + ", продажи: "
                            + this.bestAlbum.getSales()
                            + ").");
                } else {
                    return ("ID: "
                            + this.id
                            + ", название исполнителя: "
                            + this.name
                            + ", координаты: ("
                            + this.coordinates.getX()
                            + ';'
                            + this.coordinates.getY()
                            + "), дата создания: "
                            + this.creationDate
                            + ", количество участников: "
                            + this.numberOfParticipants
                            + ", жанр: неизвестен, лучший альбом: неизвестен.");
                }
            }
        }
    }
}
