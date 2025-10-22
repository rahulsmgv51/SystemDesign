package SOLID_Principle.solid_principles_java_code.ocp;

public class PaymentProcessor {
    public void process(Payment payment, double amount) {
        payment.pay(amount);
    }
}