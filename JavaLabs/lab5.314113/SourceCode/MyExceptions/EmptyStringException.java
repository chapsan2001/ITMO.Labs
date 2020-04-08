package MyExceptions;

public class EmptyStringException extends Exception{
    public EmptyStringException(){
        super("Строка не может быть пустой.");
    }
}
