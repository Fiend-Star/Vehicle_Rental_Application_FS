package com.intern.service;

import com.intern.carRental.primary.vehicletypes.Motorcycle;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for reactive operations on Motorcycle entities.
 */
public interface ReactiveMotorcycleService extends ReactiveBaseService<Motorcycle, Long> {
    
    /**
     * Find motorcycles by their type
     *
     * @param type the motorcycle type string to search for
     * @return a Flux of motorcycles with the specified type
     */
    Flux<Motorcycle> findByType(String type);
    
    /**
     * Add a new motorcycle
     *
     * @param motorcycle the motorcycle to add
     * @return a Mono containing the saved motorcycle
     */
    Mono<Motorcycle> addMotorcycle(Motorcycle motorcycle);
    
    /**
     * Update an existing motorcycle
     *
     * @param motorcycle the motorcycle with updated information
     * @return a Mono containing the updated motorcycle
     */
    Mono<Motorcycle> updateMotorcycle(Motorcycle motorcycle);
}
