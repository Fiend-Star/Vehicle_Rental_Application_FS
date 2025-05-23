package com.intern.repository;

import com.intern.carRental.primary.VehicleReservation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Reactive repository for VehicleReservation entities.
 * Provides reactive operations for VehicleReservation data access.
 */
@Repository
public interface ReactiveVehicleReservationRepository extends ReactiveBaseRepository<VehicleReservation, Long> {
    
    /**
     * Find reservations by account ID
     * 
     * @param accountId the ID of the account
     * @return a Flux emitting reservations for the specified account
     */
    Flux<VehicleReservation> findByAccountId(Long accountId);
    
    /**
     * Find reservations by vehicle ID
     * 
     * @param vehicleId the ID of the vehicle
     * @return a Flux emitting reservations for the specified vehicle
     */
    Flux<VehicleReservation> findByVehicleId(Long vehicleId);
    
    /**
     * Find active reservations (not cancelled and not completed)
     * 
     * @return a Flux emitting active reservations
     */
    Flux<VehicleReservation> findByIsCancelledFalseAndIsCompletedFalse();
    
    /**
     * Find reservations with creation date after the specified date
     * 
     * @param date the cutoff date
     * @return a Flux emitting reservations created after the specified date
     */
    Flux<VehicleReservation> findByCreationDateAfter(LocalDateTime date);
    
    /**
     * Find reservations by location ID
     * 
     * @param locationId the ID of the location
     * @return a Flux emitting reservations for the specified location
     */
    Flux<VehicleReservation> findByPickupLocationId(Long locationId);
    
    /**
     * Find reservation by confirmation number
     * 
     * @param confirmationNumber the confirmation number
     * @return a Mono emitting the reservation with the specified confirmation number
     */
    Mono<VehicleReservation> findByConfirmationNumber(String confirmationNumber);
    
    /**
     * Count active reservations for a vehicle
     * 
     * @param vehicleId the ID of the vehicle
     * @return a Mono emitting the count of active reservations
     */
    Mono<Long> countByVehicleIdAndIsCancelledFalseAndIsCompletedFalse(Long vehicleId);
}
