package com.intern.service.impl;

import com.intern.carRental.primary.vehicletypes.Truck;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveTruckRepository;
import com.intern.service.ReactiveTruckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveTruckService interface.
 */
@Service
@Slf4j
@Transactional
public class ReactiveTruckServiceImpl extends ReactiveBaseServiceImpl<Truck, Long, ReactiveTruckRepository>
        implements ReactiveTruckService {

    /**
     * Constructor with repository dependency
     *
     * @param reactiveTruckRepository the reactive truck repository
     */
    public ReactiveTruckServiceImpl(ReactiveTruckRepository reactiveTruckRepository) {
        super(reactiveTruckRepository);
    }

    @Override
    protected Class<Truck> getEntityClass() {
        return Truck.class;
    }

    @Override
    protected String getEntityName() {
        return "Truck";
    }

    @Override
    public Flux<Truck> findByType(String type) {
        log.debug("Finding trucks by type: {}", type);
        return reactiveRepository.findByType(type);
    }

    @Override
    public Mono<Truck> addTruck(Truck truck) {
        log.debug("Adding new truck: {}", truck);
        return reactiveRepository.save(truck);
    }

    @Override
    public Mono<Truck> updateTruck(Truck truck) {
        log.debug("Updating truck with ID: {}", truck.getId());
        return findById(truck.getId())
                .flatMap(existingTruck -> {
                    // Update fields from the input truck
                    existingTruck.setType(truck.getType());
                    existingTruck.setLicensePlate(truck.getLicensePlate());
                    existingTruck.setMake(truck.getMake());
                    existingTruck.setModel(truck.getModel());
                    existingTruck.setYear(truck.getYear());
                    existingTruck.setPassengerCapacity(truck.getPassengerCapacity());
                    existingTruck.setBarcode(truck.getBarcode());
                    existingTruck.setStatus(truck.getStatus());
                    existingTruck.setLogEntries(truck.getLogEntries());
                    
                    return reactiveRepository.save(existingTruck);
                })
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Truck", "id", truck.getId())));
    }
}
