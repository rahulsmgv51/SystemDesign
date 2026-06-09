# Factory Pattern — ShapeFactory Creates Circle, Square, Triangle

## Overview

The **Factory Pattern** is a **Creational Design Pattern** that provides a centralized mechanism for creating objects without exposing the object creation logic to the client.

Instead of directly instantiating classes using the `new` keyword, clients request objects from a factory.

### Benefits

* Loose coupling
* Centralized object creation
* Better maintainability
* Easier testing
* Improved extensibility

---

# Problem Statement

Suppose we have multiple shape implementations:

* Circle
* Square
* Triangle

Without a factory, client code directly creates objects.

```java
Circle circle = new Circle();
Square square = new Square();
Triangle triangle = new Triangle();
```

Problems:

* Client is tightly coupled to concrete implementations.
* Every new shape requires client code changes.
* Object creation logic is scattered across the application.

Factory Pattern solves these issues.

---

# Project Structure

```text
src/
└── main/
    └── java/
        └── com/
            └── example/
                └── factory/
                    ├── Shape.java
                    ├── Circle.java
                    ├── Square.java
                    ├── Triangle.java
                    ├── ShapeFactory.java
                    └── FactoryPatternDemo.java
```

---

# Class Diagram

```text
                    +-----------+
                    |   Shape   |
                    +-----------+
                    | + draw()  |
                    +-----------+
                          ▲
          ┌───────────────┼───────────────┐
          │               │               │
          ▼               ▼               ▼

      +--------+      +--------+      +----------+
      | Circle |      | Square |      | Triangle |
      +--------+      +--------+      +----------+
      | draw() |      | draw() |      | draw()   |
      +--------+      +--------+      +----------+

                          ▲
                          │
                    +-------------+
                    | ShapeFactory|
                    +-------------+
                    | getShape()  |
                    +-------------+
```

---

# Step 1: Create Shape Interface

## Shape.java

```java
package com.example.factory;

public interface Shape {

    void draw();
}
```

---

# Step 2: Create Concrete Implementations

## Circle.java

```java
package com.example.factory;

public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Drawing Circle");
    }
}
```

---

## Square.java

```java
package com.example.factory;

public class Square implements Shape {

    @Override
    public void draw() {
        System.out.println("Drawing Square");
    }
}
```

---

## Triangle.java

```java
package com.example.factory;

public class Triangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Drawing Triangle");
    }
}
```

---

# Step 3: Create Factory Class

## ShapeFactory.java

```java
package com.example.factory;

public class ShapeFactory {

    public static Shape getShape(String shapeType) {

        if (shapeType == null) {
            throw new IllegalArgumentException(
                    "Shape type cannot be null");
        }

        if ("CIRCLE".equalsIgnoreCase(shapeType)) {
            return new Circle();
        }

        if ("SQUARE".equalsIgnoreCase(shapeType)) {
            return new Square();
        }

        if ("TRIANGLE".equalsIgnoreCase(shapeType)) {
            return new Triangle();
        }

        throw new IllegalArgumentException(
                "Unsupported Shape Type: " + shapeType);
    }
}
```

---

# Step 4: Client Code

## FactoryPatternDemo.java

```java
package com.example.factory;

public class FactoryPatternDemo {

    public static void main(String[] args) {

        Shape circle =
                ShapeFactory.getShape("CIRCLE");

        Shape square =
                ShapeFactory.getShape("SQUARE");

        Shape triangle =
                ShapeFactory.getShape("TRIANGLE");

        circle.draw();
        square.draw();
        triangle.draw();
    }
}
```

---

# Output

```text
Drawing Circle
Drawing Square
Drawing Triangle
```

---

# Execution Flow

## Step 1

Client requests a shape.

```java
Shape shape =
        ShapeFactory.getShape("CIRCLE");
```

## Step 2

Factory evaluates request.

```java
if ("CIRCLE".equalsIgnoreCase(shapeType))
```

## Step 3

Factory creates object.

```java
return new Circle();
```

## Step 4

Object returned to client.

```java
shape.draw();
```

---

# Internal Workflow

```text
Client
   │
   ▼
ShapeFactory.getShape()
   │
   ▼
Factory Logic
   │
   ▼
Create Required Object
   │
   ▼
Return Interface Reference
   │
   ▼
Business Logic
```

