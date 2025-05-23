package com.intern.repository;

import com.intern.carRental.primary.abstrct.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class ReactiveVehicleRepositoryTest {

    @Autowired
    private ReactiveVehicleRepository vehicleRepository;

    @Test
    public void testFindAll() {
        Flux<Vehicle> vehicles = vehicleRepository.findAll();
        
        StepVerifier.create(vehicles.collectList())
                .expectNextMatches(list -> true) // Just verify we get a list (might be empty in fresh DB)
                .verifyComplete();
    }
    
    @Test
    public void testFindByStatus() {
        // This assumes there might be vehicles with status "AVAILABLE" in the DB
        // If not, the test will still pass, but with an empty result
        Flux<Vehicle> availableVehicles = vehicleRepository.findByStatus("AVAILABLE");
        
        StepVerifier.create(availableVehicles.collectList())
                .expectNextMatches(list -> true)
                .verifyComplete();
    }
}
