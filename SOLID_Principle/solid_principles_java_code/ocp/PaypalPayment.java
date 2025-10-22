package SOLID_Principle.solid_principles_java_code.ocp;

public class PaypalPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Paid Rs. " + amount + " using PayPal");
    }
}
