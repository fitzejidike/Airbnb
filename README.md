# 🏡 Airbnb-like Property Management System – Java Spring Boot

A modular, scalable, and production-ready backend system for managing properties, bookings, and users — inspired by Airbnb. Built using Java (Spring Boot), PostgreSQL, Cloudinary, Kafka, and Docker, with a strong focus on clean architecture, testing, and ADHD-informed system design.

---

## 🚀 Features

### ✅ Property Management
- Create, update, delete, and view properties
- Upload images securely via Cloudinary
- Role-based access (hosts vs. guests)
- Availability tracking per property

### ✅ Booking System
- Book and cancel reservations
- Host and guest logic separation
- Prevent double-bookings and enforce availability

### ✅ User & Auth Module
- Register, update profile, and manage account settings
- JWT-based authentication
- Role-based authorization (guest, host, admin)
  
### ✅ Payments (via Paystack)

- Initiate payments for bookings using Paystack APIs
- Verify transactions securely
- Transfers support (e.g., payout to hosts)
- Resilience features with Resilience4j:
- @Retry for automatic retries when Paystack APIs fail
- @CircuitBreaker for graceful fallbacks if the API is down
- Fallback Responses return user-friendly messages when Paystack cannot be reached
- 
### ✅ Messaging & Notification (Upcoming)
- Email notifications for bookings (SMTP/JavaMailSender)
- Kafka pipeline for asynchronous event handling

---

## 🧠 System Architecture

- **Tech Stack:** Java, Spring Boot, PostgreSQL, Cloudinary, Kafka, Docker
- **Design Patterns:** Clean Architecture, DTO Mapping, Validation Layers
- **Testing:** JUnit, Mockito, Integration Testing
- **Microservices:** Modular design to support separation of concerns (User, Property, Booking, Messaging)

---

## 🧑‍💻 ADHD-Informed Developer Principles

This system is designed and maintained using ADHD-informed engineering strategies:
- **Deep work segments**: Code in focused modules
- **Pattern recognition:** Reusable patterns across services
- **Reduced cognitive load:** Clean separation of concerns and controller logic

---

## 🧪 Tests

This application is rigorously tested to ensure high reliability and maintainability:

- ✅ **Unit Tests** – written using JUnit and Mockito to validate individual service and repository layers.
- ✅ **Integration Tests** – simulate end-to-end scenarios using `@SpringBootTest` and `Testcontainers` (optional).
- ✅ **Maven Test Coverage** – tracked with `JaCoCo` plugin (target: 90%+ line coverage).
- ✅ **Testable Architecture** – clean separation of concerns makes mocking and testing straightforward.

To run tests locally:

```bash
mvn test

