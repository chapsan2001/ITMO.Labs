package com.lab.common.musicBand;

import com.lab.common.musicBand.exceptions.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Данный класс необходим для заполнения коллекции. Представляет из себя реализацию музыкальной
 * группы с информацией о ее номере, названии, координатах, количестве участников, жанре и лучшем
 * альбоме.
 */
public class MusicBand implements Comparable<MusicBand>, Serializable {
    /**
     * Поле номера группы. Значение не может быть null, должно принимать значения больше 0, быть
     * уникальным и генерироваться автоматически.
     */
    private Integer id;
    /**
     * Поле id владельца объекта.
     */
    private long ownerId;
    /**
     * Поле key совпадает с ключем в коллекции.
     */
    private int key;
    /**
     * Поле названия группы. Значение не может быть null и не может быть пустым.
     */
    private String name;
    /**
     * Поле координат группы. Значение не может быть null.
     *
     * @see Coordinates
     */
    private Coordinates coordinates;
    /**
     * Поле даты создания группы. Значение не может быть null и должно генерироваться автоматически.
     */
    private LocalDate creationDate;
    /**
     * Поле количества участников группы. Значение должно быть больше 0.
     */
    private long numberOfParticipants;
    /**
     * Поле жанра группы. Значение может быть null.
     *
     * @see MusicGenre
     */
    private MusicGenre genre;
    /**
     * Поле лучшего альбома группы. Значение может быть null.
     *
     * @see Album
     */
    private Album bestAlbum;

    /**
     * Конструктор класса Музыкальная группа - создание нового объекта с заданными параметрами.
     *
     * @param name                 Название группы
     * @param coordinates          Координаты группы
     * @param numberOfParticipants Количество участников группы
     * @param genre                Жанр группы
     * @param bestAlbum            Лучший альбом группы
     * @throws ValidationException если передано неверное значение
     * @see MusicBand#MusicBand()
     */
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

    /**
     * Конструктор класса Музыкальная группа - создание нового объекта.
     */
    public MusicBand() {
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Геттер поля номера музыкальной группы.{@link MusicBand#id}
     *
     * @return Возвращает номер музыкальной группы.
     * @see MusicBand#setId(Integer)
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Сеттер поля номера музыкальной группы.{@link MusicBand#id}
     *
     * @param id Номер музыкальной группы.
     * @throws ValidationException если передано неверное значение
     * @see MusicBand#getId()
     */
    public void setId(Integer id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("Значение id не может быть null.");
        }
        if (id <= 0) {
            throw new ValidationException("Значение id должно быть больше 0.");
        }

        this.id = id;
    }

    /**
     * Геттер поля названия музыкальной группы.{@link MusicBand#name}
     *
     * @return Возвращает название музыкальной группы.
     * @see MusicBand#setName(String)
     */
    public String getName() {
        return this.name;
    }

    /**
     * Сеттер поля названия музыкальной группы.{@link MusicBand#name}
     *
     * @param name Название музыкальной группы.
     * @throws ValidationException если передано неверное значение
     * @see MusicBand#getName()
     */
    public void setName(String name) throws ValidationException {
        if (name == null) {
            throw new ValidationException("Имя не может быть null.");
        }
        if (name.equals("")) {
            throw new ValidationException("Имя не может быть пустой строкой.");
        }

        this.name = name;
    }

    /**
     * Геттер поля координат музыкальной группы.{@link MusicBand#coordinates}
     *
     * @return Возвращает пару координат музыкальной группы.
     * @see MusicBand#setCoordinates(Coordinates)
     */
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /**
     * Сеттер поля координат музыкальной группы.{@link MusicBand#coordinates}
     *
     * @param coordinates Координаты музыкальной группы.
     * @throws ValidationException если передано неверное значение
     * @see MusicBand#getCoordinates()
     */
    public void setCoordinates(Coordinates coordinates) throws ValidationException {
        if (coordinates == null) {
            throw new ValidationException("Значение coordinates не может быть null.");
        }

        this.coordinates = coordinates;
    }

    /**
     * Геттер поля даты создания музыкальной группы.{@link MusicBand#creationDate}
     *
     * @return Возвращает дату создания музыкальной группы.
     * @see MusicBand#setCreationDate(LocalDate)
     */
    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    /**
     * Сеттер поля даты создания музыкальной группы.{@link MusicBand#creationDate}
     *
     * @param creationDate Дата создания музыкальной группы.
     * @throws ValidationException если передано неверное значение
     * @see MusicBand#getCreationDate()
     */
    public void setCreationDate(LocalDate creationDate) throws ValidationException {
        if (creationDate == null) {
            throw new ValidationException("Значение createDate не може быть null.");
        }

        this.creationDate = creationDate;
    }

    /**
     * Геттер поля количества участников музыкальной группы.{@link MusicBand#numberOfParticipants}
     *
     * @return Возвращает количество участников музыкальной группы.
     * @see MusicBand#setNumberOfParticipants(long)
     */
    public long getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    /**
     * Сеттер поля количества учатсников музыкальной группы.{@link MusicBand#numberOfParticipants}
     *
     * @param numberOfParticipants Количество участников музыкальной группы.
     * @throws ValidationException если передано неверное значение
     * @see MusicBand#getNumberOfParticipants()
     */
    public void setNumberOfParticipants(long numberOfParticipants) throws ValidationException {
        if (numberOfParticipants <= 0) {
            throw new ValidationException("Значение количества участиков должно юыть больше 0.");
        }

        this.numberOfParticipants = numberOfParticipants;
    }

    /**
     * Геттер поля жанра музыкальной группы.{@link MusicBand#genre}
     *
     * @return Возвращает жанр музыкальной группы.
     * @see MusicBand#setGenre(MusicGenre)
     */
    public MusicGenre getGenre() {
        return this.genre;
    }

    /**
     * Сеттер поля жанра музыкальной группы.{@link MusicBand#genre}
     *
     * @param genre Жанр музыкальной группы.
     * @see MusicBand#getGenre()
     */
    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    /**
     * Геттер поля лучшего альбома музыкальной группы.{@link MusicBand#bestAlbum}
     *
     * @return Возвращает лучший альбом музыкальной группы.
     * @see MusicBand#setBestAlbum(Album)
     */
    public Album getBestAlbum() {
        return this.bestAlbum;
    }

    /**
     * Сеттер поля лучшего альбома музыкальной группы.{@link MusicBand#bestAlbum}
     *
     * @param bestAlbum Лучший альбом музыкальной группы.
     * @see MusicBand#getBestAlbum()
     */
    public void setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
    }

    /**
     * Данный метод служит для получения хеш-кода на основе значений полей объекта класса.
     *
     * @return Возвращает хеш-код музыкальной группы.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate, numberOfParticipants, genre, bestAlbum);
    }

    /**
     * Данный метод сравнивает музыкальные группы по длине названия.
     *
     * @param other Музыкальная группа, с которой необходимо сравнить исходную.
     * @return Возвращает разность длин названий музыкальных групп.
     */
    @Override
    public int compareTo(MusicBand other) {
        return (this.name.length() - other.getName().length());
    }

    /**
     * Данный метод преобразует объект класса для строкового представления.
     *
     * @return Возвращает информацию о музыкальной группе в строковом виде.
     */
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
