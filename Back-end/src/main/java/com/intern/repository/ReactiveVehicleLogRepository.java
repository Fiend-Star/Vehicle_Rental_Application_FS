package com.intern.repository;

import com.intern.carRental.primary.VehicleLog;
import com.intern.primary.enums.VehicleLogType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Reactive repository for VehicleLog entities.
 * Provides reactive operations for VehicleLog data access.
 */
@Repository
public interface ReactiveVehicleLogRepository extends ReactiveBaseRepository<VehicleLog, Long> {
    
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
}
