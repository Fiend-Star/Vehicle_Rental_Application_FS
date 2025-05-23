package com.intern.repository;

import com.intern.carRental.primary.vehicletypes.Truck;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Reactive repository for Truck entities.
 * Provides reactive operations for Truck data access.
 */
@Repository
public interface ReactiveTruckRepository extends ReactiveBaseRepository<Truck, Long> {
    
    /**
     * Find trucks by their type
     *
     * @param type the truck type to search for
     * @return a Flux emitting trucks of the specified type
     */
    Flux<Truck> findByType(String type);
}
