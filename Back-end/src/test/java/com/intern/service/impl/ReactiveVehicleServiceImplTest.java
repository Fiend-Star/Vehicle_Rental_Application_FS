package com.intern.service.impl;

import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.primary.enums.VehicleStatus;
import com.intern.repository.ReactiveVehicleRepository;
import com.intern.service.ReactiveVehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ReactiveVehicleServiceImplTest {

    @Mock
    private ReactiveVehicleRepository vehicleRepository;

    @InjectMocks
    private ReactiveVehicleServiceImpl vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        // Mock repository behavior
        when(vehicleRepository.findAll()).thenReturn(Flux.empty());

        // Test
        Flux<Vehicle> result = vehicleService.findAll();

        // Verify
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void findById() {
        // Mock repository behavior
        when(vehicleRepository.findById(anyLong())).thenReturn(Mono.empty());

        // Test
        Mono<Vehicle> result = vehicleService.findById(1L);

        // Verify
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void findAvailableVehicles() {
        // Mock repository behavior
        when(vehicleRepository.findByStatus(any())).thenReturn(Flux.empty());

        // Test
        Flux<Vehicle> result = vehicleService.findAvailableVehicles();

        // Verify
        StepVerifier.create(result)
                .verifyComplete();
    }
}
