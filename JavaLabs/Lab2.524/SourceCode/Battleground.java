import ru.ifmo.se.pokemon.*;

public class Battleground {
    public static void main(String[] args) {
        Battle field = new Battle();
        field.addAlly(new Tropius("Письмак А. Е.", 4));
        field.addAlly(new Mienfoo("Белозубов А. В.", 2));
        field.addAlly(new Mienshao("Калинин И. В.", 3));
        field.addFoe(new Starly("Гусарова Е. В.", 5));
        field.addFoe(new Staravia("Пшеничнов В. Е.", 3));
        field.addFoe(new Staraptor("Прищепенок О. Б.", 2));
        field.go();
    }
}
