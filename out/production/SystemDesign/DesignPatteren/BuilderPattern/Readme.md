# Builder Pattern with Fluent API

## Overview

This project demonstrates the **Builder Design Pattern** in Java using a **Fluent API** approach.

The Builder Pattern is a **Creational Design Pattern** that allows complex objects to be constructed step-by-step while keeping the code readable, maintainable, and flexible.

Instead of creating objects using large constructors with many parameters, the Builder Pattern provides a clean and expressive way to build objects.

---

## Why Builder Pattern?

Consider the following constructor:

```java
Person person = new Person(
        "Rahul",
        "Vishwakarma",
        28,
        "rahul@gmail.com",
        "9876543210",
        "Mumbai");
```

### Problems

* Difficult to understand parameter order.
* Hard to maintain when new fields are added.
* Constructor overloading becomes excessive.
* Readability decreases as object complexity grows.

The Builder Pattern solves these issues by providing a more readable and maintainable object creation mechanism.

---

## Project Structure

```text
src/
└── main/
    └── java/
        └── com/
            └── example/
                └── builder/
                    ├── Person.java
                    └── BuilderPatternDemo.java
```

---

## Person Class

The `Person` class is designed as an immutable object.

### Fields

```java
private final String firstName;
private final String lastName;
private final int age;
private final String email;
private final String phoneNumber;
private final String address;
```

### Characteristics

* Immutable
* Thread-safe after creation
* No public constructor
* Object creation controlled through Builder

---

## Builder Implementation

### Static Inner Builder Class

```java
public static class PersonBuilder
```

The Builder class contains the same fields as the target object.

```java
private String firstName;
private String lastName;
private int age;
private String email;
private String phoneNumber;
private String address;
```

---

## Fluent API

Each builder method returns the current builder instance.

### Example

```java
public PersonBuilder firstName(String firstName) {
    this.firstName = firstName;
    return this;
}
```

This enables method chaining.

```java
Person person = new Person.PersonBuilder()
        .firstName("Rahul")
        .lastName("Vishwakarma")
        .age(28)
        .email("rahul@gmail.com")
        .build();
```

---

## Build Method

The `build()` method creates the final immutable object.

```java
public Person build() {
    return new Person(this);
}
```

### Validation

Builder can perform validations before object creation.

```java
if (firstName == null || firstName.isBlank()) {
    throw new IllegalArgumentException(
        "First Name is mandatory");
}
```

### Benefits

* Prevents invalid objects
* Centralizes validation logic
* Ensures object consistency

---

## Execution Flow

### Step 1: Create Builder

```java
Person.PersonBuilder builder =
        new Person.PersonBuilder();
```

### Step 2: Set Properties

```java
builder.firstName("Rahul");
builder.lastName("Vishwakarma");
builder.age(28);
```

### Step 3: Build Object

```java
Person person = builder.build();
```

### Internal Flow

```text
Builder Object
      │
      ▼
Set Properties
      │
      ▼
Validation
      │
      ▼
Create Person Object
      │
      ▼
Return Immutable Object
```

---

## Example Usage

```java
Person person = new Person.PersonBuilder()
        .firstName("Rahul")
        .lastName("Vishwakarma")
        .age(28)
        .email("rahul@gmail.com")
        .phoneNumber("9876543210")
        .address("Mumbai")
        .build();

System.out.println(person);
```

### Output

```text
Person{
firstName='Rahul',
lastName='Vishwakarma',
age=28,
email='rahul@gmail.com',
phoneNumber='9876543210',
address='Mumbai'
}
```

---

## UML Diagram

```text
+----------------------+
|       Person         |
+----------------------+
| - firstName          |
| - lastName           |
| - age                |
| - email              |
| - phoneNumber        |
| - address            |
+----------------------+
| + getFirstName()     |
| + getLastName()      |
| + getAge()           |
| + getEmail()         |
+----------------------+
           ▲
           |
           |
+----------------------+
|    PersonBuilder     |
+----------------------+
| - firstName          |
| - lastName           |
| - age                |
| - email              |
| - phoneNumber        |
| - address            |
+----------------------+
| + firstName()        |
| + lastName()         |
| + age()              |
| + email()            |
| + phoneNumber()      |
| + address()          |
| + build()            |
+----------------------+
```

---

## Advantages

### 1. Improved Readability

```java
new Person.PersonBuilder()
    .firstName("Rahul")
    .lastName("Vishwakarma")
    .build();
```

Much easier to understand than a long constructor.

### 2. Immutable Objects

```java
private final String firstName;
```

Objects cannot be modified after creation, improving consistency and thread safety.

### 3. Flexible Object Creation

Optional fields can be skipped.

```java
Person person = new Person.PersonBuilder()
        .firstName("Rahul")
        .lastName("Vishwakarma")
        .build();
```

### 4. Validation Support

```java
if (age < 0) {
    throw new IllegalArgumentException();
}
```

Validation is centralized inside the Builder.

### 5. No Constructor Explosion

Without Builder:

```java
Person()
Person(String firstName)
Person(String firstName, String lastName)
Person(String firstName, String lastName, int age)
...
```

Builder eliminates the need for numerous overloaded constructors.

---

## Real-World Applications

### Spring Boot

```java
ResponseEntity.ok()
        .header("token", "123")
        .body(data);
```

### Lombok Builder

```java
@Builder
public class Person {
    private String firstName;
    private String lastName;
}
```

Usage:

```java
Person.builder()
      .firstName("Rahul")
      .lastName("Vishwakarma")
      .build();
```

### Java HTTP Client

```java
HttpRequest request = HttpRequest.newBuilder()
        .uri(uri)
        .header("Content-Type", "application/json")
        .build();
```

---

## When to Use Builder Pattern

### Use Builder When

* Object contains many fields.
* Multiple optional parameters exist.
* Constructors become difficult to manage.
* Object should be immutable.
* Validation is required before object creation.

### Avoid Builder When

* Object contains only 2–3 mandatory fields.
* Object creation is simple and unlikely to change.

---

## Complexity Analysis

| Operation       | Complexity |
| --------------- | ---------- |
| Build Object    | O(1)       |
| Memory Overhead | O(1)       |
| Method Chaining | O(1)       |

---

## Design Pattern Category

```text
Design Patterns
│
├── Creational
│   ├── Singleton
│   ├── Factory
│   ├── Abstract Factory
│   ├── Builder
│   └── Prototype
│
├── Structural
│
└── Behavioral
```

The Builder Pattern is one of the most widely used creational patterns in modern Java applications, particularly in Spring Boot microservices, REST APIs, DTO creation, configuration objects, and enterprise banking systems.

---
