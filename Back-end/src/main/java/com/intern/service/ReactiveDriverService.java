package com.intern.service;

import com.intern.primary.addonServices.Driver;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for reactive operations on Driver entities.
 */
public interface ReactiveDriverService extends ReactiveBaseService<Driver, Long> {
    
    /**
     * Find drivers by their vehicle reservation ID
     *
     * @param vehicleReservationId the vehicle reservation ID to search for
     * @return a Flux of drivers associated with the specified vehicle reservation
     */
    Flux<Driver> findByVehicleReservationId(Long vehicleReservationId);
    
    /**
     * Add a new driver
     *
     * @param driver the driver to add
     * @return a Mono containing the saved driver
     */
    Mono<Driver> addDriver(Driver driver);
    
    /**
     * Update an existing driver
     *
     * @param driver the driver with updated information
     * @return a Mono containing the updated driver
     */
    Mono<Driver> updateDriver(Driver driver);
}
