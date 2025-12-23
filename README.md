# CQRS Account Management System

A comprehensive demonstration of the **Command Query Responsibility Segregation (CQRS)** pattern using Spring Boot, Axon Framework, and event sourcing. This project showcases a microservices architecture with separate command and query sides for managing banking accounts.

## 📋 Project Overview

This project consists of two Spring Boot microservices:

1. **Account Service** - Handles all account commands and events
2. **Analytics Service** - Queries account data and provides analytics insights

### Architecture Pattern: CQRS

The CQRS pattern separates read and write operations:

- **Command Side (Write)**: Handles account creation, debits, and credits
- **Query Side (Read)**: Provides account analytics and historical data
- **Event Sourcing**: All state changes are captured as immutable events

---

## 🏗️ Project Structure

```
cqrs_app/
├── accountservice/                    # Main Account Service
│   ├── src/main/java/com/example/accountservice/
│   │   ├── commands/
│   │   │   ├── aggregates/            # Axon Aggregates (AccountAggregate)
│   │   │   ├── cmds/                  # Command definitions (Add, Debit, Credit)
│   │   │   ├── controllers/           # REST endpoints for commands
│   │   │   ├── dto/                   # Data Transfer Objects
│   │   │   ├── enums/                 # AccountStatus, TransactionType
│   │   │   └── events/                # Domain events
│   │   ├── config/                    # OpenAPI/Swagger configuration
│   │   └── query/                     # Query side components
│   ├── build.gradle.kts
│   └── src/main/resources/application.properties
│
├── analyticsservice/                  # Analytics Service
│   ├── src/main/java/com/example/analyticsservice/
│   │   ├── controller/                # REST endpoints for queries
│   │   ├── entities/                  # JPA entities
│   │   ├── queries/                   # Query definitions
│   │   ├── repo/                      # Repository layer
│   │   └── service/                   # Business logic & event handlers
│   ├── build.gradle.kts
│   └── src/main/resources/application.properties
│
└── README.md                          # This file
```

---

## 🔑 Key Components

### Account Service (Command Side)

#### Commands

- **AddAccountCommand**: Creates a new account with initial balance
- **CreditAccountCommand**: Adds funds to an account
- **DebitAccountCommand**: Withdraws funds from an account

#### Events

- **AccountCreatedEvent**: Triggered when account is created
- **AccountCreditedEvent**: Triggered when funds are credited
- **AccountDebitedEvent**: Triggered when funds are debited

#### Aggregate

- **AccountAggregate**: Manages account state transitions and event application
  - Validates business rules (e.g., no negative balances)
  - Applies events to update internal state
  - Handles command routing

#### Enums

- **AccountStatus**: CREATED, ACTIVE, CLOSED
- **TransactionType**: DEBIT, CREDIT

### Analytics Service (Query Side)

#### Entities

- **AccountAnalytics**: Denormalized data for read operations
  - accountId, balance, totalDebit, totalCredit
  - totalNumberOfDebits, totalNumberOfCredits

#### Event Handlers

- **AccountAnalyticsEventHandler**: Listens to events from the command side and updates analytics

#### Queries

- **GetAllAccountAnalytics**: Retrieves all account analytics
- **GetAllAccountAnalyticsByAccountId**: Retrieves analytics for a specific account

---

## 🛠️ Technologies Used

### Framework & Libraries

- **Spring Boot 3.4.1**: Framework for building Java applications
- **Axon Framework 4.10.3**: CQRS and event sourcing framework
- **Spring Data JPA**: ORM for database operations
- **Spring Web**: REST API support
- **SpringDoc OpenAPI 2.6.0**: Swagger/OpenAPI documentation

### Database Support

- **H2**: In-memory database (default)
- **PostgreSQL**: Production database support
- **MySQL**: Alternative database support

### Development Tools

- **Lombok**: Reduces boilerplate code
- **Spring Boot DevTools**: Hot reload support
- **Gradle**: Build automation tool

### Java Version

- **Java 21**: Latest LTS version with enhanced features

---

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Gradle (included via gradlew)
- Git

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/safeone1/cqrs_app.git
   cd cqrs_app
   ```

2. **Build the project**

   ```bash
   ./gradlew clean build
   ```

3. **Run Account Service**

   ```bash
   cd accountservice
   ./gradlew bootRun
   ```

   The service will start on `http://localhost:8080`

4. **Run Analytics Service** (in a new terminal)
   ```bash
   cd accountservice/analyticsservice
   ./gradlew bootRun
   ```
   The service will start on `http://localhost:8081` (configure in application.properties)

