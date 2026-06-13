package DesignPatteren.ProxyPattern;

//Client
public class ProxyPatternDemo {

    public static void main(String[] args) {

        PaymentService service = new PaymentProxy("ADMIN");

        service.processPayment(5000);
    }
}