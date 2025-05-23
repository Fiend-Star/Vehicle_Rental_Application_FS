package com.intern.service;

import com.intern.carRental.primary.VehicleLog;
import com.intern.primary.enums.VehicleLogType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Service interface for managing vehicle logs in the rental system.
 * Uses reactive types (Mono/Flux) for reactive programming support.
 * Extends the base reactive service interface.
 */
public interface ReactiveVehicleLogService extends ReactiveBaseService<VehicleLog, Long> {
    
    /**
     * Create a new vehicle log
     *
     * @param vehicleLog the vehicle log to create
     * @return a Mono emitting the created vehicle log
     */
    Mono<VehicleLog> createVehicleLog(VehicleLog vehicleLog);
    
    /**
     * Update an existing vehicle log
     *
     * @param vehicleLog the vehicle log with updated properties
     * @return a Mono emitting the updated vehicle log
     */
    Mono<VehicleLog> updateVehicleLog(VehicleLog vehicleLog);
    
    /**
     * Delete a vehicle log
     *
     * @param id the ID of the vehicle log to delete
     * @return a Mono completing when the vehicle log is deleted
     */
    Mono<Void> deleteVehicleLog(Long id);
    
    /**
     * Find all vehicle logs for a specific vehicle
     *
     * @param vehicleId the ID of the vehicle
     * @return a Flux emitting vehicle logs for the specified vehicle
     */
    Flux<VehicleLog> findAllByVehicleId(Long vehicleId);
    
    /**
     * Find vehicle logs by log type
     *
     * @param type the log type to search for
     * @return a Flux emitting vehicle logs of the specified type
     */
    Flux<VehicleLog> findByType(VehicleLogType type);
    
    /**
     * Find vehicle logs created within a date range
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return a Flux emitting vehicle logs created within the date range
     */
    Flux<VehicleLog> findByCreationDateBetween(Date startDate, Date endDate);
}
