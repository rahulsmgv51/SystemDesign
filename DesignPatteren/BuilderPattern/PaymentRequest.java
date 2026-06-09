package DesignPatteren.BuilderPattern;

public class PaymentRequest {
    private final String txnId;
    private final String accountNumber;
    private final double amount;
    private final String remarks;

    private PaymentRequest(PaymentRequestBuilder builder){
        this.txnId = builder.txnId;
        this.accountNumber = builder.accountNumber;
        this.amount = builder.amount;
        this.remarks = builder.remarks;
    }

    public static class PaymentRequestBuilder{
        private String txnId;
        private String accountNumber;
        private double amount;
        private String remarks;

        public PaymentRequestBuilder txnId(String txnId) {
            this.txnId = txnId;
            return this;
        }
        public PaymentRequestBuilder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }
        public PaymentRequestBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }
        public PaymentRequestBuilder remarks(String remarks) {
            this.remarks = remarks;
            return this;
        }
        public PaymentRequest build(){
            return  new PaymentRequest(this);
        }
    }
}
