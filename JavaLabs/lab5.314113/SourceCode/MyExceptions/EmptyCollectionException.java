package MyExceptions;

public class EmptyCollectionException extends Exception {
    public EmptyCollectionException(){
        super("Коллекция пуста.");
    }
}
