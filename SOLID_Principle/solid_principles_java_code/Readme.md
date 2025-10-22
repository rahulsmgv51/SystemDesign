# 🧱 SOLID Principles in Java (Plain Java Examples)

---

## 📘 Overview

The **SOLID principles** are five design guidelines that make your object-oriented programs **more maintainable, scalable, and readable**.  

They stand for:

| Principle | Full Form |
|------------|------------|
| **S** | Single Responsibility Principle |
| **O** | Open/Closed Principle |
| **L** | Liskov Substitution Principle |
| **I** | Interface Segregation Principle |
| **D** | Dependency Inversion Principle |

---

# 🧩 1. Single Responsibility Principle (SRP)

> **A class should have only one reason to change.**

Each class should do only **one job** — one clear responsibility.

---

### ❌ Without SRP

```java
public class EmployeeService {
    public void addEmployee(String name) {
        System.out.println("Adding employee: " + name);
    }

    public void generateReport(String name) {
        System.out.println("Generating report for: " + name);
    }

    public void sendEmail(String name) {
        System.out.println("Sending email to: " + name);
    }
}
👎 EmployeeService is doing three things — adding employees, generating reports, and sending emails.

✅ With SRP
java

public class EmployeeRepository {
    public void addEmployee(String name) {
        System.out.println("Adding employee: " + name);
    }
}
java

public class ReportService {
    public void generateReport(String name) {
        System.out.println("Generating report for: " + name);
    }
}
java

public class EmailService {
    public void sendEmail(String name) {
        System.out.println("Sending email to: " + name);
    }
}
java

public class EmployeeManager {
    private EmployeeRepository repository = new EmployeeRepository();
    private ReportService reportService = new ReportService();
    private EmailService emailService = new EmailService();

    public void onboardEmployee(String name) {
        repository.addEmployee(name);
        reportService.generateReport(name);
        emailService.sendEmail(name);
    }
}
✅ Each class has one reason to change, improving maintainability.

🧱 2. Open/Closed Principle (OCP)
Classes should be open for extension but closed for modification.

We should be able to add new behavior without modifying existing code.

❌ Without OCP
java

public class PaymentProcessor {
    public void process(String type, double amount) {
        if (type.equals("CREDIT")) {
            System.out.println("Paid ₹" + amount + " using Credit Card");
        } else if (type.equals("PAYPAL")) {
            System.out.println("Paid ₹" + amount + " using PayPal");
        }
    }
}
👎 Every time a new payment method is added, this class must be modified.

✅ With OCP
java

public interface Payment {
    void pay(double amount);
}
java

public class CreditCardPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Credit Card");
    }
}
java

public class PaypalPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using PayPal");
    }
}
java

public class PaymentProcessor {
    public void process(Payment payment, double amount) {
        payment.pay(amount);
    }
}
✅ You can now add new payment types (like UPIPayment) without touching PaymentProcessor.

🧬 3. Liskov Substitution Principle (LSP)
Subclasses should be replaceable by their base classes without breaking the program.

If B is a subclass of A, then we should be able to use B wherever A is expected.

❌ Without LSP
java

public class Bird {
    public void fly() {
        System.out.println("Bird is flying");
    }
}

public class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostrich can't fly!");
    }
}
👎 Violates LSP — Ostrich breaks the expected behavior of Bird.

✅ With LSP
java

public interface Bird {
    void eat();
}
java

public interface Flyable {
    void fly();
}
java

public class Sparrow implements Bird, Flyable {
    public void eat() { System.out.println("Sparrow eating"); }
    public void fly() { System.out.println("Sparrow flying"); }
}
java

public class Ostrich implements Bird {
    public void eat() { System.out.println("Ostrich eating"); }
}
✅ Now all subclasses can be safely substituted for their parent interfaces.

⚙️ 4. Interface Segregation Principle (ISP)
Clients should not be forced to depend on methods they do not use.

Large interfaces should be split into smaller, more specific ones.

❌ Without ISP
java

public interface Worker {
    void work();
    void eat();
}
java

public class Robot implements Worker {
    public void work() { System.out.println("Robot working"); }
    public void eat() { throw new UnsupportedOperationException("Robot can't eat"); }
}
👎 Robots are forced to implement an irrelevant eat() method.

✅ With ISP
java

public interface Workable {
    void work();
}

public interface Eatable {
    void eat();
}
java

public class Human implements Workable, Eatable {
    public void work() { System.out.println("Human working"); }
    public void eat() { System.out.println("Human eating"); }
}
java

public class Robot implements Workable {
    public void work() { System.out.println("Robot working"); }
}
✅ Classes now implement only the methods they need.

🏗️ 5. Dependency Inversion Principle (DIP)
High-level modules should not depend on low-level modules. Both should depend on abstractions.

❌ Without DIP
java

public class EmailService {
    public void sendEmail(String message) {
        System.out.println("Sending Email: " + message);
    }
}

public class NotificationService {
    private EmailService emailService = new EmailService();

    public void notifyUser(String message) {
        emailService.sendEmail(message);
    }
}
👎 NotificationService is tightly coupled to EmailService. Hard to replace or test.

✅ With DIP
java

public interface MessageService {
    void sendMessage(String message);
}
java

public class EmailService implements MessageService {
    public void sendMessage(String message) {
        System.out.println("Sending Email: " + message);
    }
}
java

public class SMSService implements MessageService {
    public void sendMessage(String message) {
        System.out.println("Sending SMS: " + message);
    }
}
java

public class NotificationService {
    private MessageService messageService;

    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void notifyUser(String message) {
        messageService.sendMessage(message);
    }
}
✅ Usage
java

public class Main {
    public static void main(String[] args) {
        MessageService email = new EmailService();
        NotificationService notification = new NotificationService(email);
        notification.notifyUser("Hello User!");

        MessageService sms = new SMSService();
        NotificationService smsNotification = new NotificationService(sms);
        smsNotification.notifyUser("Hello via SMS!");
    }
}
✅ High-level class depends only on the interface, not the implementation.

🧠 Summary Table
Principle	Definition	Benefit
SRP	One class = one responsibility	Easier maintenance
OCP	Open for extension, closed for modification	Extensible code
LSP	Subtypes must behave like base types	Reliable inheritance
ISP	Clients shouldn’t depend on unused methods	Focused interfaces
DIP	Depend on abstractions, not concretions	Loose coupling

🧩 Folder Structure
css

solid-principles-java/
├── srp/
│   ├── EmployeeRepository.java
│   ├── ReportService.java
│   ├── EmailService.java
│   └── EmployeeManager.java
├── ocp/
│   ├── Payment.java
│   ├── CreditCardPayment.java
│   ├── PaypalPayment.java
│   └── PaymentProcessor.java
├── lsp/
│   ├── Bird.java
│   ├── Flyable.java
│   ├── Sparrow.java
│   └── Ostrich.java
├── isp/
│   ├── Workable.java
│   ├── Eatable.java
│   ├── Human.java
│   └── Robot.java
├── dip/
│   ├── MessageService.java
│   ├── EmailService.java
│   ├── SMSService.java
│   ├── NotificationService.java
│   └── Main.java
└── README.md
🏁 Final Thoughts
Applying SOLID makes your Java code:

✅ More modular
✅ Easier to maintain
✅ Extensible without fear of breaking things
✅ Testable and reusable

Remember:

“Code that follows SOLID is code that can grow without chaos.”
---