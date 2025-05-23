package com.intern.repository;

import com.intern.carRental.primary.vehicletypes.Car;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Reactive repository for Car entities.
 * Provides reactive operations for Car data access.
 */
@Repository
public interface ReactiveCarRepository extends ReactiveBaseRepository<Car, Long> {
    
    /**
     * Find cars by their type
     *
     * @param type the car type to search for
     * @return a Flux emitting cars of the specified type
     */
    Flux<Car> findByType(String type);
}
