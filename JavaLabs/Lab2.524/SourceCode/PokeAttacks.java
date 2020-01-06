import ru.ifmo.se.pokemon.*;

class ThunderWave extends StatusMove{
    protected ThunderWave(){
        super(Type.ELECTRIC, 0, 90);
    }
    protected String describe(){
        return "использует Thunder Wave\nПротивник парализован\nХр-р-р";
    }
    protected void applyOppEffects(Pokemon p){
        Effect e = new Effect().chance(1).turns(1);
        e.paralyze(p);
        p.addEffect(e);
    }
}

class Roost extends StatusMove{
    protected Roost(){
        super(Type.FLYING, 0, 0);
    }
    protected String describe(){
        return "использует Roost\nи восстанавливает половину здоровья";
    }
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.HP, (int) (p.getHP()+p.getStat(Stat.HP)/2));
    }
}

class DragonRush extends PhysicalMove{
    protected DragonRush(){
        super(Type.DRAGON, 100, 75);
    }
    protected String describe(){
        return "использует Dragon Rush\nи заставляет противника отступить";
    }
    protected void applyOppEffects(Pokemon p){
        Effect e = new Effect().chance(0.2).turns(1);
        e.flinch(p);
        p.addEffect(e);
    }
}

class WildCharge extends PhysicalMove{
    protected WildCharge(){
        super(Type.ELECTRIC, 90, 100);
    }
    protected String describe(){
        return "использует Wild Charge";
    }
    protected void applySelfDamage(Pokemon p, double Damage){
        p.setMod(Stat.HP, (int) Math.round(Damage/4));
    }
}

class Swagger extends StatusMove{
    protected Swagger(){
        super(Type.NORMAL, 0, 85);
    }
    protected String describe(){
        return "использует Swagger,\nувеличивает атаку противника на 2\nи вводит его в состояние растерянности";
    }
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.ATTACK, 2);
        Effect e = new Effect().chance(1).turns(1);
        e.confuse(p);
        p.addEffect(e);
    }
}

class Facade extends PhysicalMove{
    protected Facade(){
        super(Type.NORMAL, 70, 100);
    }
    protected String describe(){
        return "использует Facade";
    }
    protected void applyOppDamage(Pokemon p, double damage){
        Status PokCon = p.getCondition();
        if (PokCon.equals(Status.BURN) || PokCon.equals(Status.POISON) || PokCon.equals(Status.PARALYZE)) {
            p.setMod(Stat.HP, (int) Math.round(damage) * 2);
        }
    }
}

class DoubleTeam extends StatusMove{
    protected DoubleTeam(){
        super(Type.NORMAL, 0, 0);
    }
    protected String describe(){
        return "использует Double Team\nи увеличивает свое уклонение на 1";
    }
    protected void applySelfEffects(Pokemon p){
        Effect e = new Effect().chance(1).turns(1).stat(Stat.EVASION, 1);
        p.addEffect(e);
    }
}

class GrassWhistle extends StatusMove{
    protected GrassWhistle(){
        super(Type.GRASS, 0, 55);
    }
    protected String describe(){
        return "использует Grass Whistle\nи усыпляет противника";
    }
    protected void applyOppEffects(Pokemon p){
        Effect e = new Effect().chance(1).turns((int) Math.random() * 3 + 1);
        e.sleep(p);
        p.addEffect(e);
    }
}

class Rest extends StatusMove{
    protected Rest(){
        super(Type.PSYCHIC, 0, 0);
    }
    protected String describe(){
        return "использует Rest,\nполностью излечивается\nи засыпает на 2 хода";
    }
    protected void applyOppEffects(Pokemon p){
        p.restore();
        Effect e = new Effect().chance(1).turns(2);
        e.sleep(p);
        p.addEffect(e);
    }
}

class RockBlast extends PhysicalMove{
    protected RockBlast(){
        super(Type.ROCK, 25, 90);
        int a =(int) Math.random() * 8 + 1;
        switch (a) {
            case 1: hits = 2; break;
            case 2: hits = 2; break;
            case 3: hits = 2; break;
            case 4: hits = 3; break;
            case 5: hits = 3; break;
            case 6: hits = 3; break;
            case 7: hits = 4; break;
            case 8: hits = 5; break;
        }
    }
    protected String describe(){
        return "использует Rock Blast";
    }
}

class Tickle extends StatusMove{
    protected Tickle(){
        super(Type.NORMAL, 0, 100);
    }
    protected String describe(){
        return "использует Tickle\nи снижает атаку и защиту противника на 1";
    }
    protected void applyOppEffects(Pokemon p){
        Effect e = new Effect().chance(1).turns(1).stat(Stat.ATTACK, -1).stat(Stat.DEFENSE, -1);
        p.addEffect(e);
    }
}