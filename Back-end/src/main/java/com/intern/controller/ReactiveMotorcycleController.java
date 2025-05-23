package com.intern.controller;

import com.intern.carRental.primary.vehicletypes.Motorcycle;
import com.intern.service.ReactiveMotorcycleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for Motorcycle operations.
 * Provides reactive endpoints for Motorcycle management.
 */
@RestController
@RequestMapping("/api/v2/motorcycles")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ReactiveMotorcycleController {

    private final ReactiveMotorcycleService reactiveMotorcycleService;

    /**
     * Get all Motorcycles
     *
     * @return a Flux of all Motorcycles
     */
    @GetMapping
    public Flux<Motorcycle> getAllMotorcycles() {
        log.info("Getting all motorcycles");
        return reactiveMotorcycleService.findAll();
    }

    /**
     * Get a Motorcycle by ID
     *
     * @param id the Motorcycle ID
     * @return a Mono of the Motorcycle with the specified ID
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Motorcycle>> getMotorcycleById(@PathVariable Long id) {
        log.info("Getting motorcycle with ID: {}", id);
        return reactiveMotorcycleService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new Motorcycle
     *
     * @param motorcycle the Motorcycle to create
     * @return a Mono of the created Motorcycle
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Motorcycle> createMotorcycle(@RequestBody Motorcycle motorcycle) {
        log.info("Creating new motorcycle");
        return reactiveMotorcycleService.addMotorcycle(motorcycle);
    }

    /**
     * Update an existing Motorcycle
     *
     * @param id the Motorcycle ID
     * @param motorcycle the updated Motorcycle data
     * @return a Mono of the updated Motorcycle
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Motorcycle>> updateMotorcycle(@PathVariable Long id, @RequestBody Motorcycle motorcycle) {
        log.info("Updating motorcycle with ID: {}", id);
        motorcycle.setId(id);
        return reactiveMotorcycleService.updateMotorcycle(motorcycle)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete a Motorcycle
     *
     * @param id the Motorcycle ID
     * @return a Mono of Void indicating completion
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMotorcycle(@PathVariable Long id) {
        log.info("Deleting motorcycle with ID: {}", id);
        return reactiveMotorcycleService.deleteById(id);
    }

    /**
     * Get Motorcycles by type
     *
     * @param type the motorcycle type to filter by
     * @return a Flux of Motorcycles with the specified type
     */
    @GetMapping("/type/{type}")
    public Flux<Motorcycle> getMotorcyclesByType(@PathVariable String type) {
        log.info("Getting motorcycles with type: {}", type);
        return reactiveMotorcycleService.findByType(type);
    }
}
