import ru.ifmo.se.pokemon.*;

public class Staraptor extends Staravia {
    public Staraptor(String name, int level) {
        super(name, level);
        setStats(85,120,70,50,60,100);
        setType(Type.NORMAL,Type.FLYING);
        setMove(new Rest(), new ThunderWave(), new RockBlast(), new Tickle());
    }
}