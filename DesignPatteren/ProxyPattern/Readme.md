# Proxy Pattern — Lazy Loading & Access Control

## Overview

The **Proxy Pattern** is a **Structural Design Pattern** that provides a surrogate or placeholder object to control access to another object.

Instead of allowing clients to communicate directly with the real object, a proxy object sits in front of it and performs additional operations such as:

* Access Control
* Authentication
* Authorization
* Logging
* Monitoring
* Caching
* Lazy Loading

The proxy decides whether a request should reach the actual service.

---

## Problem Statement

Consider a payment processing system.

Without a proxy:

```text
Client
   |
   v
Payment Service
```

Problems:

* No access control
* No authentication
* No logging
* No monitoring
* Expensive service initialization

For example:

* Unauthorized users can invoke payment APIs.
* Large resources are loaded even when never used.
* Every request reaches the backend service directly.

---

## Solution

Introduce a Proxy between the client and the actual service.

```text
Client
   |
   v
Payment Proxy
   |
   v
Payment Service
```

The Proxy:

1. Validates permissions.
2. Creates the real service only when needed.
3. Logs activity.
4. Controls access.
5. Forwards valid requests.

---

## Design Pattern Classification

| Category        | Type               |
| --------------- | ------------------ |
| Design Pattern  | Structural Pattern |
| Purpose         | Control Access     |
| Complexity      | Low                |
| Usage Frequency | Very High          |

---

## Components

### Subject

Defines a common interface for both the real object and proxy.

```java
public interface PaymentService {
    void processPayment(double amount);
}
```

### Real Subject

Actual implementation that contains business logic.

```java
public class RealPaymentService implements PaymentService {
}
```

### Proxy

Controls access to the real object.

```java
public class PaymentProxy implements PaymentService {
}
```

### Client

Uses the proxy instead of directly calling the service.

```java
PaymentService service =
        new PaymentProxy("ADMIN");
```

---

## Class Diagram

```text
                    +------------------+
                    | PaymentService   |
                    +------------------+
                             ^
                             |
           +----------------+----------------+
           |                                 |
           |                                 |
+----------------------+      +--------------------------+
| RealPaymentService   |      | PaymentProxy            |
+----------------------+      +--------------------------+
| processPayment()     |      | processPayment()        |
+----------------------+      +--------------------------+
                                         |
                                         |
                                         v
                             +----------------------+
                             | RealPaymentService   |
                             +----------------------+
```

---

## Implementation

### Step 1: Subject Interface

```java
public interface PaymentService {
    void processPayment(double amount);
}
```

### Step 2: Real Service

```java
public class RealPaymentService
        implements PaymentService {

    public RealPaymentService() {
        System.out.println(
            "Initializing Payment Service...");
    }

    @Override
    public void processPayment(double amount) {
        System.out.println(
            "Payment Processed: " + amount);
    }
}
```

### Step 3: Proxy

```java
public class PaymentProxy
        implements PaymentService {

    private RealPaymentService paymentService;

    private String role;

    public PaymentProxy(String role) {
        this.role = role;
    }

    @Override
    public void processPayment(double amount) {

        if (!"ADMIN".equals(role)) {
            System.out.println("Access Denied");
            return;
        }

        if (paymentService == null) {
            paymentService =
                    new RealPaymentService();
        }

        paymentService.processPayment(amount);
    }
}
```

### Step 4: Client

```java
public class Main {

    public static void main(String[] args) {

        PaymentService service =
                new PaymentProxy("ADMIN");

        service.processPayment(5000);
    }
}
```

---

## Output

```text
Initializing Payment Service...
Payment Processed: 5000
```

---

## Execution Flow

### Authorized User

```text
Client
   |
   v
PaymentProxy
   |
   | Role = ADMIN
   |
   v
RealPaymentService
   |
   v
Payment Success
```

### Unauthorized User

```text
Client
   |
   v
PaymentProxy
   |
   | Role = CUSTOMER
   |
   v
Access Denied
```

---

## Lazy Loading Explained

