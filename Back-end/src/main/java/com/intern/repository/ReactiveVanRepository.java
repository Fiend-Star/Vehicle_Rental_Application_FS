package com.intern.repository;

import com.intern.carRental.primary.vehicletypes.Van;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Reactive repository for Van entities.
 * Provides reactive operations for Van data access.
 */
@Repository
public interface ReactiveVanRepository extends ReactiveBaseRepository<Van, Long> {
    
    /**
     * Find vans by their type
     *
     * @param type the van type to search for
     * @return a Flux emitting vans of the specified type
     */
    Flux<Van> findByType(String type);
}
