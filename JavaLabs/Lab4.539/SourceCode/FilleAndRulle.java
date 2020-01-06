public class FilleAndRulle extends Person{
    FilleAndRulle(String name, String gender) {
        super(name, gender);
    }
    public static void wheeze(){
        System.out.println("А потом Малыш услышал как Филле и Рулле захрипели, словно им не хватало воздуха,");
    }
    static class Mummy{
        public static void rest(){
            System.out.println("которая покоилась на подушке.");
        }
        static class Grin{
            class Teeth{}
        }
    }
    FilleAndRulle.Mummy.Grin g = new FilleAndRulle.Mummy.Grin();
    public void see(){
        System.out.println("А потом Малыш услышал как Филле и Рулле захрипели, словно им не хватало воздуха, и он понял, что они увидели наводящий ужас оскал мумии,");
        Mummy.rest();
    }
    public void notRunAway() {
        System.out.println("Однако они не вскрикнули и не бросились наутек, а только дышали как-то странно.");
    }
}