package com.intern.service;

import com.intern.carRental.primary.ParkingStall;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing parking stalls in the rental system.
 * Uses reactive types (Mono/Flux) for reactive programming support.
 * Extends the base reactive service interface.
 */
public interface ReactiveParkingStallService extends ReactiveBaseService<ParkingStall, Long> {
    
    /**
     * Create a new parking stall
     *
     * @param parkingStall the parking stall to create
     * @return a Mono emitting the created parking stall
     */
    Mono<ParkingStall> createParkingStall(ParkingStall parkingStall);
    
    /**
     * Update an existing parking stall
     *
     * @param parkingStall the parking stall with updated properties
     * @return a Mono emitting the updated parking stall
     */
    Mono<ParkingStall> updateParkingStall(ParkingStall parkingStall);
    
    /**
     * Delete a parking stall
     *
     * @param id the ID of the parking stall to delete
     * @return a Mono completing when the parking stall is deleted
     */
    Mono<Void> deleteParkingStall(Long id);
    
    /**
     * Find a parking stall by stall number
     *
     * @param stallNumber the stall number to search for
     * @return a Mono emitting the found parking stall or empty if not found
     */
    Mono<ParkingStall> findByStallNumber(String stallNumber);
    
    /**
     * Find parking stalls by location identifier
     *
     * @param locationIdentifier the location identifier to search for
     * @return a Flux emitting matching parking stalls
     */
    Flux<ParkingStall> findByLocationIdentifier(String locationIdentifier);
}
