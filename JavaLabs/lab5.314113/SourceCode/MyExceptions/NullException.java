package MyExceptions;

public class NullException extends Exception {
    public NullException(){
        super("Поле не может принимать значение null.");
    }
}
