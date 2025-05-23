package com.intern.controller;

import com.intern.carRental.primary.ParkingStall;
import com.intern.exception.ResourceNotFoundException;
import com.intern.service.ReactiveParkingStallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

/**
 * Reactive REST controller for managing parking stalls.
 * Provides endpoints for CRUD operations on parking stalls using reactive programming.
 */
@RestController
@RequestMapping("/api/v2/parking-stalls")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class ReactiveParkingStallController {

    private final ReactiveParkingStallService parkingStallService;

    /**
     * Get all parking stalls
     *
     * @return a Flux of all parking stalls
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ParkingStall> getAllParkingStalls() {
        log.debug("REST request to get all Parking Stalls");
        return parkingStallService.findAll();
    }

    /**
     * Get a specific parking stall by ID
     *
     * @param id the ID of the parking stall
     * @return a Mono with the requested parking stall
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ParkingStall>> getParkingStall(@PathVariable Long id) {
        log.debug("REST request to get Parking Stall : {}", id);
        return parkingStallService.findById(id)
                .map(parkingStall -> ResponseEntity.ok().body(parkingStall))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new parking stall
     *
     * @param parkingStall the parking stall data
     * @return a Mono with the created parking stall
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ParkingStall> createParkingStall(@Valid @RequestBody ParkingStall parkingStall) {
        log.debug("REST request to save Parking Stall : {}", parkingStall);
        return parkingStallService.createParkingStall(parkingStall);
    }

    /**
     * Update an existing parking stall
     *
     * @param id the ID of the parking stall to update
     * @param parkingStall the updated parking stall data
     * @return a Mono with the updated parking stall
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ParkingStall>> updateParkingStall(@PathVariable Long id, @Valid @RequestBody ParkingStall parkingStall) {
        log.debug("REST request to update Parking Stall : {}, {}", id, parkingStall);
        parkingStall.setId(id);
        return parkingStallService.updateParkingStall(parkingStall)
                .map(updatedParkingStall -> ResponseEntity.ok().body(updatedParkingStall))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete a parking stall
     *
     * @param id the ID of the parking stall to delete
     * @return a Mono with void result
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteParkingStall(@PathVariable Long id) {
        log.debug("REST request to delete Parking Stall : {}", id);
        return parkingStallService.findById(id)
                .flatMap(parkingStall -> parkingStallService.delete(id))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Parking stall not found with id " + id)));
    }

    /**
     * Find a parking stall by stall number
     *
     * @param stallNumber the stall number to search for
     * @return a Mono with the requested parking stall
     */
    @GetMapping(value = "/stall/{stallNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ParkingStall>> getParkingStallByStallNumber(@PathVariable String stallNumber) {
        log.debug("REST request to get Parking Stall by stall number : {}", stallNumber);
        return parkingStallService.findByStallNumber(stallNumber)
                .map(parkingStall -> ResponseEntity.ok().body(parkingStall))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Find parking stalls by location identifier
     *
     * @param locationIdentifier the location identifier to search for
     * @return a Flux of matching parking stalls
     */
    @GetMapping(value = "/location/{locationIdentifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ParkingStall> getParkingStallsByLocationIdentifier(@PathVariable String locationIdentifier) {
        log.debug("REST request to get Parking Stalls by location identifier : {}", locationIdentifier);
        return parkingStallService.findByLocationIdentifier(locationIdentifier);
    }
}
