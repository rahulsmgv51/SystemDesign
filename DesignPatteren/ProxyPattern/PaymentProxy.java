package DesignPatteren.ProxyPattern;

//Proxy
public class PaymentProxy implements PaymentService {

    private RealPaymentService paymentService;

    private String role;

    public PaymentProxy(String role) {
        this.role = role;
    }

    @Override
    public void processPayment(double amount) {

        if (!"ADMIN".equals(role)) {
            System.out.println("Access Denied");
            return;
        }

        if (paymentService == null) {
            paymentService = new RealPaymentService();
        }

        paymentService.processPayment(amount);
    }
}