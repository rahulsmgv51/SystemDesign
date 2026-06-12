package DesignPatteren.StrategyPattern;

public class StrategyPatternDemo {

    public static void main(String[] args) {

        PaymentContext upiContext = new PaymentContext(new UpiPaymentStrategy("mehul@okicici"));

        upiContext.makePayment(5000);

        PaymentContext cardContext = new PaymentContext(new CardPaymentStrategy("1234-5678-9012-3456"));

        cardContext.makePayment(10000);

        PaymentContext walletContext = new PaymentContext(new WalletPaymentStrategy("PAYTM123"));

        walletContext.makePayment(2000);
    }
}