public class Lab4 {
    public static boolean snoringSuccess = true;
    public static void main(String[] args) {
        class oldLab {
            void oldLab3() {
                GalochkaAndKoobyshka GnK = new GalochkaAndKoobyshka("Галочка и Кубышка", "Plural");
                theList Lst = new theList();
                Lst.beMade();
                Medunitsa mdn = new Medunitsa("Медуница", "Female");
                mdn.giveClothes();
                TyubikAndGooslya TnG = new TyubikAndGooslya("Тюбик и Гусля", "Plural");
                TnG.showUp();
                News news = new News();
                news.spread();
                Neznaika nzn = new Neznaika("Незнайка", "Male");
                nzn.getHospitalised();
                GnK.runAndTell();
                Crowd crd = new Crowd();
                crd.goToHospital();
                crd.goHelp();
                crd.bringEverything();
            }
        }
        System.out.println();
        Malysh m = new Malysh("Малыш","Male");
        if (m.gender != "Male") {throw new MalyshIsATrap("IT'S A TRAP!");}
        Karlson k = new Karlson("Карлсон","Male");
        FilleAndRulle FnR = new FilleAndRulle("Филле и Рулле","Plural");
        Blanket b = new Blanket();
        try {k.toSnore();} catch (KarlsonDoesntSnoreLoudlyAndAngrily msg) {
            System.out.println(msg.getMessage());
            System.out.println("Храпит зловеще? "+Karlson.snoring.angrily+"\r\n Храпит громко? "+Karlson.snoring.loudly);
        }
        if (Lab4.snoringSuccess) {
            m.scare();
            b.flr.hang();
            m.decide();
            b.makeSomeNoise();
            FnR.see();
            FnR.notRunAway();
        } else {
            return;
        }
    }
}