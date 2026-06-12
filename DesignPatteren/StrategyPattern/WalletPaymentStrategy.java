package DesignPatteren.StrategyPattern;
public class WalletPaymentStrategy implements PaymentStrategy {

    private String walletId;

    public WalletPaymentStrategy(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Wallet : " + walletId);
    }
}