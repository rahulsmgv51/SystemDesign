package SOLID_Principle.solid_principles_java_code.ocp;

public class OCPExample {

    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();

        Payment creditCardPayment = new CreditCardPayment();
        processor.process(creditCardPayment, 100.0);

        Payment paypalPayment = new PaypalPayment();
        processor.process(paypalPayment, 200.0);

        Payment upiPayment = new UPIPayment();
        processor.process(upiPayment, 150.0);
    }
}