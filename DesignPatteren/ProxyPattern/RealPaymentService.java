package DesignPatteren.ProxyPattern;

//Real Service
public class RealPaymentService implements PaymentService {

    public RealPaymentService() {
        System.out.println( "Initializing Payment Service...");
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Payment Processed: " + amount);
    }
}