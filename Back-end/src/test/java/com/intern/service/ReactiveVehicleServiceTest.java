package com.intern.service;

import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.carRental.primary.vehicletypes.Car;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveVehicleRepository;
import com.intern.service.impl.ReactiveVehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReactiveVehicleServiceImpl.
 * Tests the service's behavior in isolation using Mockito.
 */
@ExtendWith(MockitoExtension.class)
class ReactiveVehicleServiceTest {

    @Mock
    private ReactiveVehicleRepository vehicleRepository;

    @InjectMocks
    private ReactiveVehicleServiceImpl vehicleService;

    private Vehicle testVehicle;

    @BeforeEach
    void setUp() {
        // Set up test data
        testVehicle = new Car();
        testVehicle.setId(1L);
        testVehicle.setNumberPlate("TEST123");
        testVehicle.setStockNumber("STOCK123");
        testVehicle.setModel("Test Model");
        testVehicle.setMake("Test Make");
        testVehicle.setYearOfManufacture(2023);
        testVehicle.setPassengerCapacity(5);
        testVehicle.setMileage(1000.0);
    }

    @Test
    @DisplayName("Should save a new vehicle")
    void testAddVehicle() {
        // Given
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(Mono.just(testVehicle));

        // When
        Mono<Vehicle> result = vehicleService.addVehicle(testVehicle);

        // Then
        StepVerifier.create(result)
                .expectNext(testVehicle)
                .verifyComplete();

        verify(vehicleRepository).save(any(Vehicle.class));
    }

    @Test
    @DisplayName("Should find a vehicle by ID")
    void testGetVehicleById() {
        // Given
        when(vehicleRepository.findById(anyLong())).thenReturn(Mono.just(testVehicle));

        // When
        Mono<Vehicle> result = vehicleService.getVehicleById(1L);

        // Then
        StepVerifier.create(result)
                .expectNext(testVehicle)
                .verifyComplete();

        verify(vehicleRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when vehicle not found by ID")
    void testGetVehicleByIdNotFound() {
        // Given
        when(vehicleRepository.findById(anyLong())).thenReturn(Mono.empty());

        // When
        Mono<Vehicle> result = vehicleService.getVehicleById(999L);

        // Then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof ResourceNotFoundException && 
                    throwable.getMessage().contains("not found"))
                .verify();

        verify(vehicleRepository).findById(999L);
    }

    @Test
    @DisplayName("Should find all vehicles")
    void testGetAllVehicles() {
        // Given
        Vehicle vehicle2 = new Car();
        vehicle2.setId(2L);
        vehicle2.setNumberPlate("TEST456");
        
        when(vehicleRepository.findAll()).thenReturn(Flux.just(testVehicle, vehicle2));

        // When
        Flux<Vehicle> result = vehicleService.getAllVehicles();

        // Then
        StepVerifier.create(result)
                .expectNext(testVehicle)
                .expectNext(vehicle2)
                .verifyComplete();

        verify(vehicleRepository).findAll();
    }

    @Test
    @DisplayName("Should find vehicles by model")
    void testSearchByModel() {
        // Given
        when(vehicleRepository.findByModel(anyString())).thenReturn(Flux.just(testVehicle));

        // When
        Flux<Vehicle> result = vehicleService.searchByModel("Test Model");

        // Then
        StepVerifier.create(result)
                .expectNext(testVehicle)
                .verifyComplete();

        verify(vehicleRepository).findByModel("Test Model");
    }

    @Test
    @DisplayName("Should update a vehicle")
    void testUpdateVehicle() {
        // Given
        when(vehicleRepository.findById(anyLong())).thenReturn(Mono.just(testVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(Mono.just(testVehicle));

        // Update some properties
        testVehicle.setModel("Updated Model");

        // When
        Mono<Vehicle> result = vehicleService.updateVehicle(testVehicle);

        // Then
        StepVerifier.create(result)
                .expectNext(testVehicle)
                .verifyComplete();

        verify(vehicleRepository).findById(1L);
        verify(vehicleRepository).save(testVehicle);
    }

    @Test
    @DisplayName("Should delete a vehicle by number plate")
    void testRemoveVehicle() {
        // Given
        when(vehicleRepository.findByNumberPlate(anyString())).thenReturn(Mono.just(testVehicle));
        when(vehicleRepository.delete(any(Vehicle.class))).thenReturn(Mono.empty());

        // When
        Mono<Void> result = vehicleService.removeVehicle("TEST123");

        // Then
        StepVerifier.create(result)
                .verifyComplete();

        verify(vehicleRepository).findByNumberPlate("TEST123");
        verify(vehicleRepository).delete(testVehicle);
    }

    @Test
    @DisplayName("Should throw exception when deleting vehicle that doesn't exist")
    void testRemoveVehicleNotFound() {
        // Given
        when(vehicleRepository.findByNumberPlate(anyString())).thenReturn(Mono.empty());

        // When
        Mono<Void> result = vehicleService.removeVehicle("NONEXISTENT");

        // Then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof ResourceNotFoundException && 
                    throwable.getMessage().contains("not found"))
                .verify();

        verify(vehicleRepository).findByNumberPlate("NONEXISTENT");
        verify(vehicleRepository, never()).delete(any());
    }
}
