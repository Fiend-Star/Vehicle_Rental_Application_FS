package com.intern.repository;

import com.intern.carRental.primary.vehicletypes.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Test class for ReactiveCarRepository.
 */
@SpringBootTest
@ActiveProfiles("test")
public class ReactiveCarRepositoryTest {

    @Autowired
    private ReactiveCarRepository reactiveCarRepository;

    @Test
    public void testSaveAndFindCar() {
        // Create and save a car
        Car car = new Car();
        car.setNumberPlate("TEST123");
        car.setModel("Test Model");
        car.setMake("Test Make");
        car.setManufacturingYear(2022);
        car.setMileage(0);
        car.setPassengerCapacity(4);
        car.setHasSunroof(true);
        car.setType("LUXURY");
        car.setStatusEnum(com.intern.primary.enums.VehicleStatus.Available);

        // Save the car
        Mono<Car> savedCarMono = reactiveCarRepository.save(car);

        // Test finding all cars
        StepVerifier.create(savedCarMono.flatMapMany(savedCar -> reactiveCarRepository.findAll()))
                .expectNextMatches(foundCar -> foundCar.getNumberPlate() != null)
                .thenCancel()
                .verify();

        // Test finding by ID
        StepVerifier.create(savedCarMono.flatMap(savedCar -> 
                reactiveCarRepository.findById(savedCar.getId())))
                .expectNextMatches(foundCar -> 
                    foundCar.getNumberPlate().equals("TEST123") &&
                    foundCar.getModel().equals("Test Model"))
                .verifyComplete();

        // Test finding by type
        StepVerifier.create(savedCarMono.flatMapMany(savedCar -> 
                reactiveCarRepository.findByType("LUXURY")))
                .expectNextMatches(foundCar -> foundCar.getType().equals("LUXURY"))
                .thenCancel()
                .verify();

        // Clean up - delete the car
        StepVerifier.create(savedCarMono.flatMap(savedCar -> 
                reactiveCarRepository.deleteById(savedCar.getId())))
                .verifyComplete();
    }
}
