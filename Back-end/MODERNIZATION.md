# Vehicle Rental Application - Modernized Backend

This project has been refactored to follow proper coding practices, design patterns, SOLID principles, and modern Spring features.

## Key Improvements

### Architecture and Design Patterns

1. **SOLID Principles**:
   - **Single Responsibility Principle (SRP)**: Each class has one responsibility (e.g., controllers, services, repositories)
   - **Open/Closed Principle (OCP)**: Used inheritance and interfaces for extension without modification
   - **Liskov Substitution Principle (LSP)**: Proper use of inheritance hierarchies
   - **Interface Segregation Principle (ISP)**: Specific interfaces rather than general ones
   - **Dependency Inversion Principle (DIP)**: Dependencies on abstractions, not concretions

2. **Design Patterns**:
   - **Factory Pattern**: For creating vehicle instances
   - **Strategy Pattern**: For different vehicle types
   - **Repository Pattern**: For data access
   - **Dependency Injection**: Via Spring's IoC container

3. **Clean Architecture**:
   - Layered architecture with clear separation of concerns
   - Controller → Service → Repository flow
   - Domain models independent of frameworks

### Technical Improvements

1. **Java 21 Upgrade**:
   - Updated from Java 8 to Java 21
   - Configured Maven compiler plugin accordingly

2. **Spring WebFlux and Reactive Programming**:
   - Reactive controllers, services, and repositories
   - Non-blocking I/O with Reactor (Mono/Flux)
   - Enhanced concurrency and scalability

3. **Spring AOP**:
   - Logging aspect for cross-cutting concerns
   - Performance monitoring aspect for method execution times
   - Clear separation of business logic from cross-cutting concerns

4. **Testing Improvements**:
   - Unit tests with JUnit 5 and Mockito
   - Reactive testing with StepVerifier
   - Integration tests with SpringBootTest
   - JaCoCo for test coverage reporting

5. **Error Handling**:
   - Global exception handlers for standardized API responses
   - Reactive exception handlers for WebFlux endpoints
   - Clear error hierarchies and messages

6. **API Design**:
   - RESTful API best practices
   - Consistent response formats
   - Proper HTTP status code usage

7. **Documentation**:
   - Comprehensive JavaDoc comments
   - Clear class and method descriptions
   - Consistent code style and formatting

## Build and Test

To build the project with JaCoCo coverage:

```bash
mvn clean verify
```

To view the JaCoCo coverage report, open:
```
target/site/jacoco/index.html
```

## Key Classes and Components

1. **Base Interfaces**:
   - `ReactiveBaseService` - Generic service interface
   - `ReactiveBaseRepository` - Generic repository interface
   - `ReactiveBaseController` - Generic controller base class

2. **AOP Components**:
   - `LoggingAspect` - For logging method entry/exit and exceptions
   - `PerformanceMonitoringAspect` - For monitoring method execution times

3. **Exception Handling**:
   - `GlobalExceptionHandler` - For standard REST endpoints
   - `GlobalErrorWebExceptionHandler` - For reactive endpoints

4. **Configuration**:
   - `WebFluxConfig` - For WebFlux and CORS configuration
   - `AopConfig` - For AOP configuration