---

# Enterprise Version Using Enum

String comparison can introduce bugs.

A better approach is to use Enum.

## ShapeType.java

```java
package com.example.factory;

public enum ShapeType {

    CIRCLE,
    SQUARE,
    TRIANGLE
}
```

---

## Updated ShapeFactory.java

```java
package com.example.factory;

public class ShapeFactory {

    public static Shape getShape(
            ShapeType shapeType) {

        return switch (shapeType) {

            case CIRCLE ->
                    new Circle();

            case SQUARE ->
                    new Square();

            case TRIANGLE ->
                    new Triangle();
        };
    }
}
```

---

## Usage

```java
Shape shape =
        ShapeFactory.getShape(
                ShapeType.CIRCLE);
```

Benefits:

* Compile-time safety
* No typo issues
* Better maintainability

---

# Real-World Example

## Payment Factory

Suppose a banking application supports:

* UPI
* IMPS
* NEFT

### Interface

```java
public interface PaymentService {

    void transfer();
}
```

### Implementations

```java
public class UpiPaymentService
        implements PaymentService {

    @Override
    public void transfer() {
        System.out.println("UPI Transfer");
    }
}
```

```java
public class ImpsPaymentService
        implements PaymentService {

    @Override
    public void transfer() {
        System.out.println("IMPS Transfer");
    }
}
```

```java
public class NeftPaymentService
        implements PaymentService {

    @Override
    public void transfer() {
        System.out.println("NEFT Transfer");
    }
}
```

### Factory

```java
public class PaymentFactory {

    public static PaymentService getService(
            String type) {

        if ("UPI".equalsIgnoreCase(type)) {
            return new UpiPaymentService();
        }

        if ("IMPS".equalsIgnoreCase(type)) {
            return new ImpsPaymentService();
        }

        if ("NEFT".equalsIgnoreCase(type)) {
            return new NeftPaymentService();
        }

        throw new IllegalArgumentException(
                "Unsupported Payment Type");
    }
}
```

Usage:

```java
PaymentService service =
        PaymentFactory.getService("UPI");

service.transfer();
```

---

# Advantages

### Loose Coupling

Client depends on abstraction.

```java
Shape shape =
        ShapeFactory.getShape("CIRCLE");
```

---

### Centralized Creation Logic

All object creation resides in one location.

---

### Easy Maintenance

Add new implementations with minimal impact.

---

### Better Testability

Mock implementations can be returned by the factory.

---

### Cleaner Client Code

Client focuses only on business logic.

---

# Disadvantages

### Factory Becomes Large

Many product types can make the factory complex.

---

### Open/Closed Principle Limitation

Simple Factory requires modification when adding new products.

---

# When to Use Factory Pattern

Use Factory Pattern when:

* Object creation is complex.
* Multiple implementations exist.
* Client should not know concrete classes.
* You want loose coupling.

Avoid Factory Pattern when:

* Only one implementation exists.
* Object creation is trivial.

---

# Complexity Analysis

| Operation       | Complexity |
| --------------- | ---------- |
| Factory Lookup  | O(1)       |
| Object Creation | O(1)       |
| Memory Overhead | O(1)       |

---

# Design Pattern Category

```text
Design Patterns
│
├── Creational
│   ├── Singleton
│   ├── Factory   ←
│   ├── Abstract Factory
│   ├── Builder
│   └── Prototype
│
├── Structural
│
└── Behavioral
```

---

# Interview Answer

### What is Factory Pattern?

Factory Pattern is a Creational Design Pattern that encapsulates object creation logic inside a factory class. Instead of creating objects directly using the `new` keyword, clients request objects from the factory, promoting loose coupling and maintainability.

### Difference Between Factory and Builder

| Factory                            | Builder                              |
| ---------------------------------- | ------------------------------------ |
| Creates different types of objects | Creates complex objects step-by-step |
| Focuses on object creation         | Focuses on object construction       |
| Returns existing implementation    | Returns configured object            |
| Example: ShapeFactory              | Example: PersonBuilder               |

Factory Pattern is widely used in Spring Framework, Payment Gateways, Banking Systems, Notification Services, Messaging Systems, and Enterprise Java Applications.
