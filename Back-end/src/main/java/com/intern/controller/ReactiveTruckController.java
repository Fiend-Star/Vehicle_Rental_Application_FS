package com.intern.controller;

import com.intern.carRental.primary.vehicletypes.Truck;
import com.intern.service.ReactiveTruckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for Truck operations.
 * Provides reactive endpoints for Truck management.
 */
@RestController
@RequestMapping("/api/v2/trucks")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ReactiveTruckController {

    private final ReactiveTruckService reactiveTruckService;

    /**
     * Get all Trucks
     *
     * @return a Flux of all Trucks
     */
    @GetMapping
    public Flux<Truck> getAllTrucks() {
        log.info("Getting all trucks");
        return reactiveTruckService.findAll();
    }

    /**
     * Get a Truck by ID
     *
     * @param id the Truck ID
     * @return a Mono of the Truck with the specified ID
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Truck>> getTruckById(@PathVariable Long id) {
        log.info("Getting truck with ID: {}", id);
        return reactiveTruckService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new Truck
     *
     * @param truck the Truck to create
     * @return a Mono of the created Truck
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Truck> createTruck(@RequestBody Truck truck) {
        log.info("Creating new truck");
        return reactiveTruckService.addTruck(truck);
    }

    /**
     * Update an existing Truck
     *
     * @param id the Truck ID
     * @param truck the updated Truck data
     * @return a Mono of the updated Truck
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Truck>> updateTruck(@PathVariable Long id, @RequestBody Truck truck) {
        log.info("Updating truck with ID: {}", id);
        truck.setId(id);
        return reactiveTruckService.updateTruck(truck)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete a Truck
     *
     * @param id the Truck ID
     * @return a Mono of Void indicating completion
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTruck(@PathVariable Long id) {
        log.info("Deleting truck with ID: {}", id);
        return reactiveTruckService.deleteById(id);
    }

    /**
     * Get Trucks by type
     *
     * @param type the truck type to filter by
     * @return a Flux of Trucks with the specified type
     */
    @GetMapping("/type/{type}")
    public Flux<Truck> getTrucksByType(@PathVariable String type) {
        log.info("Getting trucks with type: {}", type);
        return reactiveTruckService.findByType(type);
    }
}
