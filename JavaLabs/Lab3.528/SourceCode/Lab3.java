import java.util.Arrays;
public class Lab3 {
    public static void main(String[] args) {
        GalochkaAndKoobyshka GnK = new GalochkaAndKoobyshka("Галочка и Кубышка","Plural");
        if (Arrays.asList(args).contains("eat")){
            GnK.eat();
        } else {
            if (Arrays.asList(args).contains("die")){
                GnK.die();
            } else {
                theList Lst = new theList();
                Lst.beMade();
                Medunitsa mdn = new Medunitsa("Медуница","Female");
                mdn.giveClothes();
                TyubikAndGooslya TnG = new TyubikAndGooslya("Тюбик и Гусля","Plural");
                TnG.showUp();
                News news = new News();
                news.spread();
                Neznaika nzn = new Neznaika("Незнайка","Male");
                nzn.getHospitalised();
                GnK.runAndTell();
                Crowd crd = new Crowd();
                crd.goToHospital();
                crd.goHelp();
                crd.bringEverything();
            }
        }
    }
}