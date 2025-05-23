package com.intern.repository;

import com.intern.carRental.primary.CarRentalLocation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for CarRentalLocation entities.
 * Provides reactive operations for CarRentalLocation data access.
 */
@Repository
public interface ReactiveCarRentalLocationRepository extends ReactiveBaseRepository<CarRentalLocation, Long> {
    
    /**
     * Find a rental location by its name
     *
     * @param name the location name
     * @return a Mono emitting the found location or empty if not found
     */
    Mono<CarRentalLocation> findByName(String name);
}
