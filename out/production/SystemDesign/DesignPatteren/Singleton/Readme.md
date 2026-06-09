# Singleton Pattern — Thread-Safe with Double-Checked Locking (DCL)

## Overview

The **Singleton Pattern** is a **Creational Design Pattern** that ensures:

1. Only **one instance** of a class exists throughout the application.
2. A **global access point** is provided to that instance.

Singletons are commonly used for:

* Configuration Managers
* Cache Managers
* Logging Frameworks
* Database Connection Managers
* Thread Pools
* Application Contexts

---

## Problem Statement

Suppose we have a configuration manager.

```java id="k1"
public class ConfigManager {
}
```

We want:

* Only one instance in the JVM.
* Shared access across all threads.
* Lazy initialization.
* Thread safety.

---

## Naive Singleton Implementation

```java id="k2"
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {

        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }
}
```

### Problem

In a multi-threaded environment:

```text id="k3"
Thread-1 checks instance == null
Thread-2 checks instance == null

Thread-1 creates object
Thread-2 creates object

Two instances created ❌
```

This violates the Singleton principle.

---

## Synchronized Method Solution

```java id="k4"
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {

        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }
}
```

### Advantage

* Thread-safe.

### Drawback

Every call acquires a lock.

```text id="k5"
getInstance()
    ↓
Acquire Lock
    ↓
Check Instance
    ↓
Release Lock
```

Even after the object is created, synchronization overhead remains.

Performance suffers in high-concurrency systems.

---

## Double-Checked Locking (DCL)

Double-Checked Locking reduces synchronization overhead by locking only during the first initialization.

```java id="k6"
public class Singleton {

    private static volatile Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {

        if (instance == null) {

            synchronized (Singleton.class) {

                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}
```

---

## Why "Double Checked"?

### First Check

```java id="k7"
if (instance == null)
```

Avoids synchronization after initialization.

### Second Check

```java id="k8"
synchronized (Singleton.class) {

    if (instance == null) {
        instance = new Singleton();
    }
}
```

Protects against multiple threads entering the synchronized block simultaneously.

---

## Execution Flow

### Scenario: First Access

```text id="k9"
Thread-1
   │
   ▼
instance == null
   │
   ▼
Acquire Lock
   │
   ▼
instance == null
   │
   ▼
Create Object
   │
   ▼
Release Lock
```

### Scenario: Subsequent Access

```text id="k10"
Thread-N
   │
   ▼
instance != null
   │
   ▼
Return Existing Object
```

No lock is acquired.

---

## Why `volatile` is Required?

```java id="k11"
private static volatile Singleton instance;
```

Without `volatile`, the JVM may reorder instructions.

### Object Creation Internally

```text id="k12"
1. Allocate memory
2. Initialize object
3. Assign reference
```

Due to instruction reordering:

```text id="k13"
1. Allocate memory
3. Assign reference
2. Initialize object
```

Another thread may see a partially initialized object.

This can cause subtle concurrency bugs.

`volatile` prevents this reordering.

---

## Complete Implementation

```java id="k14"
public class Singleton {

    private static volatile Singleton instance;

    private Singleton() {

        if (instance != null) {
            throw new RuntimeException(
                "Use getInstance() method");
        }
    }

    public static Singleton getInstance() {

        if (instance == null) {

            synchronized (Singleton.class) {

                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }

    public void showMessage() {
        System.out.println("Singleton Instance");
    }
}
```

---

## Test Class

```java id="k15"
public class SingletonDemo {

    public static void main(String[] args) {

        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();

        System.out.println(s1 == s2);
    }
}
```

### Output

```text id="k16"
true
```

Both references point to the same object.

---

## Multithreaded Test

```java id="k17"
public class SingletonDemo {

    public static void main(String[] args) {

        Runnable task = () -> {
            Singleton singleton =
                    Singleton.getInstance();

            System.out.println(
                    singleton.hashCode());
        };

        for (int i = 0; i < 10; i++) {
            new Thread(task).start();
        }
    }
}
```

### Output

```text id="k18"
12345678
12345678
12345678
12345678
12345678
...
```

Same hash code indicates the same instance.

---

## UML Diagram

```text id="k19"
+----------------------+
|      Singleton       |
+----------------------+
| - instance           |
+----------------------+
| - Singleton()        |
| + getInstance()      |
| + showMessage()      |
+----------------------+
```

---

## Advantages

### Single Instance

Only one object exists.

### Lazy Initialization

Object is created only when required.

### Thread Safety

Safe for concurrent access.

### Better Performance

Synchronization occurs only once.

### Global Access

Accessible from anywhere in the application.

---

## Disadvantages

### Global State

Can make testing difficult.

### Hidden Dependencies

Classes may depend on Singleton implicitly.

### Reflection Risks

Reflection can break Singleton unless protected.

### Serialization Risks

Deserialization may create a new instance.

---

## Production-Grade Alternative

The most recommended Singleton implementation in modern Java is the Enum Singleton.

```java id="k20"
public enum Singleton {

    INSTANCE;

    public void showMessage() {
        System.out.println("Singleton Instance");
    }
}
```

Usage:

```java id="k21"
Singleton.INSTANCE.showMessage();
```

### Benefits

* Thread-safe by JVM design
* Serialization-safe
* Reflection-safe
* Simpler implementation

---

## Real-World Examples

### Java Runtime

```java id="k22"
Runtime runtime = Runtime.getRuntime();
```

### Spring Bean Scope

```java id="k23"
@Component
public class UserService {
}
```

Default Spring beans are Singleton scoped.

### Logger

```java id="k24"
Logger logger =
    LoggerFactory.getLogger(MyClass.class);
```

Logging frameworks internally use Singleton-like patterns.

---

## Complexity Analysis

| Operation            | Complexity      |
| -------------------- | --------------- |
| getInstance()        | O(1)            |
| Memory Usage         | O(1)            |
| Synchronization Cost | Only First Call |

---

## Design Pattern Category

```text id="k25"
Design Patterns
│
├── Creational
│   ├── Singleton  ←
│   ├── Factory
│   ├── Abstract Factory
│   ├── Builder
│   └── Prototype
│
├── Structural
│
└── Behavioral
```

The Singleton Pattern is one of the most frequently asked design patterns in Java interviews and is widely used in enterprise systems for shared resources, caching, logging, and configuration management.
