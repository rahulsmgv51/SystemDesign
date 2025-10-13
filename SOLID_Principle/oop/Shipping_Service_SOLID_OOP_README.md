# 🚚 Shipping Service System — OOP & SOLID Example

This project demonstrates **Object-Oriented Programming (OOP)** concepts and the **Open/Closed Principle (OCP)** from SOLID using a simple **Shipping Service System**.

---

## 🧩 Overview

The system supports multiple shipping methods — **Standard Shipping**, **Express Shipping**, and **Store Pickup** — each with its own cost calculation logic.  
All are handled polymorphically by a single `ShipmentService`.

---

## 🏗️ Class Diagram

```
                ShippingMethod (abstract)
                ┌────────────────────────────┐
                │ - destination: String      │
                │ - weight: double           │
                │ + calculateCost(): double  │
                │ + getDetails(): String     │
                └───────────┬────────────────┘
                            │
     ┌──────────────────────┼─────────────────────────────┐
     │                      │                             │
StandardShipping     ExpressShipping              StorePickup
(15 Rs/kg)           (30 Rs/kg)                   (0 Rs)
```

---

## 🧱 Classes

### 1️⃣ ShippingMethod (Abstract Class)
Defines the structure for all shipping methods.

```java
abstract class ShippingMethod {
    protected String destination;
    protected double weight;

    public ShippingMethod(String destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public abstract double calculateCost();
    public abstract String getDetails();
}
```

---

### 2️⃣ StandardShipping

```java
public class StandardShipping extends ShippingMethod {
    public StandardShipping(String destination, double weight) {
        super(destination, weight);
    }

    @Override
    public double calculateCost() {
        return weight * 15;
    }

    @Override
    public String getDetails() {
        return "Standard Shipping to " + destination + " (" + weight + "kg): " + calculateCost() + " Rs.";
    }
}
```

---

### 3️⃣ ExpressShipping

```java
public class ExpressShipping extends ShippingMethod {
    public ExpressShipping(String destination, double weight) {
        super(destination, weight);
    }

    @Override
    public double calculateCost() {
        return weight * 30;
    }

    @Override
    public String getDetails() {
        return "Express Shipping to " + destination + " (" + weight + "kg): " + calculateCost() + " Rs.";
    }
}
```

---

### 4️⃣ StorePickup

```java
public class StorePickup extends ShippingMethod {
    public StorePickup(String destination, double weight) {
        super(destination, weight);
    }

    @Override
    public double calculateCost() {
        return 0;
    }

    @Override
    public String getDetails() {
        return "Store Pickup at " + destination + ": " + calculateCost() + " Rs.";
    }
}
```

---

### 5️⃣ ShipmentService

Handles a list of shipments **polymorphically**.

```java
public class ShipmentService {
    private List<ShippingMethod> shipments = new ArrayList<>();

    public void addShipment(ShippingMethod shipment) {
        shipments.add(shipment);
    }

    public void displayAllShipments() {
        for (ShippingMethod shipment : shipments) {
            System.out.println(shipment.getDetails());
        }
    }

    public double calculateTotalShippingCost() {
        double totalCost = 0;
        for (ShippingMethod shipment : shipments) {
            totalCost += shipment.calculateCost();
        }
        return totalCost;
    }
}
```

---

### 6️⃣ Program (Main Class)

```java
public class Program {
    public static void main(String[] args) {
        StandardShipping s1 = new StandardShipping("Delhi", 12);
        StandardShipping s2 = new StandardShipping("Banglore", 7);
        ExpressShipping s3 = new ExpressShipping("Pune", 4);
        StorePickup s4 = new StorePickup("Mumbai", 12);

        ShipmentService service = new ShipmentService();
        service.addShipment(s1);
        service.addShipment(s2);
        service.addShipment(s3);
        service.addShipment(s4);

        service.displayAllShipments();
        System.out.println("Total Cost : " + service.calculateTotalShippingCost());
    }
}
```

---

## 🧠 Output Example

```
Standard Shipping to Delhi (12kg): 180.0 Rs.
Standard Shipping to Banglore (7kg): 105.0 Rs.
Express Shipping to Pune (4kg): 120.0 Rs.
Store Pickup at Mumbai: 0.0 Rs.
Total Cost : 405.0
```

---

## 🧩 Concepts Demonstrated

| OOP Concept | How it's used |
|--------------|---------------|
| **Abstraction** | `ShippingMethod` defines abstract behavior for all shipping types. |
| **Inheritance** | Concrete shipping classes extend the abstract base. |
| **Polymorphism** | `ShipmentService` handles all types via the base class reference. |
| **Encapsulation** | Data and behavior are grouped logically inside each class. |

---

## 💡 SOLID Principle Highlighted

**Open/Closed Principle (OCP)** — System is *open for extension* but *closed for modification*.

You can add new classes (like `DroneShipping`, `InternationalShipping`) **without changing** existing logic in `ShipmentService`.

---

## ✅ Summary

This project demonstrates how to design flexible, maintainable systems by leveraging OOP features.  
Each new shipping type adds functionality **without breaking** existing code.