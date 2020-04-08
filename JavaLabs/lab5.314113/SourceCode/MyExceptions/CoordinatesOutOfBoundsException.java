package MyExceptions;

public class CoordinatesOutOfBoundsException extends Exception{
    public CoordinatesOutOfBoundsException(){super("Одна из координат выходит за допустимые границы.");}
}
