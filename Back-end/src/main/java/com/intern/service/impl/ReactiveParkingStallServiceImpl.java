package com.intern.service.impl;

import com.intern.DAO.ParkingStallRepository;
import com.intern.carRental.primary.ParkingStall;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveParkingStallRepository;
import com.intern.service.ReactiveParkingStallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveParkingStallService.
 * Provides reactive operations for parking stall management.
 */
@Service
@Slf4j
@Transactional
public class ReactiveParkingStallServiceImpl extends ReactiveBaseServiceImpl<ParkingStall, Long, ReactiveParkingStallRepository> 
        implements ReactiveParkingStallService {

    // Temporary reference to the old repository for migration
    @Autowired
    private ParkingStallRepository parkingStallRepository;

    /**
     * Constructor with repository dependency
     *
     * @param reactiveParkingStallRepository the reactive repository to use
     */
    public ReactiveParkingStallServiceImpl(ReactiveParkingStallRepository reactiveParkingStallRepository) {
        super(reactiveParkingStallRepository);
    }
    
    @Override
    protected Class<ParkingStall> getEntityClass() {
        return ParkingStall.class;
    }
    
    @Override
    protected String getEntityName() {
        return "ParkingStall";
    }

    @Override
    public Mono<ParkingStall> createParkingStall(ParkingStall parkingStall) {
        log.debug("Creating new parking stall: {}", parkingStall);
        return reactiveRepository.save(parkingStall);
    }

    @Override
    public Mono<ParkingStall> updateParkingStall(ParkingStall parkingStall) {
        log.debug("Updating parking stall: {}", parkingStall);
        return reactiveRepository.findById(parkingStall.getId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Parking stall not found with id: " + parkingStall.getId())))
                .flatMap(existingParkingStall -> {
                    // Update properties
                    existingParkingStall.setStallNumber(parkingStall.getStallNumber());
                    existingParkingStall.setLocationIdentifier(parkingStall.getLocationIdentifier());
                    
                    return reactiveRepository.save(existingParkingStall);
                });
    }

    @Override
    public Mono<Void> deleteParkingStall(Long id) {
        log.debug("Deleting parking stall with id: {}", id);
        return reactiveRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Parking stall not found with id: " + id)))
                .flatMap(parkingStall -> reactiveRepository.delete(parkingStall));
    }

    @Override
    public Mono<ParkingStall> findByStallNumber(String stallNumber) {
        log.debug("Finding parking stall by stall number: {}", stallNumber);
        return reactiveRepository.findByStallNumber(stallNumber);
    }

    @Override
    public Flux<ParkingStall> findByLocationIdentifier(String locationIdentifier) {
        log.debug("Finding parking stalls by location identifier: {}", locationIdentifier);
        return reactiveRepository.findByLocationIdentifier(locationIdentifier);
    }
}
