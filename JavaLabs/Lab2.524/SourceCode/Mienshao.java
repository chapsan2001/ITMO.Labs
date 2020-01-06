import ru.ifmo.se.pokemon.*;

public class Mienshao extends Mienfoo {
    public Mienshao(String name, int level) {
        super(name, level);
        setStats(65,125,60,95,60,105);
        setType(Type.FIGHTING);
        setMove(new Swagger(), new Facade(), new DoubleTeam(), new GrassWhistle());
    }
}
