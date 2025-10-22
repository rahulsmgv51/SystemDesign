package SOLID_Principle.solid_principles_java_code.ocp;

public class CreditCardPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Credit Card");
    }
}