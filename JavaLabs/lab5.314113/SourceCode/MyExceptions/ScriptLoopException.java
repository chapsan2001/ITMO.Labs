package MyExceptions;

public class ScriptLoopException extends Exception {
    public ScriptLoopException() {super("При выполнении скрипта произошло зацикливание. Скрипт остановлен.");}
}
