# Observer Pattern + EventBus + Publisher-Subscriber

## Overview

This project demonstrates the implementation of the Observer Pattern using an EventBus architecture in Java.

The goal is to decouple publishers from subscribers and provide a scalable event-driven communication mechanism within an application.

The implementation is lightweight, thread-safe, extensible, and serves as a foundation for building enterprise-grade event-driven systems.

---

# Table of Contents

1. Introduction
2. Problem Statement
3. Architecture
4. Design Patterns Used
5. Components
6. Execution Flow
7. Sequence Diagram
8. Class Diagram
9. Code Walkthrough
10. Running the Project
11. Sample Output
12. Advantages
13. Limitations
14. Enterprise Enhancements
15. Real World Banking Use Cases
16. Future Improvements

---

# Introduction

Modern applications often require multiple modules to react when an event occurs.

Examples:

- User Registration
- Payment Success
- Account Creation
- Fund Transfer
- Order Placement

Without a proper event-driven mechanism, services become tightly coupled and difficult to maintain.

The Observer Pattern solves this problem by allowing multiple subscribers to react to an event without the publisher knowing about them.

---

# Problem Statement

Assume a payment is successful.

Multiple actions must happen:

1. Send SMS
2. Send Email
3. Create Audit Record
4. Update Dashboard
5. Notify Fraud System

A naive implementation might look like:

```java
paymentService.process();

smsService.sendSms();

emailService.sendEmail();

auditService.saveAudit();

dashboardService.update();

fraudService.notify();
```

Problems:

- Tight coupling
- Difficult to add new services
- Difficult testing
- Difficult maintenance
- Reduced scalability

---

# Solution

Introduce an EventBus.

Publisher publishes an event.

Subscribers register for events.

EventBus routes the event to interested subscribers.

```text
                    Payment Service
                           |
                           |
                           v
                    PaymentSuccessEvent
                           |
                           v
                       EventBus
                           |
     ------------------------------------------------
     |                 |               |            |
     v                 v               v            v
 SMS Service     Email Service    Audit Service  Dashboard
```

Publisher never directly calls subscribers.

---

# Architecture

## High-Level Architecture

```text
+--------------------+
|    Publisher       |
+--------------------+
          |
          |
          v
+--------------------+
|      EventBus      |
+--------------------+
      |      |      |
      |      |      |
      v      v      v
+------+ +------+ +------+
| SMS  | |Email | |Audit |
+------+ +------+ +------+
```

---

# Design Patterns Used

## 1. Observer Pattern

One-to-many dependency.

```text
Publisher
   |
   v
Observers
```

Whenever the publisher changes state, all observers are notified.

---

## 2. Publisher-Subscriber Pattern

Publisher and subscribers do not know each other.

```text
Publisher
    |
 Message Broker
    |
Subscribers
```

EventBus acts as an in-memory broker.

---

## 3. Dependency Inversion Principle

Publisher depends on abstraction.

```java
EventListener<T>
```

instead of concrete implementations.

---

# Components

## Event

Represents something that happened.

Examples:

```java
PaymentSuccessEvent
UserCreatedEvent
AccountOpenedEvent
FundTransferEvent
```

Base Interface:

```java
public interface Event {
}
```

---

## EventListener

Responsible for consuming events.

```java
public interface EventListener<T extends Event> {
    void onEvent(T event);
}
```

Every subscriber implements this interface.

Example:

```java
public class SmsService
implements EventListener<PaymentSuccessEvent>
```

---

## EventBus

Core component.

Responsibilities:

- Register listeners
- Manage subscriptions
- Publish events
- Notify listeners

Methods:

```java
subscribe()
publish()
```

---

## Publisher

Generates events.

Example:

```java
Payment Service
```

```java
eventBus.publish(
    new PaymentSuccessEvent(...)
);
```

---

## Subscribers

Consumers of events.

Examples:

### SMS Service

```java
Send SMS
```

### Email Service

```java
Send Email
```

### Audit Service

```java
Store Audit Log
```

---

# Execution Flow

## Step 1

Application starts.

```java
EventBus eventBus = new EventBus();
```

---

## Step 2

Subscribers register.

```java
eventBus.subscribe(
    PaymentSuccessEvent.class,
    new SmsService());
```

```java
eventBus.subscribe(
    PaymentSuccessEvent.class,
    new EmailService());
```

```java
eventBus.subscribe(
    PaymentSuccessEvent.class,
    new AuditService());
```

---

## Step 3

Payment occurs.

