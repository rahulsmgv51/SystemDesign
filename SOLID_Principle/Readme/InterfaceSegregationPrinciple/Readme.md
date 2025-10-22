# 🧩 Interface Segregation Principle (ISP) in Microservice

---

## 📘 Overview

The **Interface Segregation Principle (ISP)** is the fourth principle of the **SOLID** design principles.

> "Clients should not be forced to depend on interfaces they do not use."

In short: prefer multiple small, specific interfaces to one large, general-purpose interface.

---

## 💡 Real meaning

A class that implements an interface should only be required to implement methods that make logical sense for that class. Large interfaces force classes to implement irrelevant methods, causing poor design and code bloat.

---

## ❌ Without ISP (bad design)

Example of a bloated interface:

```java
public interface BankOperations {
    void deposit(double amount);
    void withdraw(double amount);
    void transfer(double amount);
    void requestLoan(double amount);
    void openFixedDeposit(double amount);
}
```

Now consider two account types:
- SavingsAccount (supports most operations)
- LoanAccount (only supports loan-related operations)

Implementing LoanAccount with the large interface forces unsupported methods:

```java
public class LoanAccount implements BankOperations {
    @Override
    public void deposit(double amount) {
        throw new UnsupportedOperationException("Deposit not allowed in Loan Account");
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdraw not allowed in Loan Account");
    }

    @Override
    public void transfer(double amount) {
        throw new UnsupportedOperationException("Transfer not allowed in Loan Account");
    }

    @Override
    public void requestLoan(double amount) {
        System.out.println("Processing loan request of ₹" + amount);
    }

    @Override
    public void openFixedDeposit(double amount) {
        throw new UnsupportedOperationException("Fixed Deposit not allowed in Loan Account");
    }
}
```

This violates ISP: the class depends on methods it doesn't use.

---

## ✅ With ISP (good design)

Break the large interface into focused interfaces:

```java
public interface DepositService {
    void deposit(double amount);
}

public interface WithdrawService {
    void withdraw(double amount);
}

public interface TransferService {
    void transfer(double amount);
}

public interface LoanService {
    void requestLoan(double amount);
}

public interface FixedDepositService {
    void openFixedDeposit(double amount);
}
```

Implement only what's needed.

SavingsAccount (supports most operations):

```java
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SavingsAccount implements DepositService, WithdrawService, TransferService, FixedDepositService {

    @Override
    public void deposit(double amount) {
        System.out.println("Deposited ₹" + amount + " to Savings Account");
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Withdrawn ₹" + amount + " from Savings Account");
    }

    @Override
    public void transfer(double amount) {
        System.out.println("Transferred ₹" + amount + " from Savings Account");
    }

    @Override
    public void openFixedDeposit(double amount) {
        System.out.println("Opened Fixed Deposit of ₹" + amount);
    }
}
```

LoanAccount (implements only loan behavior):

```java
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoanAccount implements LoanService {

    @Override
    public void requestLoan(double amount) {
        System.out.println("Processing loan request of ₹" + amount);
    }
}
```

Each class now implements only the methods it needs — modular and compliant with ISP.

---

## 🧱 Architecture diagram (conceptual)

      ┌─────────────────────┐
      │  Bank Application   │
      └──────────┬──────────┘
                 │
                 ▼
   ┌───────────────────────────────┐
   │       Service Interfaces      │
   ├───────────────────────────────┤
   │ DepositService                │
   │ WithdrawService               │
   │ TransferService               │
   │ LoanService                   │
   │ FixedDepositService           │
   └────────────────┬──────────────┘
        ┌───────────┴──────────┐
        ▼                      ▼
   ┌──────────────┐       ┌──────────────┐
   │SavingsAccount│       │ LoanAccount  │
   │Implements    │       │Implements    │
   │relevant ones │       │LoanService   │
   └──────────────┘       └──────────────┘

---

## 🧠 Why this follows ISP

| Class         | Interfaces Implemented                                | Result                                    |
|---------------|-------------------------------------------------------|-------------------------------------------|
| SavingsAccount| Deposit, Withdraw, Transfer, FixedDeposit             | ✅ Implements relevant methods only       |
| LoanAccount   | LoanService                                           | ✅ No redundant or unsupported methods    |

---

## 🚀 Benefits in microservices

- Decoupled services: each interface focuses on one role  
- Better maintainability: changes to one interface don't affect others  
- Higher reusability: small interfaces are easy to reuse and extend  
- Reduced bugs: no meaningless or empty implementations  
- Improved testability: each interface can be mocked independently

---

## 🧩 Real-world analogy

A driver’s license doesn't include permissions to fly a plane. Each license is specific — just like interfaces should be.

---

## 🔗 Relation with other SOLID principles

- SRP — Single Responsibility Principle  
- OCP — Open/Closed Principle  
- LSP — Liskov Substitution Principle  
- ISP — Interface Segregation Principle  
- DIP — Dependency Inversion Principle

---

## 🖥️ Example folder structure

```text
src/
└── main/
    ├── java/
    │   └── com/example/bank/
    │       ├── service/
    │       │   ├── DepositService.java
    │       │   ├── WithdrawService.java
    │       │   ├── TransferService.java
    │       │   ├── LoanService.java
    │       │   ├── FixedDepositService.java
    │       │   ├── SavingsAccount.java
    │       │   └── LoanAccount.java
    └── resources/
        └── application.properties
```

---

## 🏁 Summary

ISP keeps interfaces small, specific, and meaningful. In a Quarkus microservice, this enables modular services, independent development and testing, and fewer unnecessary dependencies.