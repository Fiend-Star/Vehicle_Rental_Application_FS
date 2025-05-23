# R2DBC Migration Plan

## Current Issues and Tasks

### 1. Repository Classes to Update
The following repositories need to be converted from `JpaRepository` to `ReactiveCrudRepository`:

- VehicleLogRepository
- BarcodeReaderRepository
- CarRentalLocationRepository
- CarRentalSystemRepository
- ParkingStallRepository
- BillItemRepository
- MemberRepository
- ReceptionistRepository
- AdditionalDriverRepository
- RentalInsuranceRepository
- NotificationRepository
- VehicleReservationRepository
- BillRepository
- CarRepository
- PaymentRepository
- ServiceRepository
- EquipmentRepository
- BarcodeRepository

### 2. Entity Classes to Update
These entity classes need to be updated to use R2DBC annotations:

- BarcodeReader
- Barcode
- EmailNotification
- Additional entities referenced in the compilation errors

### 3. Service Classes to Update
The following service classes need to be updated to replace `@Transactional` from javax with Spring's reactive equivalent:

- VehicleServiceImpl
- AccountServiceImpl
- VehicleReservationImpl

### 4. Security Components to Create
The following JWT and security components need to be created for WebFlux:

- JwtService (for ReactiveAuthenticationFilter and ReactiveSecurityContextRepository)
- UserDetailsImpl (for ReactiveUserDetailsService)
- Fix ReactiveAccountServiceImpl to work with Account class
- Fix validation issues in controllers

### 5. Fix Account Class References
The Account class is being referenced in several places but seems to be causing issues:

- ReactiveUserDetailsServiceImpl
- ReactiveAuthController
- ReactiveAccountServiceImpl

### 6. Fix Import Validation Issues
Fix javax.validation imports with jakarta.validation or Spring's reactive validation:

- SmsRequest
- AuthController

## Prioritized Strategy

1. First fix the Account class references
2. Create essential security components (JwtService, UserDetailsImpl)
3. Update repository classes in order of dependency
4. Update entity classes to use R2DBC annotations
5. Fix service classes to use reactive transactions
6. Update validation components

## Conclusion
This project will require significant refactoring to properly migrate from JPA to R2DBC. The strategy should be to focus on the core Account and security components first, then work through the repositories methodically, followed by entities and services.