---

## 📡 API Endpoints

### Account Service (Command Side)

#### Create Account

```bash
POST /accounts/add
Content-Type: application/json

{
  "id": "ACC001",
  "initialBalance": 1000.0,
  "currency": "USD"
}
```

#### Credit Account

```bash
POST /accounts/credit
Content-Type: application/json

{
  "id": "ACC001",
  "amount": 500.0,
  "currency": "USD"
}
```

#### Debit Account

```bash
POST /accounts/debit
Content-Type: application/json

{
  "id": "ACC001",
  "amount": 200.0,
  "currency": "USD"
}
```

### Analytics Service (Query Side)

#### Get All Account Analytics

```bash
GET /analytics/accounts
```

#### Get Analytics by Account ID

```bash
GET /analytics/accounts/{accountId}
```

---

## 🔄 Event Flow

1. **User sends command** → Account Service REST endpoint
2. **Command handler** → Validates and routes to aggregate
3. **Aggregate processes** → Generates domain events
4. **Events published** → Event bus distributes events
5. **Analytics service listens** → Updates denormalized data
6. **User queries** → Analytics Service provides read-optimized data

---

## 📚 CQRS Benefits in This Project

✅ **Scalability**: Read and write operations can scale independently  
✅ **Performance**: Query side uses denormalized data for fast reads  
✅ **Event Sourcing**: Complete audit trail of all account changes  
✅ **Consistency**: Event-driven eventual consistency model  
✅ **Flexibility**: Easy to add new query projections  
✅ **Testability**: Commands and events are easy to unit test

---

## 🔧 Configuration

### Account Service - application.properties

```properties
spring.application.name=accountservice
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
server.port=8080
```

### Analytics Service - application.properties

```properties
spring.application.name=analyticsservice
spring.datasource.url=jdbc:h2:mem:analyticsdb
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
server.port=8081
```

---

## 📖 API Documentation

Both services provide Swagger/OpenAPI documentation:

- **Account Service**: `http://localhost:8080/swagger-ui.html`
- **Analytics Service**: `http://localhost:8081/swagger-ui.html`

---

## 🧪 Testing

Run all tests:

```bash
./gradlew test
```

Run tests for Account Service:

```bash
cd accountservice
./gradlew test
```

Run tests for Analytics Service:

```bash
cd accountservice/analyticsservice
./gradlew test
```

---

## 📊 Database Schema (H2)

### Account Service

Event store for all domain events

### Analytics Service

```sql
CREATE TABLE account_analytics (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  account_id VARCHAR(255),
  balance DOUBLE,
  total_debit DOUBLE,
  total_credit DOUBLE,
  total_number_of_debits INT,
  total_number_of_credits INT
);
```

---

## 🤝 Architectural Patterns Used

1. **CQRS (Command Query Responsibility Segregation)**

   - Separates read and write models
   - Each model optimized for its specific use case

2. **Event Sourcing**

   - Events are the source of truth
   - Complete audit trail maintained
   - State reconstructed from events

3. **Event-Driven Architecture**

   - Loosely coupled services communicate via events
   - Asynchronous event processing

4. **Repository Pattern**

   - Data access abstraction layer
   - Easy to switch databases

5. **DTO Pattern**

   - Decouples internal models from API contracts
   - Version-safe API evolution

6. **Aggregate Pattern (DDD)**
   - Encapsulates business logic
   - Ensures consistency boundaries

---

## 🐛 Troubleshooting

### Service won't start

- Ensure Java 21 is installed: `java -version`
- Check if ports 8080 and 8081 are available
- Review logs for configuration issues

### H2 Console (optional)

Add to Account Service application.properties:

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Access at: `http://localhost:8080/h2-console`

### Event not reaching Analytics Service

- Verify both services are running
- Check network connectivity between services
- Review service logs for exceptions

---

## 📝 Learning Resources

- [Axon Framework Documentation](https://docs.axoniq.io/)
- [CQRS Pattern](https://martinfowler.com/bliki/CQRS.html)
- [Event Sourcing](https://martinfowler.com/eaaDev/EventSourcing.html)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Domain-Driven Design](https://www.domainlanguage.com/)

---

## 📄 License

This project is provided for educational purposes.

---

## ✉️ Contact & Support

For questions about this implementation, please refer to the course materials or contact the instructor.

---

**Last Updated**: December 2025  
**Java Version**: 21  
**Spring Boot Version**: 3.4.1  
**Axon Framework Version**: 4.10.3
