public class KarlsonDoesntSnoreLoudlyAndAngrily extends Exception{
    public KarlsonDoesntSnoreLoudlyAndAngrily(String message){
        super(message);
        Lab4.snoringSuccess = false;
    }
}