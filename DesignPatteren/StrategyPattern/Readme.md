# Strategy Pattern — Payment Strategy (UPI, Card, Wallet)

## Overview

This project demonstrates the **Strategy Design Pattern** using multiple payment methods such as:

* UPI
* Credit/Debit Card
* Wallet

The Strategy Pattern enables selecting a payment algorithm at runtime without modifying existing code. Each payment method is encapsulated in its own strategy class while adhering to a common contract.

---

## Problem Statement

In modern payment systems, users can choose from various payment methods:

* UPI
* Credit/Debit Card
* Wallet
* Net Banking
* AEPS
* IMPS

A traditional implementation often relies on multiple `if-else` or `switch` statements:

```java
if(paymentType.equals("UPI")) {
    // Process UPI Payment
} else if(paymentType.equals("CARD")) {
    // Process Card Payment
} else if(paymentType.equals("WALLET")) {
    // Process Wallet Payment
}
```

### Challenges

* Difficult to maintain
* Violates the Open/Closed Principle
* Requires modification whenever a new payment method is introduced
* Business logic becomes tightly coupled

The Strategy Pattern addresses these issues by encapsulating each payment method into a separate strategy implementation.

---

## Design Pattern Type

**Behavioral Design Pattern**

The Strategy Pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable at runtime.

---

## Project Structure

```text
src
│
├── strategy
│   ├── PaymentStrategy.java
│   ├── UpiPaymentStrategy.java
│   ├── CardPaymentStrategy.java
│   └── WalletPaymentStrategy.java
│
├── context
│   └── PaymentContext.java
│
└── client
    └── StrategyPatternDemo.java
```

---

## Class Diagram

```text
                    +------------------+
                    | PaymentStrategy  |
                    +------------------+
                    | pay(amount)      |
                    +------------------+
                             ^
                             |
        -----------------------------------------
        |                   |                   |
        |                   |                   |
+---------------+ +---------------+ +---------------+
| UPI Payment   | | Card Payment  | | WalletPayment |
+---------------+ +---------------+ +---------------+
| pay()         | | pay()         | | pay()         |
+---------------+ +---------------+ +---------------+

                             ^
                             |
                    +------------------+
                    | PaymentContext   |
                    +------------------+
                    | strategy         |
                    | makePayment()    |
                    +------------------+
```

---

# Components

## 1. PaymentStrategy

Defines the common contract for all payment methods.

```java
public interface PaymentStrategy {
    void pay(double amount);
}
```

### Responsibility

* Provides a common payment interface
* Promotes loose coupling
* Enables runtime strategy selection

---

## 2. UpiPaymentStrategy

Handles UPI-based transactions.

```java
public class UpiPaymentStrategy implements PaymentStrategy {

    private String upiId;

    public UpiPaymentStrategy(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public void pay(double amount) {
        System.out.println(
            "Paid ₹" + amount + " using UPI");
    }
}
```

### Responsibility

* Processes UPI payments
* Encapsulates UPI-specific business rules

---

## 3. CardPaymentStrategy

Handles card transactions.

```java
public class CardPaymentStrategy implements PaymentStrategy {

    private String cardNumber;

    public CardPaymentStrategy(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println(
            "Paid ₹" + amount + " using Card");
    }
}
```

### Responsibility

* Processes debit and credit card payments
* Encapsulates card validation logic

---

## 4. WalletPaymentStrategy

Handles wallet-based payments.

```java
public class WalletPaymentStrategy implements PaymentStrategy {

    private String walletId;

    public WalletPaymentStrategy(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public void pay(double amount) {
        System.out.println(
            "Paid ₹" + amount + " using Wallet");
    }
}
```

### Responsibility

* Processes wallet transactions
* Encapsulates wallet-specific logic

---

## 5. PaymentContext

Acts as the orchestrator that delegates execution to the selected strategy.

```java
public class PaymentContext {

    private PaymentStrategy paymentStrategy;

    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void makePayment(double amount) {
        paymentStrategy.pay(amount);
    }
}
```

