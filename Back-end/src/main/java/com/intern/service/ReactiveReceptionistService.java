package com.intern.service;

import com.intern.carRental.primary.Receptionist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for Receptionist operations.
 * Provides reactive operations for managing Receptionists.
 */
public interface ReactiveReceptionistService extends ReactiveBaseService<Receptionist, Long> {
    
    /**
     * Find receptionists by active status
     *
     * @param active the active status to search for
     * @return a Flux emitting receptionists with the specified active status
     */
    Flux<Receptionist> findByActiveStatus(boolean active);
    
    /**
     * Create a new receptionist
     *
     * @param receptionist the receptionist to create
     * @return a Mono emitting the created receptionist
     */
    Mono<Receptionist> createReceptionist(Receptionist receptionist);
    
    /**
     * Update an existing receptionist
     *
     * @param receptionist the receptionist with updated information
     * @return a Mono emitting the updated receptionist
     */
    Mono<Receptionist> updateReceptionist(Receptionist receptionist);
}
