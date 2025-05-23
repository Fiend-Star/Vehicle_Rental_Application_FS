package com.intern.service;

import com.intern.carRental.primary.vehicletypes.Truck;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for reactive operations on Truck entities.
 */
public interface ReactiveTruckService extends ReactiveBaseService<Truck, Long> {
    
    /**
     * Find trucks by their type
     *
     * @param type the truck type string to search for
     * @return a Flux of trucks with the specified type
     */
    Flux<Truck> findByType(String type);
    
    /**
     * Add a new truck
     *
     * @param truck the truck to add
     * @return a Mono containing the saved truck
     */
    Mono<Truck> addTruck(Truck truck);
    
    /**
     * Update an existing truck
     *
     * @param truck the truck with updated information
     * @return a Mono containing the updated truck
     */
    Mono<Truck> updateTruck(Truck truck);
}
