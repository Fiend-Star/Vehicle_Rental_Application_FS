package com.intern.service.impl;

import com.intern.DAO.VehicleRepository;
import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveVehicleRepository;
import com.intern.service.ReactiveVehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveVehicleService.
 * Provides reactive operations for vehicle management.
 */
@Service
@Slf4j
@Transactional
public class ReactiveVehicleServiceImpl extends ReactiveBaseServiceImpl<Vehicle, Long, ReactiveVehicleRepository> 
        implements ReactiveVehicleService {

    // Temporary reference to the old repository for migration
    @Autowired
    private VehicleRepository vehicleRepository;

    /**
     * Constructor with repository dependency
     *
     * @param reactiveVehicleRepository the reactive repository to use
     */
    public ReactiveVehicleServiceImpl(ReactiveVehicleRepository reactiveVehicleRepository) {
        super(reactiveVehicleRepository);
    }
    
    @Override
    protected Class<Vehicle> getEntityClass() {
        return Vehicle.class;
    }
    
    @Override
    protected String getEntityName() {
        return "Vehicle";
    }

    @Override
    public Mono<Vehicle> addVehicle(Vehicle vehicle) {
        log.debug("Adding new vehicle: {}", vehicle);
        return reactiveVehicleRepository.save(vehicle);
    }

    @Override
    public Mono<Vehicle> updateVehicle(Vehicle vehicle) {
        log.debug("Updating vehicle: {}", vehicle);
        return reactiveVehicleRepository.findById(vehicle.getId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Vehicle not found with id: " + vehicle.getId())))
                .flatMap(existingVehicle -> {
                    // Update fields
                    return reactiveVehicleRepository.save(vehicle);
                });
    }

    @Override
    public Mono<Void> removeVehicle(String numberPlate) {
        log.debug("Removing vehicle with number plate: {}", numberPlate);
        return reactiveVehicleRepository.findByNumberPlate(numberPlate)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Vehicle not found with number plate: " + numberPlate)))
                .flatMap(vehicle -> reactiveVehicleRepository.delete(vehicle));
    }

    @Override
    public Flux<Vehicle> searchByModel(String model) {
        log.debug("Searching vehicles by model: {}", model);
        return reactiveVehicleRepository.findByModel(model);
    }

    @Override
    public Flux<Vehicle> searchByMake(String make) {
        log.debug("Searching vehicles by make: {}", make);
        return reactiveVehicleRepository.findByMake(make);
    }

    @Override
    public Flux<Vehicle> getAllVehicles() {
        log.debug("Getting all vehicles");
        return reactiveVehicleRepository.findAll();
    }

    @Override
    public Mono<Vehicle> getVehicleById(Long id) {
        log.debug("Getting vehicle by ID: {}", id);
        return reactiveVehicleRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Vehicle not found with id: " + id)));
    }

    @Override
    public Mono<Vehicle> getVehicleByNumberPlate(String numberPlate) {
        log.debug("Getting vehicle by number plate: {}", numberPlate);
        return reactiveVehicleRepository.findByNumberPlate(numberPlate)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Vehicle not found with number plate: " + numberPlate)));
    }
    
    /**
     * Migration method to help transition from JPA to Reactive repositories.
     * This will load all vehicles from JPA repository and save them to reactive repository.
     *
     * @return a Flux emitting all migrated vehicles
     */
    public Flux<Vehicle> migrateVehiclesToReactive() {
        return Flux.fromIterable(vehicleRepository.findAll())
                .flatMap(reactiveVehicleRepository::save);
    }
}
