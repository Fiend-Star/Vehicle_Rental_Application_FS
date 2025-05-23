package com.intern.repository;

import com.intern.primary.addonServices.Driver;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Reactive repository for Driver entities.
 * Provides reactive operations for Driver data access.
 */
@Repository
public interface ReactiveDriverRepository extends ReactiveBaseRepository<Driver, Long> {
    
    /**
     * Find drivers by their vehicle reservation ID
     *
     * @param vehicleReservationId the vehicle reservation ID to search for
     * @return a Flux emitting drivers associated with the specified vehicle reservation
     */
    Flux<Driver> findByVehicleReservationId(Long vehicleReservationId);
}
