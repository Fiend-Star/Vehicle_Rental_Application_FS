package com.intern.service.impl;

import com.intern.DAO.VehicleLogRepository;
import com.intern.carRental.primary.VehicleLog;
import com.intern.exception.ResourceNotFoundException;
import com.intern.primary.enums.VehicleLogType;
import com.intern.repository.ReactiveVehicleLogRepository;
import com.intern.service.ReactiveVehicleLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Implementation of the ReactiveVehicleLogService.
 * Provides reactive operations for vehicle log management.
 */
@Service
@Slf4j
@Transactional
public class ReactiveVehicleLogServiceImpl extends ReactiveBaseServiceImpl<VehicleLog, Long, ReactiveVehicleLogRepository> 
        implements ReactiveVehicleLogService {

    // Temporary reference to the old repository for migration
    @Autowired
    private VehicleLogRepository vehicleLogRepository;

    /**
     * Constructor with repository dependency
     *
     * @param reactiveVehicleLogRepository the reactive repository to use
     */
    public ReactiveVehicleLogServiceImpl(ReactiveVehicleLogRepository reactiveVehicleLogRepository) {
        super(reactiveVehicleLogRepository);
    }
    
    @Override
    protected Class<VehicleLog> getEntityClass() {
        return VehicleLog.class;
    }
    
    @Override
    protected String getEntityName() {
        return "VehicleLog";
    }

    @Override
    public Mono<VehicleLog> createVehicleLog(VehicleLog vehicleLog) {
        log.debug("Creating new vehicle log: {}", vehicleLog);
        if (vehicleLog.getCreationDate() == null) {
            vehicleLog.setCreationDate(new Date());
        }
        return reactiveRepository.save(vehicleLog);
    }

    @Override
    public Mono<VehicleLog> updateVehicleLog(VehicleLog vehicleLog) {
        log.debug("Updating vehicle log: {}", vehicleLog);
        return reactiveRepository.findById(vehicleLog.getId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Vehicle log not found with id: " + vehicleLog.getId())))
                .flatMap(existingVehicleLog -> {
                    // Update properties
                    existingVehicleLog.setType(vehicleLog.getType());
                    existingVehicleLog.setDescription(vehicleLog.getDescription());
                    
                    return reactiveRepository.save(existingVehicleLog);
                });
    }

    @Override
    public Mono<Void> deleteVehicleLog(Long id) {
        log.debug("Deleting vehicle log with id: {}", id);
        return reactiveRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Vehicle log not found with id: " + id)))
                .flatMap(vehicleLog -> reactiveRepository.delete(vehicleLog));
    }

    @Override
    public Flux<VehicleLog> findAllByVehicleId(Long vehicleId) {
        log.debug("Finding vehicle logs by vehicle id: {}", vehicleId);
        return reactiveRepository.findAllByVehicleId(vehicleId);
    }

    @Override
    public Flux<VehicleLog> findByType(VehicleLogType type) {
        log.debug("Finding vehicle logs by type: {}", type);
        return reactiveRepository.findByType(type);
    }

    @Override
    public Flux<VehicleLog> findByCreationDateBetween(Date startDate, Date endDate) {
        log.debug("Finding vehicle logs between dates: {} and {}", startDate, endDate);
        // This method would typically require a custom query in the repository
        // For now, we'll fetch all and filter
        return reactiveRepository.findAll()
                .filter(vehicleLog -> 
                    vehicleLog.getCreationDate() != null &&
                    !vehicleLog.getCreationDate().before(startDate) && 
                    !vehicleLog.getCreationDate().after(endDate)
                );
    }
}