Without Proxy:

```java
RealPaymentService service =
        new RealPaymentService();
```

The object is created immediately.

With Proxy:

```java
if(paymentService == null){
    paymentService =
         new RealPaymentService();
}
```

The object is created only when required.

Benefits:

* Faster startup
* Better memory utilization
* Reduced resource consumption

---

## API-Based Example

### Payment API

```http
POST /payments
```

Request:

```json
{
  "amount": 5000
}
```

Flow:

```text
Client
  |
  v
Payment Proxy API
  |
  +--> Validate JWT
  +--> Validate Role
  +--> Log Request
  +--> Rate Limit Check
  |
  v
Payment Service
```

---

## Sequence Diagram

```text
Client
  |
  | POST /payment
  |
  v
Proxy
  |
  | Validate Role
  |
  +---- Invalid ----> Access Denied
  |
  +---- Valid ------+
                    |
                    v
            Real Service
                    |
                    v
              Payment Done
                    |
                    v
                Response
```

---

## Real-World Banking Example

### Transaction Flow

```text
Agent Device
      |
      v
Transaction Proxy Service
      |
      +--> Agent Validation
      +--> JWT Validation
      +--> Transaction Limits
      +--> Fraud Checks
      +--> Audit Logs
      |
      v
    Bank
```

### Responsibilities of Transaction Proxy

* Authentication
* Authorization
* AML Validation
* Fraud Detection
* Audit Logging
* Rate Limiting
* Request Validation

---

## Types of Proxy

### 1. Virtual Proxy

Used for lazy loading expensive objects.

Examples:

* Images
* PDFs
* Videos

### 2. Protection Proxy

Used for access control.

Examples:

* Admin APIs
* Banking APIs
* Internal Services

### 3. Remote Proxy

Represents remote services.

Examples:

* REST APIs
* SOAP Services
* gRPC Services

### 4. Caching Proxy

Stores responses and reduces backend calls.

Examples:

* User Profile APIs
* Product Catalog APIs
* Configuration Services

---

## Advantages

### Security

Controls access before reaching the actual object.

### Lazy Loading

Creates expensive resources only when required.

### Logging

Captures all requests and responses.

### Monitoring

Tracks performance and metrics.

### Caching

Improves response time.

### Separation of Concerns

Business logic remains independent of infrastructure concerns.

---

## Disadvantages

### Additional Layer

Adds another object in the request flow.

### Increased Complexity

More classes and maintenance.

### Performance Overhead

Every request passes through the proxy.

---

## Proxy vs Decorator

| Feature         | Proxy                       | Decorator               |
| --------------- | --------------------------- | ----------------------- |
| Purpose         | Control Access              | Add Features            |
| Focus           | Security, Access Control    | Behavior Extension      |
| Object Creation | Can Be Delayed              | Usually Immediate       |
| Typical Usage   | API Gateway, Security Layer | Compression, Encryption |

---

## Industry Examples

### API Gateway as Proxy

Responsibilities:

* Authentication
* Authorization
* Routing
* Rate Limiting

Examples:

* Kong
* NGINX
* Apache APISIX

### Service Mesh Proxy

Responsibilities:

* Mutual TLS
* Traffic Routing
* Observability

Examples:

* Envoy
* Istio

---

## When to Use

Use Proxy Pattern when:

* Access control is required.
* Authentication is required.
* Logging is required.
* Monitoring is required.
* Expensive objects need lazy initialization.
* Remote service communication exists.
* Caching is required.

---

## When Not to Use

Avoid Proxy Pattern when:

* Direct access is sufficient.
* Performance overhead must be minimized.
* No additional control logic is required.

---

## Conclusion

The Proxy Pattern provides an intermediary object that controls access to another object.

It is widely used in:

* API Gateways
* Banking Platforms
* Payment Systems
* Service Meshes
* Security Layers
* Distributed Systems

The pattern improves:

* Security
* Scalability
* Performance
* Maintainability

while keeping business logic clean and independent from infrastructure concerns.
