# 🧩 Single Responsibility Principle (SRP) in Microservice

---

## 📘 Overview

The **Single Responsibility Principle (SRP)** is the first of the **SOLID** design principles.

> A class should have only one reason to change.

Each class, module, or component should focus on a single responsibility. When a class handles multiple responsibilities, changes in one concern can unintentionally affect others, making the system brittle and hard to maintain.

---

## ❌ Without SRP (Bad Design)

```java
@ApplicationScoped
public class CustomerService {

    @Inject
    EntityManager entityManager;

    public void registerCustomer(CustomerDTO customerDTO) {
        // 1️⃣ Validate input
        if (customerDTO.getEmail() == null || !customerDTO.getEmail().contains("@")) {
            throw new RuntimeException("Invalid email");
        }

        // 2️⃣ Business logic
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setCreatedAt(LocalDateTime.now());

        // 3️⃣ Save to DB
        entityManager.persist(customer);

        // 4️⃣ Send welcome email
        System.out.println("Sending welcome email to " + customer.getEmail());
    }
}
```

⚠️ Problems:
- The class handles too many responsibilities:
  - Validation
  - Business logic
  - Database persistence
  - Email notification
- Changes in any one concern (e.g., email mechanism, DB schema, validation rule) will require modifying this class — violating SRP.

---

## ✅ With SRP (Good Design)

We separate responsibilities into focused classes — each with one reason to change.

### 1️⃣ Validation Layer

```java
@ApplicationScoped
public class CustomerValidator {
    public void validate(CustomerDTO dto) {
        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            throw new RuntimeException("Invalid email");
        }
    }
}
```

### 2️⃣ Persistence Layer

```java
@ApplicationScoped
public class CustomerRepository {

    @Inject
    EntityManager entityManager;

    public void save(Customer customer) {
        entityManager.persist(customer);
    }
}
```

### 3️⃣ Notification Layer

```java
@ApplicationScoped
public class EmailService {
    public void sendWelcomeEmail(String email) {
        System.out.println("Sending welcome email to " + email);
    }
}
```

### 4️⃣ Service Layer (Coordinator)

```java
@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerValidator validator;

    @Inject
    CustomerRepository repository;

    @Inject
    EmailService emailService;

    public void registerCustomer(CustomerDTO dto) {
        validator.validate(dto);                // Validation
        Customer customer = new Customer(dto);  // Business mapping/logic
        repository.save(customer);              // Persistence
        emailService.sendWelcomeEmail(dto.getEmail());  // Notification
    }
}
```

---

## 🧱 Architecture Diagram

```
             ┌───────────────────────┐
             │     CustomerDTO       │
             └──────────┬────────────┘
                        │
                        ▼
             ┌───────────────────────┐
             │   CustomerService     │
             │ (Business Orchestration)
             └──────────┬────────────┘
        ┌───────────────┼───────────────┐
        ▼               ▼               ▼
┌────────────────┐  ┌────────────────┐   ┌────────────────┐
│CustomerValidator│ │CustomerRepository│ │   EmailService  │
│  (Validation)   │ │  (Persistence)   │ │  (Notification) │
└─────────────────┘ └─────────────────┘  └─────────────────┘
```

Each class focuses on one responsibility and has one reason to change.

---

## 🧠 Why This Follows SRP

| Class               | Responsibility             | Reason to Change                     |
|---------------------|---------------------------:|--------------------------------------|
| CustomerValidator   | Validate input data        | Validation rules update              |
| CustomerRepository  | Handle database persistence| Data access logic changes            |
| EmailService        | Send notifications        | Email mechanism changes              |
| CustomerService     | Coordinate workflow       | Business process flow changes        |

---

## 🚀 Benefits of SRP in Microservices

- Maintainability — Small, focused classes are easier to modify.
- Reusability — Components like EmailService can be reused.
- Testability — Each unit can be tested independently.
- Scalability — Services can be extracted or scaled independently.
- Separation of Concerns — Keeps architecture clean and modular.

---

## 🧩 Real-World Analogy

- Chef — cooks the food
- Waiter — serves the food
- Cashier — handles payments

If one person tried to do all roles it would be chaotic — like a class with too many responsibilities.

---

## 🔗 Related SOLID Principles

| Principle | Meaning                                             |
|----------:|-----------------------------------------------------|
| S         | Single Responsibility — One reason to change        |
| O         | Open/Closed — Open for extension, closed for modification |
| L         | Liskov Substitution — Subtypes replace base types   |
| I         | Interface Segregation — Prefer smaller interfaces   |
| D         | Dependency Inversion — Depend on abstractions       |

---

## 🏁 Summary

Apply SRP in Quarkus microservices by separating:
- Validation logic
- Persistence layer
- Notification handling
- Business orchestration

This keeps services modular, maintainable, and testable.

---

## 💻 Example Folder Structure

```
src/
└── main/
    ├── java/
    │   └── com/example/customer/
    │       ├── dto/
    │       │   └── CustomerDTO.java
    │       ├── service/
    │       │   ├── CustomerService.java
    │       │   ├── EmailService.java
    │       │   └── CustomerValidator.java
    │       ├── repository/
    │       │   └── CustomerRepository.java
    │       └── entity/
    │           └── Customer.java
    └── resources/
        └── application.properties
```
---