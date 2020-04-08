import MyExceptions.*;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Данный класс необходим для заполнения поля Лучший альбом у объекта класса MusicBand. {@link MusicBand}
 * Представляет из себя реализацию музыкального альбома с информацией о названии, количестве треков и продажах.
 */
public class Album {
    /** Поле названия альбома. Значение не может быть null, не может быть пустым.*/
    @JSONField(name = "name")
    private String name;
    /** Поле количества треков в альбоме. Значение должно быть больше 0.*/
    @JSONField(name = "tracks")
    private int tracks;
    /** Поле продаж альбома. Значение должно быть больше 0.*/
    @JSONField(name = "sales")
    private float sales;

    /**
     * Конструктор класса Альбом - создание нового объекта c заданными параметрами
     * @param name Название альбома
     * @param sales Продажи альбома
     * @param tracks Количество треков в альбоме
     * @throws EmptyStringException Исключение выбрасывается в случае, если введена пустая строка там, где ее быть не должно.
     * @throws NullException Исключение выбрасывается в случае, если получено значение null там, где его быть не должно.
     * @throws ZeroException Исключение выбрасывается в случае, если получено значение 0 там, где его быть не должно.
     * @see Album#Album()
     */
    public Album(String name, int tracks, float sales) throws EmptyStringException, NullException, ZeroException {
        if (name == null) {
            throw new NullException();
        } else {
            if (name == "") {
                throw new EmptyStringException();
            } else {
                if (tracks == 0 || sales == 0) {
                    throw new ZeroException();
                } else {
                    this.name = name;
                    this.tracks = tracks;
                    this.sales = sales;
                }
            }
        }
    }
    /** Конструктор класса Альбом - создание нового объекта
     * @see Album#Album(String, int, float)
     */
    public Album(){}
    /** Сеттер поля названия класса Альбом {@link Album#name}
     * @param name Название альбома
     * @see Album#getName()
     */
    public void setName(String name) {this.name = name;}
    /** Сеттер поля продаж класса Альбом {@link Album#sales}
     * @param sales Продажи альбома
     * @see Album#getSales()
     */
    public void setSales(float sales) {this.sales = sales;}
    /** Сеттер поля количества треков класса Альбом {@link Album#tracks}
     * @param tracks Количество треков в альбоме
     * @see Album#getTracks()
     */
    public void setTracks(int tracks) {this.tracks = tracks;}
    /** Геттер поля имени класса Альбом {@link Album#name}
     * @return возвращает название альбома
     * @see Album#setName(String)
     */
    public String getName() {return name;}
    /** Геттер поля продаж класса Альбом {@link Album#sales}
     * @return возвращает количество продаж альбома
     * @see Album#setSales(float)
     */
    public float getSales() {return sales;}
    /** Геттер поля количества треков класса Альбом {@link Album#tracks}
     * @return возвращает количество треков в альбоме
     * @see Album#setTracks(int)
     */
    public int getTracks() {return tracks;}
}