public class Malysh extends Person {
    String thought = "Зря он так. Они нас обнаружат.";
    String decision = "И все же он это здорово придумал";
    Malysh(String name, String gender) {
        super(name, gender);
    }
    public void scare(){
        System.out.println("Малыш пришел от этого в ужас и подумал:"+thought);
    }
    public void decide(){
        System.out.println(decision+", - решил Малыш.");
    }
}
