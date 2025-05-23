package com.intern.repository;

import com.intern.carRental.primary.vehicletypes.Motorcycle;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Reactive repository for Motorcycle entities.
 * Provides reactive operations for Motorcycle data access.
 */
@Repository
public interface ReactiveMotorcycleRepository extends ReactiveBaseRepository<Motorcycle, Long> {
    
    /**
     * Find motorcycles by their type
     *
     * @param type the motorcycle type to search for
     * @return a Flux emitting motorcycles of the specified type
     */
    Flux<Motorcycle> findByType(String type);
}
