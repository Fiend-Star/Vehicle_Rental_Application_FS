package com.intern.service.impl;

import com.intern.carRental.primary.VehicleReservation;
import com.intern.repository.ReactiveVehicleReservationRepository;
import com.intern.service.ReactiveVehicleReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Implementation of ReactiveVehicleReservationService.
 */
@Service
public class ReactiveVehicleReservationServiceImpl implements ReactiveVehicleReservationService {

    private final ReactiveVehicleReservationRepository reservationRepository;

    @Autowired
    public ReactiveVehicleReservationServiceImpl(ReactiveVehicleReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Flux<VehicleReservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Mono<VehicleReservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Mono<VehicleReservation> save(VehicleReservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return reservationRepository.deleteById(id);
    }

    @Override
    public Flux<VehicleReservation> findByAccountId(Long accountId) {
        return reservationRepository.findByAccountId(accountId);
    }

    @Override
    public Flux<VehicleReservation> findByVehicleId(Long vehicleId) {
        return reservationRepository.findByVehicleId(vehicleId);
    }

    @Override
    public Flux<VehicleReservation> findActiveReservations() {
        return reservationRepository.findByIsCancelledFalseAndIsCompletedFalse();
    }

    @Override
    public Flux<VehicleReservation> findByPickupLocation(Long locationId) {
        return reservationRepository.findByPickupLocationId(locationId);
    }

    @Override
    public Mono<VehicleReservation> findByConfirmationNumber(String confirmationNumber) {
        return reservationRepository.findByConfirmationNumber(confirmationNumber);
    }

    @Override
    public Mono<VehicleReservation> createReservation(VehicleReservation reservation) {
        // Set creation date and initial status
        reservation.setCreationDate(new Date().getTime());
        reservation.setStatus("CREATED");
        
        return reservationRepository.save(reservation);
    }

    @Override
    public Mono<VehicleReservation> cancelReservation(Long id) {
        return reservationRepository.findById(id)
                .flatMap(reservation -> {
                    reservation.setStatus("CANCELLED");
                    return reservationRepository.save(reservation);
                });
    }

    @Override
    public Mono<VehicleReservation> completeReservation(Long id, Date returnDate) {
        return reservationRepository.findById(id)
                .flatMap(reservation -> {
                    reservation.setStatus("COMPLETED");
                    reservation.setReturnDate(returnDate.getTime());
                    return reservationRepository.save(reservation);
                });
    }
}
