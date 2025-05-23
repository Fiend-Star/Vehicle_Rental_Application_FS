package com.intern.service;

import com.intern.carRental.primary.abstrct.Vehicle;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing vehicles in the rental system.
 * Uses reactive types (Mono/Flux) for reactive programming support.
 * Extends the base reactive service interface.
 */
public interface ReactiveVehicleService extends ReactiveBaseService<Vehicle, Long> {
    
    /**
     * Add a new vehicle to the system
     *
     * @param vehicle the vehicle to add
     * @return a Mono emitting the saved vehicle
     */
    Mono<Vehicle> addVehicle(Vehicle vehicle);
    
    /**
     * Update an existing vehicle
     *
     * @param vehicle the vehicle with updated properties
     * @return a Mono emitting the updated vehicle
     */
    Mono<Vehicle> updateVehicle(Vehicle vehicle);
    
    /**
     * Remove a vehicle from the system
     *
     * @param numberPlate the number plate of the vehicle to remove
     * @return a Mono completing when the vehicle is removed
     */
    Mono<Void> removeVehicle(String numberPlate);
    
    /**
     * Search for vehicles by model
     *
     * @param model the model to search for
     * @return a Flux emitting the matching vehicles
     */
    Flux<Vehicle> searchByModel(String model);
    
    /**
     * Search for vehicles by make
     *
     * @param make the make to search for
     * @return a Flux emitting the matching vehicles
     */
    Flux<Vehicle> searchByMake(String make);
    
    /**
     * Get all vehicles in the system
     *
     * @return a Flux emitting all vehicles
     */
    Flux<Vehicle> getAllVehicles();
    
    /**
     * Get a vehicle by its ID
     *
     * @param id the ID of the vehicle
     * @return a Mono emitting the vehicle or empty if not found
     */
    Mono<Vehicle> getVehicleById(Long id);
    
    /**
     * Get a vehicle by its number plate
     *
     * @param numberPlate the number plate of the vehicle
     * @return a Mono emitting the vehicle or empty if not found
     */
    Mono<Vehicle> getVehicleByNumberPlate(String numberPlate);
}
