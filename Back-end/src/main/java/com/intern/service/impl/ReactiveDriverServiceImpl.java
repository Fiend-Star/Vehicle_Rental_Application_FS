package com.intern.service.impl;

import com.intern.primary.addonServices.Driver;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveDriverRepository;
import com.intern.service.ReactiveDriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveDriverService interface.
 */
@Service
@Slf4j
@Transactional
public class ReactiveDriverServiceImpl extends ReactiveBaseServiceImpl<Driver, Long, ReactiveDriverRepository>
        implements ReactiveDriverService {

    /**
     * Constructor with repository dependency
     *
     * @param reactiveDriverRepository the reactive driver repository
     */
    public ReactiveDriverServiceImpl(ReactiveDriverRepository reactiveDriverRepository) {
        super(reactiveDriverRepository);
    }

    @Override
    protected Class<Driver> getEntityClass() {
        return Driver.class;
    }

    @Override
    protected String getEntityName() {
        return "Driver";
    }

    @Override
    public Flux<Driver> findByVehicleReservationId(Long vehicleReservationId) {
        log.debug("Finding drivers by vehicle reservation ID: {}", vehicleReservationId);
        return reactiveRepository.findByVehicleReservationId(vehicleReservationId);
    }

    @Override
    public Mono<Driver> addDriver(Driver driver) {
        log.debug("Adding new driver: {}", driver);
        return reactiveRepository.save(driver);
    }

    @Override
    public Mono<Driver> updateDriver(Driver driver) {
        log.debug("Updating driver with ID: {}", driver.getId());
        return findById(driver.getId())
                .flatMap(existingDriver -> {
                    // Update any fields specific to Driver entity
                    // If Driver has specific fields that need to be updated, add them here
                    
                    // Update fields from the Service parent class
                    existingDriver.setName(driver.getName());
                    existingDriver.setDescription(driver.getDescription());
                    existingDriver.setCost(driver.getCost());
                    
                    return reactiveRepository.save(existingDriver);
                })
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Driver", "id", driver.getId())));
    }
}
