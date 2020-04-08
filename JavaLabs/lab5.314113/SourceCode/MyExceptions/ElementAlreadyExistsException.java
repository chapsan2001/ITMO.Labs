package MyExceptions;

public class ElementAlreadyExistsException extends Exception {
    public ElementAlreadyExistsException(){super("Уже существует элемент с таким ключом.");}
}
