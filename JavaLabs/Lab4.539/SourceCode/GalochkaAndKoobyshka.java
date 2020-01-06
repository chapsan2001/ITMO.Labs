public class GalochkaAndKoobyshka extends Person{
    GalochkaAndKoobyshka(String name, String gender) {
        super(name, gender);
    }
    public void runAndTell() {
        System.out.println(this.toString()+" без устали бегали из дома в дом и рассказывали новость подругам.");
        System.out.print("Эти подруги, в свою очередь, рассказывали другим подругам, другие - третьим, ");
    }
}