### Responsibility

* Holds the selected strategy
* Delegates payment execution
* Decouples client code from concrete implementations

---

## Execution Flow

```text
User Selects Payment Method
            |
            v
Create Corresponding Strategy
            |
            v
Inject Strategy into Context
            |
            v
Context Delegates Payment
            |
            v
Selected Strategy Executes
```

---

## Example Usage

```java
public class StrategyPatternDemo {

    public static void main(String[] args) {

        PaymentContext upiContext =
            new PaymentContext(
                new UpiPaymentStrategy("rahul@upi"));

        upiContext.makePayment(5000);

        PaymentContext cardContext =
            new PaymentContext(
                new CardPaymentStrategy("1234-5678"));

        cardContext.makePayment(10000);

        PaymentContext walletContext =
            new PaymentContext(
                new WalletPaymentStrategy("PAYTM123"));

        walletContext.makePayment(2000);
    }
}
```

---

## Sample Output

```text
Paid ₹5000 using UPI
Paid ₹10000 using Card
Paid ₹2000 using Wallet
```

---

## Runtime Strategy Switching

One of the key advantages of the Strategy Pattern is dynamic behavior selection.

```java
PaymentContext context = new PaymentContext();

context.setStrategy(
    new UpiPaymentStrategy("rahul@upi"));

context.makePayment(1000);

context.setStrategy(
    new WalletPaymentStrategy("wallet123"));

context.makePayment(500);
```

---

## Benefits

### Open/Closed Principle

New payment methods can be introduced without changing existing code.

### Loose Coupling

Clients depend on abstractions rather than implementations.

### Easy Testing

Each strategy can be tested independently.

### Better Maintainability

Payment logic remains isolated and organized.

### Runtime Flexibility

Strategies can be switched dynamically.

---

## Extending the System

Adding a new payment method requires only a new strategy implementation.

### Net Banking Strategy

```java
public class NetBankingStrategy
        implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println(
            "Paid ₹" + amount +
            " using Net Banking");
    }
}
```

No changes are required in:

* PaymentContext
* Existing Strategies
* Client Code

---

## Banking & FinTech Use Cases

### Fund Transfer Strategy

```text
TransferStrategy
    |
    +-- IMPS
    +-- NEFT
    +-- RTGS
    +-- UPI
```

### Authentication Strategy

```text
AuthenticationStrategy
    |
    +-- OTP
    +-- MPIN
    +-- Biometric
```

### Encryption Strategy

```text
EncryptionStrategy
    |
    +-- AES
    +-- RSA
    +-- SHA-256
```

### Routing Strategy

```text
RoutingStrategy
    |
    +-- UPI 
    +-- AEPS 
    +-- IMPS 
    +-- BBPS 
```

---

## When to Use Strategy Pattern

Use Strategy Pattern when:

* Multiple algorithms perform the same task differently
* Runtime selection of behavior is required
* Large `if-else` or `switch` statements need to be eliminated
* New business rules are frequently added
* SOLID principles need to be enforced

---

## SOLID Principles Applied

| Principle                       | Implementation                                              |
| ------------------------------- | ----------------------------------------------------------- |
| Single Responsibility Principle | Each strategy has a single responsibility                   |
| Open/Closed Principle           | New strategies can be added without modifying existing code |
| Liskov Substitution Principle   | Any strategy can replace another                            |
| Interface Segregation Principle | Small and focused interface                                 |
| Dependency Inversion Principle  | Context depends on abstractions                             |

---

## Conclusion

The Strategy Pattern is one of the most commonly used behavioral design patterns in payment gateways, banking platforms, and microservice architectures.

It promotes:

* Extensibility
* Maintainability
* Loose Coupling
* Runtime Flexibility
* SOLID Design Principles

By encapsulating payment methods into interchangeable strategies, systems remain scalable and easier to evolve as new payment channels are introduced.
