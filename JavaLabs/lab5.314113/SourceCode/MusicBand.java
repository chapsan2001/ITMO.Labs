import MyExceptions.*;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Данный класс необходим для заполнения коллекции.
 * Представляет из себя реализацию музыкальной группы с информацией о ее номере, названии, координатах, количестве участников, жанре и лучшем альбоме.
 */
public class MusicBand implements Comparable<MusicBand>{
    /** Поле номера группы. Значение не может быть null, должно принимать значения больше 0, быть уникальным и генерироваться автоматически.*/
    private Integer id;
    /** Поле названия группы. Значение не может быть null и не может быть пустым.*/
    @JSONField(name = "name")
    private String name;
    /** Поле координат группы. Значение не может быть null.
     * @see Coordinates
     */
    @JSONField(name = "coordinates")
    private Coordinates coordinates;
    /** Поле даты создания группы. Значение не может быть null и должно генерироваться автоматически.*/
    private LocalDate creationDate;
    /** Поле количества участников группы. Значение должно быть больше 0.*/
    @JSONField(name = "numberOfParticipants")
    private long numberOfParticipants;
    /** Поле жанра группы. Значение может быть null.
     * @see MusicGenre
     */
    @JSONField(name = "genre")
    private MusicGenre genre;
    /** Поле лучшего альбома группы. Значение может быть null.
     * @see Album
     */
    @JSONField(name = "bestAlbum")
    private Album bestAlbum;

    /**
     * Конструктор класса Музыкальная группа - создание нового объекта с заданными параметрами.
     * @param name Название группы
     * @param coordinates Координаты группы
     * @param numberOfParticipants Количество участников группы
     * @param genre Жанр группы
     * @param bestAlbum Лучший альбом группы
     * @throws EmptyStringException Исключение выбрасывается в случае, если введена пустая строка там, где ее быть не должно.
     * @throws NullException Исключение выбрасывается в случае, если получено значение null там, где его быть не должно.
     * @throws ZeroException Исключение выбрасывается в случае, если получено значение 0 там, где его быть не должно.
     * @see MusicBand#MusicBand()
     */
    public MusicBand (String name, Coordinates coordinates, long numberOfParticipants, MusicGenre genre, Album bestAlbum) throws NullException, ZeroException, EmptyStringException {
        if (bestAlbum == null) {
            this.bestAlbum = new Album("undefined", 1,1);
        }
        if (name == null || coordinates == null) {
            throw new NullException();
        } else {
            if (id <= 0 || numberOfParticipants <= 0) {
                throw new ZeroException();
            } else {
                if (name.equals("")) {
                    throw new EmptyStringException();
                } else {
                    this.name = name;
                    this.coordinates = coordinates;
                    this.numberOfParticipants = numberOfParticipants;
                    this.genre = genre;
                    this.bestAlbum = bestAlbum;
                }
            }
        }
    }

