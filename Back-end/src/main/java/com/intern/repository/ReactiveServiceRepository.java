package com.intern.repository;

import com.intern.carRental.primary.abstrct.Service;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Reactive repository for Service entities.
 * Provides reactive operations for Service data access.
 */
@Repository
public interface ReactiveServiceRepository extends ReactiveBaseRepository<Service, Long> {
    
    /**
     * Find services by their vehicle reservation ID
     *
     * @param vehicleReservationId the vehicle reservation ID to search for
     * @return a Flux emitting services associated with the specified vehicle reservation
     */
    Flux<Service> findByVehicleReservationId(Long vehicleReservationId);
    
    /**
     * Find services by their service ID
     *
     * @param serviceId the service ID to search for
     * @return a Flux emitting services with the specified service ID
     */
    Flux<Service> findByServiceId(String serviceId);
}