```java
PaymentSuccessEvent event =
        new PaymentSuccessEvent(
                "TXN123",
                5000);
```

---

## Step 4

Publisher publishes event.

```java
eventBus.publish(event);
```

---

## Step 5

EventBus finds subscribers.

```java
listeners.get(event.getClass())
```

---

## Step 6

Subscribers are notified.

```java
listener.onEvent(event);
```

---

## Sequence Diagram

```text
Publisher           EventBus          SMS        Email      Audit
    |                  |               |           |          |
    | publish(event)   |               |           |          |
    |----------------->|               |           |          |
    |                  | notify()      |           |          |
    |                  |-------------> |           |          |
    |                  |               |           |          |
    |                  | notify()      |           |          |
    |                  |-------------------------->|          |
    |                  |               |           |          |
    |                  | notify()      |           |          |
    |                  |------------------------------------->|
```

---

# Class Diagram

```text
                    +----------------+
                    |     Event      |
                    +----------------+
                            ^
                            |
            +-----------------------------+
            | PaymentSuccessEvent         |
            +-----------------------------+

                    +----------------------+
                    | EventListener<T>     |
                    +----------------------+
                             ^
            ----------------------------------------
            |                |                    |
            |                |                    |
            v                v                    v

      SmsService      EmailService        AuditService

                     +----------------+
                     |    EventBus    |
                     +----------------+
                     | subscribe()    |
                     | publish()      |
                     +----------------+
```

---

# Thread Safety

The EventBus implementation uses:

```java
ConcurrentHashMap
```

and

```java
CopyOnWriteArrayList
```

Benefits:

- Safe concurrent reads
- Safe subscriber registration
- Avoids ConcurrentModificationException

---

# Running the Project

## Prerequisites

- Java 17+
- Maven 3.8+

---

## Compile

```bash
mvn clean compile
```

---

## Run

```bash
mvn exec:java
```

or

```bash
java Main
```

---

# Sample Output

```text
[SMS] Transaction Successful : TXN123456

[EMAIL] Receipt Sent : TXN123456

[AUDIT] Logged : TXN123456
```

---

# Advantages

## Loose Coupling

Publisher does not know subscribers.

---

## Easy Extension

Add new subscriber without modifying publisher.

Example:

```java
FraudDetectionService
```

No publisher code changes.

---

## Better Maintainability

Responsibilities are separated.

---

## Reusability

Same EventBus can support multiple events.

---

## Scalability

Can evolve into Kafka architecture.

---

# Limitations

## In-Memory Only

Events are lost after application restart.

---

## No Retry

Failed subscriber execution is not retried.

---

## No Persistence

Events are not stored.

---

## Same Process Only

Cannot communicate across applications.

---

# Enterprise Enhancements

## Async Processing

Instead of:

```java
listener.onEvent(event);
```

Use:

```java
executor.submit(
    () -> listener.onEvent(event));
```

Benefits:

- Non-blocking
- Higher throughput

---

## Retry Mechanism

```text
Failure
   |
 Retry
   |
 Success
```

---

## Dead Letter Queue

Failed events are stored.

```text
Event
  |
 Failure
  |
 DLQ
```

---

## Event Persistence

Store events in database.

```text
Event
  |
 Database
  |
 Consumers
```

---

## Kafka Integration

Replace EventBus:

```text
Publisher
    |
    v
 Kafka
    |
 Consumers
```

---

# Real World Banking Use Cases

## UPI Transaction

Publisher:

```text
Transaction Service
```

Subscribers:

```text
SMS Service
Email Service
Notification Service
Fraud Engine
Audit Service
Reporting Service
```

---

## AEPS Cash Withdrawal

Event:

```text
CashWithdrawalSuccess
```

Subscribers:

```text
Ledger Service
SMS Service
Audit Service
Settlement Service
```

---

## Account Opening

Event:

```text
AccountCreated
```

Subscribers:

```text
CRM
Notification
KYC Verification
Welcome Email
```

---

# Future Improvements

- Event Prioritization
- Event Filtering
- Event Replay
- Distributed EventBus
- Kafka Integration
- RabbitMQ Integration
- Event Sourcing
- CQRS
- Outbox Pattern
- Saga Pattern

---

# Conclusion

This project demonstrates a clean implementation of the Observer Pattern using an EventBus architecture.

The design provides:

- Loose coupling
- High maintainability
- Extensibility
- Thread safety

and serves as a foundation for building enterprise-grade event-driven systems used in banking, fintech, e-commerce, and large-scale microservice environments.