    /**
     * Конструктор класса Музыкальная группа - создание нового объекта.
     */
    public MusicBand(){}
    /** Геттер поля номера музыкальной группы.{@link MusicBand#id}
     * @return Возвращает номер музыкальной группы.
     * @see MusicBand#setId(Integer)
     */
    public Integer getId(){ return this.id; }
    /** Геттер поля названия музыкальной группы.{@link MusicBand#name}
     * @return Возвращает название музыкальной группы.
     * @see MusicBand#setName(String)
     */
    public String getName(){ return this.name; }
    /** Геттер поля координат музыкальной группы.{@link MusicBand#coordinates}
     * @return Возвращает пару координат музыкальной группы.
     * @see MusicBand#setCoordinates(Coordinates)
     */
    public Coordinates getCoordinates(){ return this.coordinates; }
    /** Геттер поля даты создания музыкальной группы.{@link MusicBand#creationDate}
     * @return Возвращает дату создания музыкальной группы.
     * @see MusicBand#setCreationDate(LocalDate)
     */
    public java.time.LocalDate getCreationDate(){ return this.creationDate; }
    /** Геттер поля количества участников музыкальной группы.{@link MusicBand#numberOfParticipants}
     * @return Возвращает количество участников музыкальной группы.
     * @see MusicBand#setNumberOfParticipants(long)
     */
    public long getNumberOfParticipants(){ return this.numberOfParticipants; }
    /** Геттер поля жанра музыкальной группы.{@link MusicBand#genre}
     * @return Возвращает жанр музыкальной группы.
     * @see MusicBand#setGenre(MusicGenre)
     */
    public MusicGenre getGenre(){ return this.genre; }
    /** Геттер поля лучшего альбома музыкальной группы.{@link MusicBand#bestAlbum}
     * @return Возвращает лучший альбом музыкальной группы.
     * @see MusicBand#setBestAlbum(Album)
     */
    public Album getBestAlbum(){ return this.bestAlbum; }
    /** Сеттер поля номера музыкальной группы.{@link MusicBand#id}
     * @param id Номер музыкальной группы.
     * @see MusicBand#getId()
     */
    public void setId(Integer id){ this.id = id; }
    /** Сеттер поля названия музыкальной группы.{@link MusicBand#name}
     * @param name Название музыкальной группы.
     * @see MusicBand#getName()
     */
    public void setName(String name){ this.name = name; }
    /** Сеттер поля координат музыкальной группы.{@link MusicBand#coordinates}
     * @param coordinates Координаты музыкальной группы.
     * @see MusicBand#getCoordinates()
     */
    public void setCoordinates(Coordinates coordinates){ this.coordinates = coordinates; }
    /** Сеттер поля даты создания музыкальной группы.{@link MusicBand#creationDate}
     * @param creationDate Дата создания музыкальной группы.
     * @see MusicBand#getCreationDate()
     */
    public void setCreationDate(java.time.LocalDate creationDate){ this.creationDate = creationDate; }
    /** Сеттер поля количества учатсников музыкальной группы.{@link MusicBand#numberOfParticipants}
     * @param numberOfParticipants Количество участников музыкальной группы.
     * @see MusicBand#getNumberOfParticipants()
     */
    public void setNumberOfParticipants(long numberOfParticipants){ this.numberOfParticipants = numberOfParticipants; }
    /** Сеттер поля жанра музыкальной группы.{@link MusicBand#genre}
     * @param genre Жанр музыкальной группы.
     * @see MusicBand#getGenre()
     */
    public void setGenre(MusicGenre genre){ this.genre = genre; }
    /** Сеттер поля лучшего альбома музыкальной группы.{@link MusicBand#bestAlbum}
     * @param bestAlbum Лучший альбом музыкальной группы.
     * @see MusicBand#getBestAlbum()
     */
    public void setBestAlbum(Album bestAlbum){ this.bestAlbum = bestAlbum; }
    /**
     * Данный метод служит для получения хеш-кода на основе значений полей объекта класса.
     * @return Возвращает хеш-код музыкальной группы.
     */
    @Override
    public int hashCode(){return Objects.hash(name, coordinates, creationDate, numberOfParticipants, genre, bestAlbum);}
    /**
     * Данный метод сравнивает музыкальные группы по длине названия.
     * @param other Музыкальная группа, с которой необходимо сравнить исходную.
     * @return Возвращает разность длин названий музыкальных групп.
     */
    @Override
    public int compareTo(MusicBand other) {
        return (this.name.length() - other.getName().length());
    }
    /**
     * Данный метод преобразует объект класса для строкового представления.
     * @return Возвращает информацию о музыкальной группе в строковом виде.
     */
    @Override
    public String toString() {
        if (this.bestAlbum != null && this.genre != null) {
            return ("ID: " + this.id + ", название исполнителя: " + this.name + ", координаты: (" + this.coordinates.getX() + ';' + this.coordinates.getY() + "), дата создания: " + this.creationDate + ", количество участников: " + this.numberOfParticipants + ", жанр: " + this.genre.toString() + ", лучший альбом: " + this.bestAlbum.getName() + " (количество треков: " + this.bestAlbum.getTracks() + ", продажи: " + this.bestAlbum.getSales() + ").");
        } else {
            if (this.bestAlbum == null && this.genre != null) {
                return ("ID: " + this.id + ", название исполнителя: " + this.name + ", координаты: (" + this.coordinates.getX() + ';' + this.coordinates.getY() + "), дата создания: " + this.creationDate + ", количество участников: " + this.numberOfParticipants + ", жанр: " + this.genre.toString() + ", лучший альбом: неизвестен.");
            } else {
                if (this.bestAlbum != null && this.genre == null) {
                    return ("ID: " + this.id + ", название исполнителя: " + this.name + ", координаты: (" + this.coordinates.getX() + ';' + this.coordinates.getY() + "), дата создания: " + this.creationDate + ", количество участников: " + this.numberOfParticipants + ", жанр: неизвестен, лучший альбом: " + this.bestAlbum.getName() + " (количество треков: " + this.bestAlbum.getTracks() + ", продажи: " + this.bestAlbum.getSales() + ").");
                } else {
                    return ("ID: " + this.id + ", название исполнителя: " + this.name + ", координаты: (" + this.coordinates.getX() + ';' + this.coordinates.getY() + "), дата создания: " + this.creationDate + ", количество участников: " + this.numberOfParticipants + ", жанр: неизвестен, лучший альбом: неизвестен.");
                }
            }
        }
    }
}

