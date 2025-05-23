package com.intern.controller;

import com.intern.carRental.primary.VehicleReservation;
import com.intern.service.ReactiveVehicleReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * REST controller for managing VehicleReservation resources reactively.
 */
@RestController
@RequestMapping("/api/reactive/reservations")
public class ReactiveVehicleReservationController {

    private final ReactiveVehicleReservationService reservationService;

    @Autowired
    public ReactiveVehicleReservationController(ReactiveVehicleReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * GET /api/reactive/reservations : Get all reservations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reservations
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<VehicleReservation> getAllReservations() {
        return reservationService.findAll();
    }

    /**
     * GET /api/reactive/reservations/:id : Get reservation by id.
     *
     * @param id the id of the reservation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reservation,
     * or with status 404 (Not Found)
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<VehicleReservation>> getReservation(@PathVariable Long id) {
        return reservationService.findById(id)
                .map(reservation -> ResponseEntity.ok().body(reservation))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/reactive/reservations : Create a new reservation.
     *
     * @param reservation the reservation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reservation
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<VehicleReservation> createReservation(@RequestBody VehicleReservation reservation) {
        return reservationService.createReservation(reservation);
    }

    /**
     * PUT /api/reactive/reservations/:id : Update an existing reservation.
     *
     * @param id the id of the reservation to update
     * @param reservation the reservation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reservation,
     * or with status 404 (Not Found) if the reservation couldn't be updated
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<VehicleReservation>> updateReservation(
            @PathVariable Long id,
            @RequestBody VehicleReservation reservation) {
        reservation.setId(id);
        return reservationService.save(reservation)
                .map(result -> ResponseEntity.ok().body(result))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/reactive/reservations/:id : Delete a reservation.
     *
     * @param id the id of the reservation to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteReservation(@PathVariable Long id) {
        return reservationService.deleteById(id);
    }

    /**
     * GET /api/reactive/reservations/account/:accountId : Get reservations by account id.
     *
     * @param accountId the id of the account
     * @return the ResponseEntity with status 200 (OK) and the list of reservations
     */
    @GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<VehicleReservation> getReservationsByAccount(@PathVariable Long accountId) {
        return reservationService.findByAccountId(accountId);
    }

    /**
     * GET /api/reactive/reservations/vehicle/:vehicleId : Get reservations by vehicle id.
     *
     * @param vehicleId the id of the vehicle
     * @return the ResponseEntity with status 200 (OK) and the list of reservations
     */
    @GetMapping(value = "/vehicle/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<VehicleReservation> getReservationsByVehicle(@PathVariable Long vehicleId) {
        return reservationService.findByVehicleId(vehicleId);
    }

    /**
     * GET /api/reactive/reservations/location/:locationId : Get reservations by pickup location.
     *
     * @param locationId the id of the location
     * @return the ResponseEntity with status 200 (OK) and the list of reservations
     */
    @GetMapping(value = "/location/{locationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<VehicleReservation> getReservationsByLocation(@PathVariable Long locationId) {
        return reservationService.findByPickupLocation(locationId);
    }

    /**
     * GET /api/reactive/reservations/active : Get all active reservations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of active reservations
     */
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<VehicleReservation> getActiveReservations() {
        return reservationService.findActiveReservations();
    }

    /**
     * PUT /api/reactive/reservations/:id/cancel : Cancel a reservation.
     *
     * @param id the id of the reservation to cancel
     * @return the ResponseEntity with status 200 (OK) and with body the updated reservation
     */
    @PutMapping(value = "/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<VehicleReservation>> cancelReservation(@PathVariable Long id) {
        return reservationService.cancelReservation(id)
                .map(result -> ResponseEntity.ok().body(result))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/reactive/reservations/:id/complete : Complete a reservation.
     *
     * @param id the id of the reservation to complete
     * @return the ResponseEntity with status 200 (OK) and with body the updated reservation
     */
    @PutMapping(value = "/{id}/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<VehicleReservation>> completeReservation(@PathVariable Long id) {
        return reservationService.completeReservation(id, new Date())
                .map(result -> ResponseEntity.ok().body(result))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
