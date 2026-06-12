# Decorator Pattern — Add Features to Objects Without Modifying the Class

## Overview

This project demonstrates the **Decorator Design Pattern** using a Coffee Ordering System.

The Decorator Pattern allows behavior to be added to an object dynamically without modifying its original implementation. Instead of creating multiple subclasses for every possible feature combination, decorators wrap existing objects and extend their functionality at runtime.

This pattern is widely used in enterprise applications, Spring Boot filters, API gateways, logging frameworks, and middleware systems.

---

## Design Pattern Type

**Structural Design Pattern**

---

## Problem Statement

Consider a coffee ordering application where customers can customize their coffee by adding:

* Milk
* Sugar
* Cream

Without the Decorator Pattern, you may end up creating numerous subclasses:

```text
SimpleCoffee
CoffeeWithMilk
CoffeeWithSugar
CoffeeWithCream
CoffeeWithMilkAndSugar
CoffeeWithMilkAndSugarAndCream
...
```

As customization options grow, maintaining these classes becomes difficult.

The Decorator Pattern solves this problem by allowing features to be added dynamically at runtime.

---

## Solution

The Decorator Pattern wraps an existing object with decorator objects.

Each decorator:

* Implements the same interface as the original object.
* Holds a reference to the wrapped object.
* Adds new behavior before or after delegating the request.

---

## Class Diagram

```text
                    +----------------+
                    |    Coffee      |
                    |  (Interface)   |
                    +----------------+
                             ^
                             |
            ---------------------------------
            |                               |
            |                               |
+----------------------+      +--------------------------+
|    SimpleCoffee      |      |    CoffeeDecorator       |
| (Concrete Component) |      |  (Abstract Decorator)    |
+----------------------+      +--------------------------+
                                         ^
                                         |
               -----------------------------------------------
               |                     |                      |
               |                     |                      |
      +----------------+   +----------------+   +----------------+
      | MilkDecorator  |   | SugarDecorator |   | CreamDecorator |
      +----------------+   +----------------+   +----------------+
```

---

## Project Structure

```text
src
├── Coffee.java
├── SimpleCoffee.java
├── CoffeeDecorator.java
├── MilkDecorator.java
├── SugarDecorator.java
├── CreamDecorator.java
└── Main.java
```

---

## Component Interface

```java
public interface Coffee {

    String getDescription();

    double getCost();
}
```

---

## Concrete Component

```java
public class SimpleCoffee implements Coffee {

    @Override
    public String getDescription() {
        return "Simple Coffee";
    }

    @Override
    public double getCost() {
        return 100;
    }
}
```

---

## Abstract Decorator

```java
public abstract class CoffeeDecorator implements Coffee {

    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}
```

---

## Concrete Decorators

### MilkDecorator

```java
public class MilkDecorator extends CoffeeDecorator {

    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 20;
    }
}
```

### SugarDecorator

```java
public class SugarDecorator extends CoffeeDecorator {

    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Sugar";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 10;
    }
}
```

### CreamDecorator

```java
public class CreamDecorator extends CoffeeDecorator {

    public CreamDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Cream";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 30;
    }
}
```

---

## Client Code

```java
public class Main {

    public static void main(String[] args) {

        Coffee coffee = new SimpleCoffee();

        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        coffee = new CreamDecorator(coffee);

        System.out.println(coffee.getDescription());
        System.out.println("Total Cost: ₹" + coffee.getCost());
    }
}
```

---

## Output

```text
Simple Coffee, Milk, Sugar, Cream
Total Cost: ₹160.0
```

---

## Execution Flow

### Step 1

```java
Coffee coffee = new SimpleCoffee();
```

Output:

```text
Simple Coffee
Cost = ₹100
```

### Step 2

```java
coffee = new MilkDecorator(coffee);
```

Output:

```text
Simple Coffee, Milk
Cost = ₹120
```

### Step 3

```java
coffee = new SugarDecorator(coffee);
```

Output:

```text
Simple Coffee, Milk, Sugar
Cost = ₹130
```

### Step 4

```java
coffee = new CreamDecorator(coffee);
```

Output:

```text
Simple Coffee, Milk, Sugar, Cream
Cost = ₹160
```

---

## Internal Wrapping Structure

```text
CreamDecorator
      |
SugarDecorator
      |
MilkDecorator
      |
SimpleCoffee
```

Method calls travel from the outermost decorator to the innermost object.

```text
CreamDecorator.getCost()
      |
SugarDecorator.getCost()
      |
MilkDecorator.getCost()
      |
SimpleCoffee.getCost()
```

Cost Calculation:

```text
100 + 20 + 10 + 30 = 160
```

---

## Real-World Microservice Example

Decorator Pattern is heavily used in API request processing.

```text
Incoming Request
        |
Authentication Decorator
        |
Logging Decorator
        |
Rate Limiting Decorator
        |
Business Service
```

Example:

```java
Service service =
    new LoggingDecorator(
        new AuthenticationDecorator(
            new RateLimitDecorator(
                new PaymentService()
            )
        )
    );
```

---

## Spring Boot Examples

Decorator-like implementations are commonly seen in:

### Servlet Filters

* Authentication Filter
* Logging Filter
* Audit Filter

### Handler Interceptors

* Request Validation
* Performance Monitoring
* User Tracking

### API Gateway Filters

* JWT Validation
* Rate Limiting
* Request Transformation
* Request Logging

---

## Advantages

### Runtime Flexibility

Features can be added dynamically.

### Open/Closed Principle

Existing code remains unchanged.

### Avoids Class Explosion

No need to create numerous subclasses.

### Reusable Components

Decorators can be reused across different objects.

### Easy Feature Composition

Features can be stacked in any combination.

---

## Disadvantages

### Increased Number of Objects

Each decorator creates an additional wrapper.

### More Complex Debugging

Nested decorators can make tracing harder.

### Configuration Complexity

Excessive decorators can reduce readability.

---

## Decorator vs Inheritance

| Inheritance              | Decorator                |
| ------------------------ | ------------------------ |
| Compile-time behavior    | Runtime behavior         |
| Static extension         | Dynamic extension        |
| Many subclasses          | Few reusable decorators  |
| Tight coupling           | Loose coupling           |
| Hard to combine features | Easy feature composition |

---

## When to Use

Use Decorator Pattern when:

* Behavior must be added dynamically.
* Existing classes should not be modified.
* Multiple feature combinations exist.
* Inheritance causes class explosion.
* Features can be layered independently.

Common examples:

* Logging
* Security
* Monitoring
* Compression
* Encryption
* Caching
* API Middleware
* Request Processing

---

## Interview Questions

### What is the Decorator Pattern?

A structural design pattern that dynamically adds behavior to an object without modifying its original class.

### Why use Decorator instead of Inheritance?

Decorator avoids creating numerous subclasses and provides runtime flexibility.

### Which SOLID principle does it follow?

Open/Closed Principle (OCP).

### Is Decorator Pattern Structural or Behavioral?

Structural Design Pattern.

### Give a Spring Boot example.

* Servlet Filters
* Spring Security Filter Chain
* Handler Interceptors
* API Gateway Filters

---

## Key Takeaway

The Decorator Pattern wraps existing objects and extends their functionality dynamically. It provides a flexible alternative to inheritance and is commonly used in enterprise applications, middleware frameworks, Spring Boot filters, API gateways, logging systems, and microservice architectures.
