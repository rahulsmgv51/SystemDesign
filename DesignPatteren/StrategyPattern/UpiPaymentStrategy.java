package DesignPatteren.StrategyPattern;

public class UpiPaymentStrategy implements PaymentStrategy {

    private String upiId;

    public UpiPaymentStrategy(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using UPI : " + upiId);
    }
}