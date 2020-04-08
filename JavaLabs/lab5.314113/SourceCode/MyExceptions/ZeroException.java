package MyExceptions;

public class ZeroException extends Exception {
    public ZeroException (){
        super("Поле не может принимать неположительные значения.");
    }
}
