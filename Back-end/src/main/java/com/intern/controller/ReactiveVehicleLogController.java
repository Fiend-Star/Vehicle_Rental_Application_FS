package com.intern.controller;

import com.intern.carRental.primary.VehicleLog;
import com.intern.exception.ResourceNotFoundException;
import com.intern.primary.enums.VehicleLogType;
import com.intern.service.ReactiveVehicleLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.Date;

/**
 * Reactive REST controller for managing vehicle logs.
 * Provides endpoints for CRUD operations on vehicle logs using reactive programming.
 */
@RestController
@RequestMapping("/api/v2/vehicle-logs")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class ReactiveVehicleLogController {

    private final ReactiveVehicleLogService vehicleLogService;

    /**
     * Get all vehicle logs
     *
     * @return a Flux of all vehicle logs
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<VehicleLog> getAllVehicleLogs() {
        log.debug("REST request to get all Vehicle Logs");
        return vehicleLogService.findAll();
    }

    /**
     * Get a specific vehicle log by ID
     *
     * @param id the ID of the vehicle log
     * @return a Mono with the requested vehicle log
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<VehicleLog>> getVehicleLog(@PathVariable Long id) {
        log.debug("REST request to get Vehicle Log : {}", id);
        return vehicleLogService.findById(id)
                .map(vehicleLog -> ResponseEntity.ok().body(vehicleLog))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new vehicle log
     *
     * @param vehicleLog the vehicle log data
     * @return a Mono with the created vehicle log
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<VehicleLog> createVehicleLog(@Valid @RequestBody VehicleLog vehicleLog) {
        log.debug("REST request to save Vehicle Log : {}", vehicleLog);
        return vehicleLogService.createVehicleLog(vehicleLog);
    }

    /**
     * Update an existing vehicle log
     *
     * @param id the ID of the vehicle log to update
     * @param vehicleLog the updated vehicle log data
     * @return a Mono with the updated vehicle log
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<VehicleLog>> updateVehicleLog(@PathVariable Long id, @Valid @RequestBody VehicleLog vehicleLog) {
        log.debug("REST request to update Vehicle Log : {}, {}", id, vehicleLog);
        vehicleLog.setId(id);
        return vehicleLogService.updateVehicleLog(vehicleLog)
                .map(updatedVehicleLog -> ResponseEntity.ok().body(updatedVehicleLog))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete a vehicle log
     *
     * @param id the ID of the vehicle log to delete
     * @return a Mono with void result
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteVehicleLog(@PathVariable Long id) {
        log.debug("REST request to delete Vehicle Log : {}", id);
        return vehicleLogService.findById(id)
                .flatMap(vehicleLog -> vehicleLogService.delete(id))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Vehicle log not found with id " + id)));
    }

    /**
     * Find all vehicle logs for a specific vehicle
     *
     * @param vehicleId the ID of the vehicle
     * @return a Flux of vehicle logs for the specified vehicle
     */
    @GetMapping(value = "/vehicle/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<VehicleLog> getVehicleLogsByVehicleId(@PathVariable Long vehicleId) {
        log.debug("REST request to get Vehicle Logs by vehicle id : {}", vehicleId);
        return vehicleLogService.findAllByVehicleId(vehicleId);
    }

    /**
     * Find vehicle logs by log type
     *
     * @param type the log type to search for
     * @return a Flux of vehicle logs of the specified type
     */
    @GetMapping(value = "/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<VehicleLog> getVehicleLogsByType(@PathVariable VehicleLogType type) {
        log.debug("REST request to get Vehicle Logs by type : {}", type);
        return vehicleLogService.findByType(type);
    }

    /**
     * Find vehicle logs created within a date range
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return a Flux of vehicle logs created within the date range
     */
    @GetMapping(value = "/date-range", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<VehicleLog> getVehicleLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        log.debug("REST request to get Vehicle Logs between dates : {} and {}", startDate, endDate);
        return vehicleLogService.findByCreationDateBetween(startDate, endDate);
    }
}
