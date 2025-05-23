package com.intern.service.impl;

import com.intern.carRental.primary.vehicletypes.Car;
import com.intern.repository.ReactiveCarRepository;
import com.intern.service.ReactiveCarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test class for ReactiveCarServiceImpl.
 */
public class ReactiveCarServiceImplTest {

    @Mock
    private ReactiveCarRepository reactiveCarRepository;

    private ReactiveCarService reactiveCarService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        reactiveCarService = new ReactiveCarServiceImpl(reactiveCarRepository);
    }

    @Test
    public void testAddCar() {
        // Create a test car
        Car car = new Car();
        car.setId(1L);
        car.setNumberPlate("ABC123");
        car.setModel("Test Model");
        car.setMake("Test Make");
        car.setManufacturingYear(2023);
        car.setMileage(0);
        car.setType("ECONOMY");

        when(reactiveCarRepository.save(any(Car.class))).thenReturn(Mono.just(car));

        // Test the service method
        Mono<Car> result = reactiveCarService.addCar(car);

        // Verify the result
        StepVerifier.create(result)
                .expectNextMatches(saved -> 
                    saved.getId().equals(1L) && 
                    saved.getNumberPlate().equals("ABC123") &&
                    saved.getType().equals("ECONOMY"))
                .verifyComplete();
    }

    @Test
    public void testFindByType() {
        // Create test cars
        Car car1 = new Car();
        car1.setId(1L);
        car1.setType("ECONOMY");

        Car car2 = new Car();
        car2.setId(2L);
        car2.setType("ECONOMY");

        when(reactiveCarRepository.findByType("ECONOMY")).thenReturn(Flux.just(car1, car2));

        // Test the service method
        Flux<Car> result = reactiveCarService.findByType("ECONOMY");

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testUpdateCar() {
        // Create a test car
        Car car = new Car();
        car.setId(1L);
        car.setNumberPlate("ABC123");
        car.setModel("Updated Model");
        car.setMake("Updated Make");
        car.setManufacturingYear(2023);
        car.setMileage(100);
        car.setType("PREMIUM");

        when(reactiveCarRepository.findById(1L)).thenReturn(Mono.just(car));
        when(reactiveCarRepository.save(any(Car.class))).thenReturn(Mono.just(car));

        // Test the service method
        Mono<Car> result = reactiveCarService.updateCar(car);

        // Verify the result
        StepVerifier.create(result)
                .expectNextMatches(updated -> 
                    updated.getModel().equals("Updated Model") &&
                    updated.getType().equals("PREMIUM") &&
                    updated.getMileage() == 100)
                .verifyComplete();
    }
}
