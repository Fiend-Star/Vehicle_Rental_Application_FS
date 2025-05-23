package com.intern.repository;

import com.intern.carRental.primary.CarRentalSystem;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for CarRentalSystem entities.
 * Provides reactive operations for CarRentalSystem data access.
 */
@Repository
public interface ReactiveCarRentalSystemRepository extends ReactiveBaseRepository<CarRentalSystem, Long> {
    
    /**
     * Find a car rental system by its name
     *
     * @param name the system name
     * @return a Mono emitting the found system or empty if not found
     */
    Mono<CarRentalSystem> findByName(String name);
}
