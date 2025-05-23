package com.intern.service;

import com.intern.carRental.primary.abstrct.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for reactive operations on Service entities.
 */
public interface ReactiveServiceService extends ReactiveBaseService<Service, Long> {
    
    /**
     * Find services by their vehicle reservation ID
     *
     * @param vehicleReservationId the vehicle reservation ID to search for
     * @return a Flux of services associated with the specified vehicle reservation
     */
    Flux<Service> findByVehicleReservationId(Long vehicleReservationId);
    
    /**
     * Find services by their service ID
     *
     * @param serviceId the service ID to search for
     * @return a Flux of services with the specified service ID
     */
    Flux<Service> findByServiceId(String serviceId);
    
    /**
     * Add a new service
     *
     * @param service the service to add
     * @return a Mono containing the saved service
     */
    Mono<Service> addService(Service service);
    
    /**
     * Update an existing service
     *
     * @param service the service with updated information
     * @return a Mono containing the updated service
     */
    Mono<Service> updateService(Service service);
}
