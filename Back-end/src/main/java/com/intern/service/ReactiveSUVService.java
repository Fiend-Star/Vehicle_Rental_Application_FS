package com.intern.service;

import com.intern.carRental.primary.vehicletypes.SUV;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for reactive operations on SUV entities.
 */
public interface ReactiveSUVService extends ReactiveBaseService<SUV, Long> {
    
    /**
     * Find SUVs by their type
     *
     * @param type the SUV type string to search for
     * @return a Flux of SUVs with the specified type
     */
    Flux<SUV> findByType(String type);
    
    /**
     * Add a new SUV
     *
     * @param suv the SUV to add
     * @return a Mono containing the saved SUV
     */
    Mono<SUV> addSUV(SUV suv);
    
    /**
     * Update an existing SUV
     *
     * @param suv the SUV with updated information
     * @return a Mono containing the updated SUV
     */
    Mono<SUV> updateSUV(SUV suv);
}
