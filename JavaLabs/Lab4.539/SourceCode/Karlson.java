public class Karlson extends Person{
    Karlson(String name, String gender) {
        super(name, gender);
    }
    static class snoring {
        static boolean loudly = true;
        static boolean angrily = true;
    }
        public void toSnore() throws KarlsonDoesntSnoreLoudlyAndAngrily {
        if (snoring.loudly && snoring.angrily) {
            System.out.println("Карлсон храпел громко и зловеще.");
        } else {
            throw new KarlsonDoesntSnoreLoudlyAndAngrily("Карлсон должен храпеть громко и зловеще!");
        }
    }
}
