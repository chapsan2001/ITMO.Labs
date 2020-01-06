import ru.ifmo.se.pokemon.*;

public class Mienfoo extends Pokemon {
    public Mienfoo(String name, int level) {
        super(name, level);
        setStats(45,85,50,55,50,65);
        setType(Type.FIGHTING);
        setMove(new Swagger(), new Facade(), new DoubleTeam());
    }
}
