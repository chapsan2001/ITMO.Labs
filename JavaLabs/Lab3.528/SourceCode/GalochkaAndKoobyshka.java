public class GalochkaAndKoobyshka extends Person implements EasterEgg{
    GalochkaAndKoobyshka(String name, String gender) {
        super(name, gender);
    }
    public void runAndTell() {
        System.out.println(this.toString()+" без устали бегали из дома в дом и рассказывали новость подругам.");
        System.out.print("Эти подруги, в свою очередь, рассказывали другим подругам, другие - третьим, ");
    }

    public void eat(){
        System.out.println("ГАЛЯ, ЖРАТЬ!!");
    }
    public void die(){
        System.out.println("Алло, Галочка, ты щас умрешь!");
        System.out.println("Omae wa mou shindeiru!");
        System.out.println("Nani?!?!");
        System.out.println("KABOOM!!");
    }
}
