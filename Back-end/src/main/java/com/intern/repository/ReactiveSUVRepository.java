package com.intern.repository;

import com.intern.carRental.primary.vehicletypes.SUV;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Reactive repository for SUV entities.
 * Provides reactive operations for SUV data access.
 */
@Repository
public interface ReactiveSUVRepository extends ReactiveBaseRepository<SUV, Long> {
    
    /**
     * Find SUVs by their type
     *
     * @param type the SUV type to search for
     * @return a Flux emitting SUVs of the specified type
     */
    Flux<SUV> findByType(String type);
}
