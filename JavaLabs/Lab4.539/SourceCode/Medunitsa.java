public class Medunitsa extends Person {
    public Medunitsa(String name, String gender) {
        super(name, gender);
    }

    public void giveClothes(){
        System.out.println(this.toString()+" велела выдать из кладовой одежду Тюбику и Гусле.");
    }
}