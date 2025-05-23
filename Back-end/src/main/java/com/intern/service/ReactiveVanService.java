package com.intern.service;

import com.intern.carRental.primary.vehicletypes.Van;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for reactive operations on Van entities.
 */
public interface ReactiveVanService extends ReactiveBaseService<Van, Long> {
    
    /**
     * Find vans by their type
     *
     * @param type the van type string to search for
     * @return a Flux of vans with the specified type
     */
    Flux<Van> findByType(String type);
    
    /**
     * Add a new van
     *
     * @param van the van to add
     * @return a Mono containing the saved van
     */
    Mono<Van> addVan(Van van);
    
    /**
     * Update an existing van
     *
     * @param van the van with updated information
     * @return a Mono containing the updated van
     */
    Mono<Van> updateVan(Van van);
}
