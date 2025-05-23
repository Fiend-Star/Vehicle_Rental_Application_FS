package com.intern.service.impl;

import com.intern.carRental.primary.abstrct.Service;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveServiceRepository;
import com.intern.service.ReactiveServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveServiceService interface.
 * Note: This service handles abstract Service entities. In practice,
 * concrete service types (like Driver) should be used for full functionality.
 */
@org.springframework.stereotype.Service
@Slf4j
@Transactional
public class ReactiveServiceServiceImpl extends ReactiveBaseServiceImpl<Service, Long, ReactiveServiceRepository>
        implements ReactiveServiceService {

    /**
     * Constructor with repository dependency
     *
     * @param reactiveServiceRepository the reactive service repository
     */
    public ReactiveServiceServiceImpl(ReactiveServiceRepository reactiveServiceRepository) {
        super(reactiveServiceRepository);
    }

    @Override
    protected Class<Service> getEntityClass() {
        return Service.class;
    }

    @Override
    protected String getEntityName() {
        return "Service";
    }

    @Override
    public Flux<Service> findByVehicleReservationId(Long vehicleReservationId) {
        log.debug("Finding services by vehicle reservation ID: {}", vehicleReservationId);
        return reactiveRepository.findByVehicleReservationId(vehicleReservationId);
    }

    @Override
    public Flux<Service> findByServiceId(String serviceId) {
        log.debug("Finding services by service ID: {}", serviceId);
        return reactiveRepository.findByServiceId(serviceId);
    }

    @Override
    public Mono<Service> addService(Service service) {
        log.debug("Adding new service: {}", service);
        return reactiveRepository.save(service);
    }

    @Override
    public Mono<Service> updateService(Service service) {
        log.debug("Updating service with ID: {}", service.getId());
        return findById(service.getId())
                .flatMap(existingService -> {
                    // Update fields
                    existingService.setServiceId(service.getServiceId());
                    existingService.setVehicleReservationId(service.getVehicleReservationId());
                    
                    return reactiveRepository.save(existingService);
                })
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Service", "id", service.getId())));
    }
}
