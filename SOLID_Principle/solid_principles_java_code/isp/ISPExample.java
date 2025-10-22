package SOLID_Principle.solid_principles_java_code.isp;

public class ISPExample {
    public static void main(String[] args) {
        Workable human = new Human();
        Workable robot = new Robot();

        human.work();
        robot.work();

        Eatable eater = new Human();
        eater.eat();
    }
}
