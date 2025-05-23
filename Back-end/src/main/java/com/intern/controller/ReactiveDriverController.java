package com.intern.controller;

import com.intern.primary.addonServices.Driver;
import com.intern.service.ReactiveDriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for Driver operations.
 * Provides reactive endpoints for Driver management.
 */
@RestController
@RequestMapping("/api/v2/drivers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ReactiveDriverController {

    private final ReactiveDriverService reactiveDriverService;

    /**
     * Get all Drivers
     *
     * @return a Flux of all Drivers
     */
    @GetMapping
    public Flux<Driver> getAllDrivers() {
        log.info("Getting all drivers");
        return reactiveDriverService.findAll();
    }

    /**
     * Get a Driver by ID
     *
     * @param id the Driver ID
     * @return a Mono of the Driver with the specified ID
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Driver>> getDriverById(@PathVariable Long id) {
        log.info("Getting driver with ID: {}", id);
        return reactiveDriverService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new Driver
     *
     * @param driver the Driver to create
     * @return a Mono of the created Driver
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Driver> createDriver(@RequestBody Driver driver) {
        log.info("Creating new driver");
        return reactiveDriverService.addDriver(driver);
    }

    /**
     * Update an existing Driver
     *
     * @param id the Driver ID
     * @param driver the updated Driver data
     * @return a Mono of the updated Driver
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Driver>> updateDriver(@PathVariable Long id, @RequestBody Driver driver) {
        log.info("Updating driver with ID: {}", id);
        driver.setId(id);
        return reactiveDriverService.updateDriver(driver)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete a Driver
     *
     * @param id the Driver ID
     * @return a Mono of Void indicating completion
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteDriver(@PathVariable Long id) {
        log.info("Deleting driver with ID: {}", id);
        return reactiveDriverService.deleteById(id);
    }

    /**
     * Get Drivers by vehicle reservation ID
     *
     * @param reservationId the vehicle reservation ID to filter by
     * @return a Flux of Drivers associated with the specified vehicle reservation
     */
    @GetMapping("/reservation/{reservationId}")
    public Flux<Driver> getDriversByReservationId(@PathVariable Long reservationId) {
        log.info("Getting drivers for vehicle reservation ID: {}", reservationId);
        return reactiveDriverService.findByVehicleReservationId(reservationId);
    }
}
