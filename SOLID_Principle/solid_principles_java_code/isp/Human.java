package SOLID_Principle.solid_principles_java_code.isp;

public class Human implements Workable, Eatable {
    public void work() { System.out.println("Human working"); }
    public void eat() { System.out.println("Human eating"); }
}