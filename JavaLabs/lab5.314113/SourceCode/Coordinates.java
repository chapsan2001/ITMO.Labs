import MyExceptions.*;
/**
 * Данный класс необходим для заполнения поля Координаты у объекта класса MusicBand. {@link MusicBand}
 * Представляет из себя реализацию пары чисел (целого и дробного).
 */
public class Coordinates {
    /** Поле координаты x. Значение не может быть null и должно быть больше -492.*/
    private Long x;
    /** Поле координаты y. Значение не может быть null и должно быть меньше 19.*/
    private float y;
    /**
     * Конструктор класса Координаты - создание нового объекта с заданными параметрами.
     * @param x Координата x.
     * @param y Координата y.
     * @throws NullException Исключение выбрасывается в случае, если получено значение null там, где его быть не должно.
     * @throws CoordinatesOutOfBoundsException Исключение выбрасывается, если хотя бы одна из координат выходит за допустимые границы.
     * @see Coordinates#Coordinates()
     */
    public Coordinates(Long x, Float y) throws NullException, CoordinatesOutOfBoundsException {
        if (x == null || y == null) {
            throw new NullException();
        } else {
            if (x <= -492 || y > 19) {
                throw new CoordinatesOutOfBoundsException();
            } else {
                this.x = x;
                this.y = y;
            }
        }
    }
    /**
     * Конструктор класса Координаты - создание нового объекта.
     */
    public Coordinates(){}
    /**
     * Геттер поля координаты X класса Координаты {@link Coordinates#x}
     * @return Возвращает координату X
     * @see Coordinates#setX(Long)
     */
    public Long getX() {return x;}
    /**
     * Сеттер поля координаты X класса Координаты {@link Coordinates#x}
     * @param x Координата X
     * @see Coordinates#getX()
     */
    public void setX(Long x) {this.x = x;}
    /**
     * Геттер поля координаты Y класса Координаты {@link Coordinates#y}
     * @return Возвращает координату Y
     * @see Coordinates#setY(Float)
     */
    public Float getY() {return y;}
    /**
     * Сеттер поля координаты Y класса Координаты {@link Coordinates#y}
     * @param y Координата Y
     * @see Coordinates#getY()
     */
    public void setY(Float y) {this.y = y;}
}