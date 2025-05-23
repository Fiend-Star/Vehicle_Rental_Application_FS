package com.intern.repository;

import com.intern.carRental.primary.abstrct.Vehicle;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for Vehicle entities.
 * Provides reactive operations for Vehicle data access.
 */
@Repository
public interface ReactiveVehicleRepository extends ReactiveBaseRepository<Vehicle, Long> {
    
    /**
     * Find a vehicle by its number plate
     *
     * @param numberPlate the vehicle's number plate
     * @return a Mono emitting the found vehicle or empty if not found
     */
    Mono<Vehicle> findByNumberPlate(String numberPlate);
    
    /**
     * Find a vehicle by its stock number
     *
     * @param stockNumber the vehicle's stock number
     * @return a Mono emitting the found vehicle or empty if not found
     */
    Mono<Vehicle> findByStockNumber(String stockNumber);
    
    /**
     * Find vehicles by their model
     *
     * @param model the vehicle model to search for
     * @return a Flux emitting vehicles matching the model
     */
    Flux<Vehicle> findByModel(String model);
    
    /**
     * Find vehicles by their make
     *
     * @param make the vehicle make to search for
     * @return a Flux emitting vehicles matching the make
     */
    Flux<Vehicle> findByMake(String make);
    
    /**
     * Find vehicles by their type
     *
     * @param type the vehicle type to search for
     * @return a Flux emitting vehicles of the specified type
     */
    Flux<Vehicle> findByType(String type);
}
