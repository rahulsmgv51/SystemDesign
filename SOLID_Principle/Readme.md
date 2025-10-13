# Design Principles
* Software design principles are guidelines and best practice that help developers create software that is robust, scalable, maintainable, and easy to understand.

* These are the set of design principles that guideline us to write code -
SOLID, DRY (Don't Repeat Yourself), YAGNI (You Aren't Gonna Need It), Favor Composition Over Inheritance, Separation of Concerns (SoC), Encapsulate What Varies, Cohesion(Same type of module united whole), Coupling, etc.

## Maximize Cohesion and minimize coupling
Maximizing cohesion means designing each module, class, or function so that its responsibilities are closely related and focused. High cohesion makes code easier to understand, maintain, and reuse because everything inside a module serves a single, well-defined purpose.

Minimizing coupling means reducing dependencies between different modules or components. Low coupling ensures that changes in one part of the system have minimal impact on others, making the codebase more flexible and easier to modify or extend.

**Summary:**  
- **High Cohesion:** Each module should do one thing and do it well.  
- **Low Coupling:** Modules should interact as little as possible, only through well-defined interfaces.

### Program to an Interface, Not an Implementation
Here is an example of tight coupling, which violates the "Program to an Interface, Not an Implementation" principle:

```java
class Engine {
    public void start() {
        System.out.println("Engine Started");
    }
}

class Car {
    private Engine engine;

    public Car() {
        // Hardcoded dependency on Engine class.
        this.engine = new Engine(); // Tight Coupling
    }

    public void drive() {
        engine.start();
        System.out.println("Car is Driving");
    }
}
```

- In this example, the `Car` class is tightly coupled to the `Engine` class. If you want to use a different type of engine (e.g., electric or sports engine), you must modify the `Car` class itself. This makes the code less flexible and harder to extend or test.

A better approach is to program to an interface, which allows for greater flexibility and easier testing:

```java
interface Engine {
    void start();
}

class Car {
    private Engine engine;

    // The Car class now depends on the Engine interface, not a specific implementation.
    public Car(Engine engine) {
        this.engine = engine;
    }

    public void drive() {
        engine.start();
        System.out.println("Car is Driving");
    }
}

class PetrolEngine implement Engine{
    public void start(){
        System.out.println("Petrol Engine Started");
    }
}

class ElecticEngine implement Engine{
    public void start(){
        System.out.println("Electric Engine Started");
    }
}
```

- By depending on the `Engine` interface, the `Car` class can work with any implementation of `Engine` (e.g., `ElectricEngine`, `SportsEngine`). This makes the code more extensible and testable.

## 🍕 Favor Composition Over Inheritance

### 📘 Overview

In Object-Oriented Design, **Composition** and **Inheritance** are two ways to reuse code and build relationships between classes.

While **Inheritance** allows a class to acquire behavior from a parent class, **Composition** allows a class to achieve functionality by containing references to other objects.

> ⚖️ **Principle:**  
> “Favor composition over inheritance” means — instead of creating rigid class hierarchies, build flexible systems by **combining reusable components**.

---

## 🔴 Problem with Inheritance

Consider the following inheritance structure:

```
          Pizza
       +-----------+
       | bake()    |
       | cut()     |
       +-----------+
        /     |     \
    Cheese  Paneer  Mushroom
    Pizza   Pizza   Pizza
```

### ❌ Issues:
1. **Rigid hierarchy** – Adding new pizza types or combinations (e.g., *CheeseMushroomPizza*) requires new subclasses.
2. **Code duplication** – Each subclass reimplements or overrides similar methods.
3. **Poor scalability** – Any new functionality (like `addTopping()`, `deliver()`, `applyDiscount()`) needs to be updated across all subclasses.
4. **Violation of OCP** – To extend functionality, you end up modifying existing code.

---

## 🟢 Solution: Use Composition

With composition, instead of subclassing `Pizza`, we **compose** it using smaller, reusable objects like `Topping`, `Crust`, `Sauce`, etc.

### ✅ Example Code (Java)

```java
import java.util.List;
import java.util.stream.Collectors;

interface Topping {
    String getName();
}

class Cheese implements Topping {
    public String getName() { return "Cheese"; }
}

class Paneer implements Topping {
    public String getName() { return "Paneer"; }
}

class Mushroom implements Topping {
    public String getName() { return "Mushroom"; }
}

class Pizza {
    private List<Topping> toppings;

    public Pizza(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public void bake() {
        System.out.println("Baking pizza with: " + getToppingNames());
    }

    public void cut() {
        System.out.println("Cutting pizza into slices");
    }

    private String getToppingNames() {
        return toppings.stream()
                       .map(Topping::getName)
                       .collect(Collectors.joining(", "));
    }
}

public class Main {
    public static void main(String[] args) {
        Pizza cheesePizza = new Pizza(List.of(new Cheese()));
        Pizza paneerPizza = new Pizza(List.of(new Paneer()));
        Pizza comboPizza = new Pizza(List.of(new Paneer(), new Mushroom(), new Cheese()));

        cheesePizza.bake();  // Baking pizza with: Cheese
        paneerPizza.bake();  // Baking pizza with: Paneer
        comboPizza.bake();   // Baking pizza with: Paneer, Mushroom, Cheese

        comboPizza.cut();    // Cutting pizza into slices
    }
}
```

---

## 🧠 Key Takeaways

| Aspect | Inheritance | Composition |
|--------|--------------|-------------|
| **Relationship Type** | “is-a” | “has-a” |
| **Flexibility** | Rigid, hard to extend | Highly flexible |
| **Code Reuse** | Through superclass | Through contained objects |
| **Extensibility** | Requires subclassing | Add new behavior by combining |
| **Coupling** | Tight | Loose |
| **OOP Principle** | Static binding | Dynamic behavior through delegation |

---

## 💡 Advantages of Composition

1. **Better reusability** – Components can be shared across different classes.
2. **Improved flexibility** – You can change behavior at runtime by composing different objects.
3. **Simplifies testing** – Smaller, focused classes are easier to unit test.
4. **Avoids deep hierarchies** – No “class explosion” problem.

---

## 🧩 Relation to SOLID Principles

| Principle | How Composition Supports It |
|------------|------------------------------|
| **SRP (Single Responsibility)** | Each class handles one specific behavior (e.g., `Cheese`, `Paneer`, `Pizza`). |
| **OCP (Open/Closed)** | New toppings can be added without modifying existing classes. |
| **DIP (Dependency Inversion)** | High-level `Pizza` depends on the abstraction `Topping`, not concrete classes. |

---

## ✅ Summary

- **Inheritance**: good for stable “is-a” relationships, but can become rigid.  
- **Composition**: promotes flexibility, scalability, and cleaner architecture.  
- In our example, instead of creating separate subclasses for each pizza type, we **compose** a `Pizza` with various `Topping` objects.

> **In short:**  
> 🧱 Build behavior by combining parts (composition), not stacking hierarchies (inheritance).

---

### 🧾 Example Output

```
Baking pizza with: Cheese
Baking pizza with: Paneer
Baking pizza with: Paneer, Mushroom, Cheese
Cutting pizza into slices
```

---

## Principles VS Patterns
- Principles are broad guidelines that define `good design` at a high level.
- Patterns are context specific, repeatable solutions to common software design problems, Often used to implement those principles effectively.