package com.intern.integration;

import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.carRental.primary.vehicletypes.Car;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveVehicleRepository;
import com.intern.service.ReactiveVehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Integration tests for ReactiveVehicleService.
 */
@SpringBootTest
@ActiveProfiles("test")
class ReactiveVehicleServiceIntegrationTest {

    @Autowired
    private ReactiveVehicleService vehicleService;

    @Autowired
    private ReactiveVehicleRepository vehicleRepository;

    private Vehicle testVehicle;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        vehicleRepository.deleteAll().block();

        // Set up test data
        testVehicle = new Car();
        testVehicle.setNumberPlate("TEST-INT-123");
        testVehicle.setStockNumber("STOCK-INT-123");
        testVehicle.setModel("Test Integration Model");
        testVehicle.setMake("Test Integration Make");
        testVehicle.setYearOfManufacture(2023);
        testVehicle.setPassengerCapacity(5);
        testVehicle.setMileage(1000.0);

        // Save the test vehicle
        vehicleService.save(testVehicle).block();
    }

    @Test
    @DisplayName("Should find a vehicle by its number plate")
    void testFindByNumberPlate() {
        // When
        Mono<Vehicle> result = vehicleService.getVehicleByNumberPlate("TEST-INT-123");

        // Then
        StepVerifier.create(result)
                .expectNextMatches(vehicle -> 
                    "TEST-INT-123".equals(vehicle.getNumberPlate()) &&
                    "Test Integration Model".equals(vehicle.getModel()) &&
                    "Test Integration Make".equals(vehicle.getMake())
                )
                .verifyComplete();
    }

    @Test
    @DisplayName("Should update a vehicle")
    void testUpdateVehicle() {
        // Given
        Mono<Vehicle> vehicleMono = vehicleService.getVehicleByNumberPlate("TEST-INT-123");
        
        // When
        Vehicle updatedVehicle = vehicleMono.block();
        updatedVehicle.setModel("Updated Model");
        Mono<Vehicle> result = vehicleService.updateVehicle(updatedVehicle);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(vehicle -> 
                    "TEST-INT-123".equals(vehicle.getNumberPlate()) &&
                    "Updated Model".equals(vehicle.getModel())
                )
                .verifyComplete();
    }

    @Test
    @DisplayName("Should throw exception when vehicle not found by number plate")
    void testVehicleNotFoundByNumberPlate() {
        // When
        Mono<Vehicle> result = vehicleService.getVehicleByNumberPlate("NONEXISTENT");

        // Then
        StepVerifier.create(result)
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("Should add and remove a vehicle")
    void testAddAndRemoveVehicle() {
        // Given
        Vehicle newVehicle = new Car();
        newVehicle.setNumberPlate("TEST-ADD-123");
        newVehicle.setStockNumber("STOCK-ADD-123");
        newVehicle.setModel("Add Test Model");
        newVehicle.setMake("Add Test Make");
        newVehicle.setYearOfManufacture(2022);
        newVehicle.setPassengerCapacity(4);
        newVehicle.setMileage(500.0);

        // When - Add
        vehicleService.addVehicle(newVehicle).block();
        
        // Then - Verify added
        StepVerifier.create(vehicleService.getVehicleByNumberPlate("TEST-ADD-123"))
                .expectNextMatches(vehicle -> "TEST-ADD-123".equals(vehicle.getNumberPlate()))
                .verifyComplete();
        
        // When - Remove
        Mono<Void> removeResult = vehicleService.removeVehicle("TEST-ADD-123");
        
        // Then - Verify removed
        StepVerifier.create(removeResult)
                .verifyComplete();
                
        StepVerifier.create(vehicleService.getVehicleByNumberPlate("TEST-ADD-123"))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
}
