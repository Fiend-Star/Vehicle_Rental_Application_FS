package com.intern.repository;

import com.intern.carRental.primary.Receptionist;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for Receptionist entities.
 * Provides reactive operations for Receptionist data access.
 */
@Repository
public interface ReactiveReceptionistRepository extends ReactiveBaseRepository<Receptionist, Long> {
    
    /**
     * Find a receptionist by active status
     *
     * @param active the active status to search for
     * @return a Flux emitting receptionists with the specified active status
     */
    Flux<Receptionist> findByActive(boolean active);
}
