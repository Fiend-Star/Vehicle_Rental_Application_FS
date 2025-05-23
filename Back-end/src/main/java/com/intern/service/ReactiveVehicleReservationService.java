package com.intern.service;

import com.intern.carRental.primary.VehicleReservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Service interface for reactive operations on VehicleReservation entities.
 */
public interface ReactiveVehicleReservationService {
    
    /**
     * Retrieve all vehicle reservations.
     * 
     * @return Flux of all vehicle reservations
     */
    Flux<VehicleReservation> findAll();
    
    /**
     * Find a vehicle reservation by its ID.
     * 
     * @param id the reservation ID
     * @return Mono containing the found reservation or empty
     */
    Mono<VehicleReservation> findById(Long id);
    
    /**
     * Save a new or updated vehicle reservation.
     * 
     * @param reservation the reservation to save
     * @return Mono containing the saved reservation
     */
    Mono<VehicleReservation> save(VehicleReservation reservation);
    
    /**
     * Delete a vehicle reservation by its ID.
     * 
     * @param id the reservation ID to delete
     * @return Mono completing when deletion is done
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * Find reservations by account ID.
     * 
     * @param accountId the account ID
     * @return Flux of reservations for the specified account
     */
    Flux<VehicleReservation> findByAccountId(Long accountId);
    
    /**
     * Find reservations by vehicle ID.
     * 
     * @param vehicleId the vehicle ID
     * @return Flux of reservations for the specified vehicle
     */
    Flux<VehicleReservation> findByVehicleId(Long vehicleId);
    
    /**
     * Find active reservations.
     * 
     * @return Flux of active reservations
     */
    Flux<VehicleReservation> findActiveReservations();
    
    /**
     * Find reservations by pickup location.
     * 
     * @param locationId the location ID
     * @return Flux of reservations for the specified location
     */
    Flux<VehicleReservation> findByPickupLocation(Long locationId);
    
    /**
     * Find reservation by confirmation number.
     * 
     * @param confirmationNumber the confirmation number
     * @return Mono containing the found reservation
     */
    Mono<VehicleReservation> findByConfirmationNumber(String confirmationNumber);
    
    /**
     * Create a new reservation.
     * 
     * @param reservation the reservation details
     * @return Mono containing the created reservation
     */
    Mono<VehicleReservation> createReservation(VehicleReservation reservation);
    
    /**
     * Cancel a reservation.
     * 
     * @param id the reservation ID
     * @return Mono containing the updated reservation
     */
    Mono<VehicleReservation> cancelReservation(Long id);
    
    /**
     * Complete a reservation upon vehicle return.
     * 
     * @param id the reservation ID
     * @param returnDate the return date
     * @return Mono containing the updated reservation
     */
    Mono<VehicleReservation> completeReservation(Long id, Date returnDate);
}
