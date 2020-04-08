package MyExceptions;

public class NoSuchGenreException extends Exception {
    public NoSuchGenreException(){super("Указанного жанра нет в базе.");}
}
