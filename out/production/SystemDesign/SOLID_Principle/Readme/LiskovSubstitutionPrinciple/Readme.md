# 🧩 Liskov Substitution Principle (LSP) in Microservice

---

## 📘 Overview

The **Liskov Substitution Principle (LSP)** is the **third** of the **SOLID** design principles, introduced by **Barbara Liskov** in 1987.

> **"Objects of a superclass should be replaceable with objects of its subclass without breaking the application."**

In simple terms:
If class **B** is a subclass of class **A**, then you should be able to **use B wherever A is expected** — without changing the program’s behavior.

---

## 💡 Real Meaning

Subclasses must **honor the behavior** of the parent class, not just inherit its methods.

So:
- No overriding that breaks expected behavior.
- No narrowing of input conditions.
- No widening of output expectations.

This keeps your **inheritance hierarchy reliable and predictable**.

---

## ❌ Without LSP (Bad Design)

Consider a **PaymentProcessor** base class and a **RefundProcessor** subclass that violates expected behavior:

```java
public class PaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Processing payment of ₹" + amount);
    }
}

public class RefundProcessor extends PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        // 🚨 Violates LSP: Refunds shouldn't call this method directly
        throw new UnsupportedOperationException("Refund not supported here!");
    }
}
⚠️ Problem
The subclass RefundProcessor breaks the behavior expected from PaymentProcessor.

Anywhere the code expects a PaymentProcessor, substituting a RefundProcessor will cause runtime errors.

This violates LSP because the subclass is not a true substitute for the parent.

✅ With LSP (Good Design)
We can fix this by creating a proper abstraction and ensuring consistent behavior across all implementations.

1️⃣ Define a Clear Interface

public interface PaymentOperation {
    void execute(double amount);
}
2️⃣ Implement Subclasses that Obey the Same Contract

@ApplicationScoped
public class PaymentProcessor implements PaymentOperation {
    @Override
    public void execute(double amount) {
        System.out.println("Processing payment of ₹" + amount);
    }
}

@ApplicationScoped
public class RefundProcessor implements PaymentOperation {
    @Override
    public void execute(double amount) {
        System.out.println("Processing refund of ₹" + amount);
    }
}
Now both classes fulfill the same contract (execute) — no unexpected behavior.

3️⃣ Use in Quarkus Service Layer

@ApplicationScoped
public class TransactionService {

    @Inject
    Instance<PaymentOperation> operations;

    public void handleTransaction(PaymentOperation operation, double amount) {
        operation.execute(amount);
    }
}
4️⃣ Example Usage

@Inject
TransactionService transactionService;

public void demo() {
    PaymentOperation payment = new PaymentProcessor();
    PaymentOperation refund = new RefundProcessor();

    transactionService.handleTransaction(payment, 5000);
    transactionService.handleTransaction(refund, 2000);
}
✅ Both PaymentProcessor and RefundProcessor can be used interchangeably.
No errors, no surprises — this respects LSP.

🧱 Architecture Diagram
                        ┌────────────────────────┐
                        │   TransactionService   │
                        │ uses PaymentOperation  │
                        └────────────┬───────────┘
                                     │
                                     ▼
                     ┌─────────────────────────────────┐
                     │          PaymentOperation       │
                     │        (Interface Contract)     │
                     └────────────────┬────────────────┘
                        ┌─────────────┴────────────┐
                        ▼                          ▼
            ┌─────────────────────┐     ┌──────────────────────┐
            │  PaymentProcessor   │     │   RefundProcessor    │
            │  (implements)       │     │   (implements)       │
            └─────────────────────┘     └──────────────────────┘
🧠 Why This Follows LSP
Class	Expected Behavior	Result
PaymentProcessor	Executes payment	✅ Works as expected
RefundProcessor	Executes refund	✅ Works as expected
TransactionService	Treats both the same (substitution works)	✅ No runtime issue

Both subclasses respect the same behavioral contract — the system doesn’t care which one it uses.

🚀 Benefits of LSP in Microservices
✅ Consistent Behavior: All subclasses behave predictably.
✅ Interchangeability: Plug in new implementations without breaking code.
✅ Extensibility: Add new operations easily (e.g., ReversalProcessor, ReconciliationProcessor).
✅ Cleaner Abstractions: Interfaces remain reliable and reusable.
✅ Reduced Bugs: Prevents misuse of inheritance and broken overrides.

🧩 Real-World Analogy
Imagine:

🏦 DebitCard and 💳 CreditCard both extend Card.

Wherever a Card is used (like a payment machine), either should work without error.

If CreditCard suddenly throws “Unsupported Operation” when swiped — it breaks LSP.

🔗 Relation with Other SOLID Principles
Principle	Description
SRP	Each class has one responsibility
OCP	Classes are open for extension, closed for modification
LSP	Subclasses can replace their base types without issues
ISP	Smaller, focused interfaces prevent misuse
DIP	Depend on abstractions to respect substitution and flexibility

🏁 Summary
The Liskov Substitution Principle ensures inheritance works safely and behavior remains consistent across subclasses.

In a Quarkus microservice, applying LSP means:

Designing interfaces carefully.

Ensuring implementations behave consistently.

Avoiding misuse of inheritance for unrelated or restricted behavior.

💻 Example Folder Structure
src/
└── main/
    ├── java/
    │   ├── com/example/transaction/
    │   │   ├── service/
    │   │   │   ├── TransactionService.java
    │   │   │   ├── PaymentOperation.java
    │   │   │   ├── PaymentProcessor.java
    │   │   │   └── RefundProcessor.java
    │   └── resources/
    │       └── application.properties
---