public class Blanket{
    Floor flr = new Floor() {
        public void hang() {
            System.out.println("Правда, покрывало свисало до самого пола скрывая его и Карлсона от света фонарика и от любопытных глаз.");
        }
    };
    public void makeSomeNoise(){
        System.out.println("Зашуршало покрывало.");
    }
}