package SOLID_Principle.solid_principles_java_code.ocp;

public class UPIPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Paid Rs. " + amount + " using UPI");
    }
}
