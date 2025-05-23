package com.intern.util;

import com.intern.DAO.*;
import com.intern.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Utility for migrating data from JPA repositories to reactive repositories.
 * This is a temporary component to ease the transition to a fully reactive application.
 * It should be removed once the migration is complete.
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test") // Do not run in test profile
public class DataMigrationUtility {

    // JPA repositories
    private final VehicleRepository vehicleRepository;
    private final BillRepository billRepository;
    private final BillItemRepository billItemRepository;
    private final VehicleReservationRepository vehicleReservationRepository;
    private final AccountRepository accountRepository;
    private final ParkingStallRepository parkingStallRepository;
    private final VehicleLogRepository vehicleLogRepository;
    private final CarRentalLocationRepository carRentalLocationRepository;
    private final CarRentalSystemRepository carRentalSystemRepository;
    
    // Reactive repositories
    private final ReactiveVehicleRepository reactiveVehicleRepository;
    private final ReactiveBillRepository reactiveBillRepository;
    private final ReactiveBillItemRepository reactiveBillItemRepository;
    private final ReactiveVehicleReservationRepository reactiveVehicleReservationRepository;
    private final ReactiveAccountRepository reactiveAccountRepository;
    private final ReactiveParkingStallRepository reactiveParkingStallRepository;
    private final ReactiveVehicleLogRepository reactiveVehicleLogRepository;
    private final ReactiveCarRentalLocationRepository reactiveCarRentalLocationRepository;
    private final ReactiveCarRentalSystemRepository reactiveCarRentalSystemRepository;
    
    /**
     * Triggered when the application is ready.
     * Migrates data from JPA repositories to reactive repositories.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void migrateData() {
        log.info("Starting data migration from JPA to R2DBC...");
        
        // Migrate accounts first since other entities may reference them
        Mono<Void> accountMigration = Flux.fromIterable(accountRepository.findAll())
                .flatMap(reactiveAccountRepository::save)
                .then()
                .doOnSuccess(v -> log.info("Account migration completed"));
        
        // Migrate vehicles
        Mono<Void> vehicleMigration = Flux.fromIterable(vehicleRepository.findAll())
                .flatMap(reactiveVehicleRepository::save)
                .then()
                .doOnSuccess(v -> log.info("Vehicle migration completed"));
        
        // Migrate reservations
        Mono<Void> reservationMigration = Flux.fromIterable(vehicleReservationRepository.findAll())
                .flatMap(reactiveVehicleReservationRepository::save)
                .then()
                .doOnSuccess(v -> log.info("Reservation migration completed"));
        
        // Migrate bills
        Mono<Void> billMigration = Flux.fromIterable(billRepository.findAll())
                .flatMap(bill -> {
                    // Convert ID type
                    bill.setId(Long.valueOf(bill.getId()));
                    
                    // Set foreign keys based on relations
                    if (bill.getVehicleReservation() != null) {
                        bill.setReservationId(Long.valueOf(bill.getVehicleReservation().getId()));
                    }
                    
                    // Convert Date to Long
                    if (bill.getCreationDate() != null) {
                        bill.setCreationDate(bill.getCreationDate().getTime());
                    }
                    
                    // Set payment status
                    if (bill.getPaymentStatus() != null) {
                        bill.setStatus(bill.getPaymentStatus().toString());
                    }
                    
                    return reactiveBillRepository.save(bill);
                })
                .then()
                .doOnSuccess(v -> log.info("Bill migration completed"));
        
        // Migrate bill items
        Mono<Void> billItemMigration = Flux.fromIterable(billItemRepository.findAll())
                .flatMap(billItem -> {
                    // Convert ID type
                    billItem.setId(Long.valueOf(billItem.getId()));
                    
                    // Set foreign keys based on relations
                    if (billItem.getBill() != null) {
                        billItem.setBillId(Long.valueOf(billItem.getBill().getId()));
                    }
                    
                    return reactiveBillItemRepository.save(billItem);
                })
                .then()
                .doOnSuccess(v -> log.info("Bill item migration completed"));
        
        // Migrate parking stalls
        Mono<Void> parkingStallMigration = Flux.fromIterable(parkingStallRepository.findAll())
                .flatMap(parkingStall -> {
                    // Convert ID type
                    parkingStall.setId(Long.valueOf(parkingStall.getId()));
                    return reactiveParkingStallRepository.save(parkingStall);
                })
                .then()
                .doOnSuccess(v -> log.info("Parking stall migration completed"));
        
        // Migrate car rental systems
        Mono<Void> carRentalSystemMigration = Flux.fromIterable(carRentalSystemRepository.findAll())
                .flatMap(system -> {
                    // Convert ID type
                    system.setId(Long.valueOf(system.getId()));
                    return reactiveCarRentalSystemRepository.save(system);
                })
                .then()
                .doOnSuccess(v -> log.info("Car rental system migration completed"));
        
        // Migrate car rental locations
        Mono<Void> carRentalLocationMigration = Flux.fromIterable(carRentalLocationRepository.findAll())
                .flatMap(location -> {
                    // Convert ID type
                    location.setId(Long.valueOf(location.getId()));
                    
                    // Set foreign keys based on relations
                    if (location.getCarRentalSystem() != null) {
                        location.setCarRentalSystemId(Long.valueOf(location.getCarRentalSystem().getId()));
                    }
                    
                    // Handle embedded Location
                    if (location.getAddress() != null) {
                        location.setStreetAddress(location.getAddress().getStreetAddress());
                        location.setCity(location.getAddress().getCity());
                        location.setState(location.getAddress().getState());
                        location.setZipcode(location.getAddress().getZipcode());
                        location.setCountry(location.getAddress().getCountry());
                    }
                    
                    return reactiveCarRentalLocationRepository.save(location);
                })
                .then()
                .doOnSuccess(v -> log.info("Car rental location migration completed"));
        
        // Migrate vehicle logs
        Mono<Void> vehicleLogMigration = Flux.fromIterable(vehicleLogRepository.findAll())
                .flatMap(vehicleLog -> {
                    // Convert ID type
                    vehicleLog.setId(Long.valueOf(vehicleLog.getId()));
                    
                    // Convert type enum to string
                    if (vehicleLog.getType() != null) {
                        vehicleLog.setType(vehicleLog.getType().toString());
                    }
                    
                    // Set foreign keys based on relations
                    if (vehicleLog.getVehicle() != null) {
                        vehicleLog.setVehicleId(Long.valueOf(vehicleLog.getVehicle().getId()));
                    }
                    
                    return reactiveVehicleLogRepository.save(vehicleLog);
                })
                .then()
                .doOnSuccess(v -> log.info("Vehicle log migration completed"));
        
        // Execute all migrations sequentially
        carRentalSystemMigration
                .then(carRentalLocationMigration)
                .then(parkingStallMigration)
                .then(accountMigration)
                .then(vehicleMigration)
                .then(vehicleLogMigration)
                .then(reservationMigration)
                .then(billMigration)
                .then(billItemMigration)
                .doOnSuccess(v -> log.info("All data migration completed"))
                .subscribe();
    }
}
