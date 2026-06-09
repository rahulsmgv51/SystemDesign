package DesignPatteren.BuilderPattern;

public class BuilderPatternDemo {

    public static void main(String[] args) {
        System.out.println("Builder Desgin Pattern Demo");
        Person person = new Person.PersonBuilder()
                .firstName("Rahul")
                .lastName("Vishwakarma")
                .age(28)
                .email("rahul@gmail.com")
                .phoneNumber("9876543210")
                .address("Mumbai")
                .build();

        System.out.println(person);

        System.out.println("--------------------------------");

        PaymentRequest request = new PaymentRequest.PaymentRequestBuilder()
                .txnId("TXN123456")
                .accountNumber("1234567890")
                .amount(1000)
                .remarks("UPI Transfer")
                .build();
        System.out.println(request);
    }
}