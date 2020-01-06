public abstract class Person extends Crowd {
    String name;
    String gender;
    Person(String name, String gender){
        this.name=name;
        this.gender=gender;
    }
    public String say (String s){return(s);}
    @Override
    public String toString(){
        return this.name;
    }
    public boolean equals(int a, int b){
        return a==b;
    }
    @Override
    public int hashCode(){
        return 0;
    }
}