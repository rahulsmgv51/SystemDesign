# 🧩 Open/Closed Principle (OCP) in Microservice

---

## 📘 Overview

The **Open/Closed Principle (OCP)** is the **second** of the **SOLID** design principles.

> **Software entities (classes, modules, functions) should be open for extension but closed for modification.**

- `Open for extension`: This means that the behavior of a software module (such as a class or function) can be extended without altering its source code.

- `Closed for modification`: Once a module is written and tested, it should not be modified. Instead, new behavior should be added in a way that doesn't impact existing code.

- `A class should be open for extension, but closed for modification`. This means you should be able to change the behavior of a class without changing its source code. You do this through polymorphism and interfaces (or abstract classes).

In simple words:  
You should be able to **add new functionality** without **changing existing code**.

This makes your system more **flexible**, **stable**, and **resistant to bugs** caused by modifying old code.

---

## ❌ Without OCP (Bad Design)

Imagine you have a Quarkus-based **PaymentService** handling multiple payment methods:

```java
@ApplicationScoped
public class PaymentService {

    public void processPayment(String type) {
        if (type.equals("CREDIT_CARD")) {
            System.out.println("Processing credit card payment...");
        } else if (type.equals("UPI")) {
            System.out.println("Processing UPI payment...");
        } else if (type.equals("NETBANKING")) {
            System.out.println("Processing net banking payment...");
        } else {
            throw new RuntimeException("Unsupported payment type!");
        }
    }
}
### ⚠️ Problems

- Every time a new payment method is added (e.g., "PayPal"), we must modify this class.
- The class becomes tightly coupled to all payment types.
- The code is not scalable — violates OCP.

### ✅ With OCP (Good Design)

We make the system extensible by using abstraction (interfaces) and polymorphism.

#### 1️⃣ Create a Common Interface
public interface PaymentProcessor {
    void processPayment();
}
2️⃣ Implement Separate Classes for Each Payment Type
java
@ApplicationScoped
public class CreditCardPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment() {
        System.out.println("Processing credit card payment...");
    }
}
java
@ApplicationScoped
public class UpiPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment() {
        System.out.println("Processing UPI payment...");
    }
}
java
@ApplicationScoped
public class NetBankingPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment() {
        System.out.println("Processing net banking payment...");
    }
}
3️⃣ Payment Factory or Strategy Selector
java
@ApplicationScoped
public class PaymentFactory {

    @Inject
    Instance<PaymentProcessor> processors;

    public PaymentProcessor getProcessor(String type) {
        return switch (type.toUpperCase()) {
            case "CREDIT_CARD" -> processors.select(CreditCardPaymentProcessor.class).get();
            case "UPI" -> processors.select(UpiPaymentProcessor.class).get();
            case "NETBANKING" -> processors.select(NetBankingPaymentProcessor.class).get();
            default -> throw new RuntimeException("Unsupported payment type!");
        };
    }
}
4️⃣ Main Service (Closed for Modification)
java
@ApplicationScoped
public class PaymentService {

    @Inject
    PaymentFactory factory;

    public void process(String type) {
        PaymentProcessor processor = factory.getProcessor(type);
        processor.processPayment();
    }
}
Now, if a new payment method is added (e.g., PayPal),
✅ we create a new class implementing PaymentProcessor,
🚫 without touching PaymentService or existing processors.

🧱 Architecture Diagram
scss
                    │  (uses factory)      │
                    └─────────┬────────────┘
                              │
                              ▼
                     ┌────────────────┐
                     │ PaymentFactory │
                     └───────┬────────┘
         ┌───────────────────┼───────────────────┐
         ▼                   ▼                   ▼
┌────────────────┐ ┌────────────────┐ ┌────────────────┐
│ CreditCard...  │ │ UpiPayment... │ │ NetBanking...  │
│ (implements)   │ │ (implements)  │ │ (implements)   │
│ PaymentProcessor│ │ PaymentProcessor│ │ PaymentProcessor│
└────────────────┘ └────────────────┘ └────────────────┘
🧠 Why This Follows OCP
Class	Open for Extension	Closed for Modification
PaymentProcessor	New implementations can be added	Interface remains unchanged
PaymentService	New payment methods work automatically	No code change required
PaymentFactory	Can be enhanced with new mapping logic	Doesn’t affect existing flow

🚀 Benefits of OCP in Microservices
✅ Extensibility: Add new features without changing existing code.
✅ Stability: Existing functionality remains untouched.
✅ Reusability: Each processor is self-contained and reusable.
✅ Reduced Risk: Fewer regression bugs.
✅ Better Maintainability: Cleaner, modular code structure.

🧩 Real-World Analogy
Think of a power strip:

You can plug in new devices (extensions).

You don’t need to open or modify the strip itself.

Similarly, your classes should allow extension without modification.

🔗 Relation with Other SOLID Principles
Principle	Description
SRP	Each class has one responsibility
OCP	Open for extension, closed for modification
LSP	Derived classes should be substitutable
ISP	Prefer small, specific interfaces
DIP	Depend on abstractions, not concretions

🏁 Summary
The Open/Closed Principle helps you write code that is scalable, maintainable, and future-proof.

In a Quarkus microservice, applying OCP allows you to:

Add new integrations (like payment gateways, adapters, or notifiers)

Extend logic safely using interfaces, factories, or strategies

Avoid risky modifications to core business classes

💻 Example Folder Structure
src/
└── main/
    │   │   ├── service/
    │   │   │   ├── PaymentService.java
    │   │   │   ├── PaymentFactory.java
    │   │   │   ├── PaymentProcessor.java
    │   │   │   ├── CreditCardPaymentProcessor.java
    │   │   │   ├── UpiPaymentProcessor.java
    │   │   │   └── NetBankingPaymentProcessor.java
    │   └── resources/
    │       └── application.properties

---