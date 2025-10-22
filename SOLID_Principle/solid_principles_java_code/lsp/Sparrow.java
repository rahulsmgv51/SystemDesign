package SOLID_Principle.solid_principles_java_code.lsp;

public class Sparrow implements Bird, Flyable {
    public void eat() {
        System.out.println("Sparrow eating");
    }

    public void fly() {
        System.out.println("Sparrow flying");
    }
}