package SOLID_Principle.solid_principles_java_code.lsp;

public class LSPExample {
    public static void main(String[] args) {
        Bird bird1 = new Sparrow();
        Bird bird2 = new Ostrich();

        bird1.eat();
        bird2.eat();

        Flyable flyableBird = new Sparrow();
        flyableBird.fly();
    }
}