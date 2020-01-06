import java.util.Arrays;
public class Crowd implements help{
    public void goToHospital(){
        System.out.println("и скоро все население города двинулось, как по команде, к больнице.");
    }
    public enum everything{
        пироги,
        варенье,
        пастила,
        компот
    }
    public void goHelp(){
        System.out.println("Каждой малышке хотелось чем-нибудь помочь пострадавшим малышам.");
    }
    public void bringEverything(){
        System.out.print("Они тащили с собой всякую всячину: ");
        System.out.println(Arrays.toString(everything.values()));
    }
}
