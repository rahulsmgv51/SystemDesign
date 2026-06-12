package DesignPatteren.DecoratorPattern;

public class CoffeeShop {

    public static void main(String[] args) {
        System.out.println("Welcome to CoffeeShop");
        Coffee coffee = new SimpleCoffee();

        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        coffee = new CreamDecorator(coffee);

        System.out.println(coffee.getDescription());
        System.out.println("Total Cost: ₹" + coffee.getCost());

        System.out.println("------------------------------");
        Coffee coffee1 = new SimpleCoffee();
        coffee1 = new MilkDecorator(coffee1);
        System.out.println(coffee1.getDescription());
        System.out.println("Total Cost: ₹"+ coffee1.getCost());

        System.out.println("------------------------------");
        Coffee coffee2 = new SimpleCoffee();
        coffee2 = new SimpleCoffee();
        System.out.println(coffee2.getDescription());
        System.out.println("Total Cost: ₹"+ coffee2.getCost());

        System.out.println("------------------------------");
        Coffee coffee3 = new SimpleCoffee();
        coffee3 = new CreamDecorator(coffee3);
        System.out.println(coffee3.getDescription());
        System.out.println("Total Cost: ₹"+ coffee3.getCost());
    }
}