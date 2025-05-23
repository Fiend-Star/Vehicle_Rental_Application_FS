package com.intern.controller;

import com.intern.carRental.primary.vehicletypes.Van;
import com.intern.service.ReactiveVanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for Van operations.
 * Provides reactive endpoints for Van management.
 */
@RestController
@RequestMapping("/api/v2/vans")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ReactiveVanController {

    private final ReactiveVanService reactiveVanService;

    /**
     * Get all Vans
     *
     * @return a Flux of all Vans
     */
    @GetMapping
    public Flux<Van> getAllVans() {
        log.info("Getting all vans");
        return reactiveVanService.findAll();
    }

    /**
     * Get a Van by ID
     *
     * @param id the Van ID
     * @return a Mono of the Van with the specified ID
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Van>> getVanById(@PathVariable Long id) {
        log.info("Getting van with ID: {}", id);
        return reactiveVanService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new Van
     *
     * @param van the Van to create
     * @return a Mono of the created Van
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Van> createVan(@RequestBody Van van) {
        log.info("Creating new van");
        return reactiveVanService.addVan(van);
    }

    /**
     * Update an existing Van
     *
     * @param id the Van ID
     * @param van the updated Van data
     * @return a Mono of the updated Van
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Van>> updateVan(@PathVariable Long id, @RequestBody Van van) {
        log.info("Updating van with ID: {}", id);
        van.setId(id);
        return reactiveVanService.updateVan(van)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete a Van
     *
     * @param id the Van ID
     * @return a Mono of Void indicating completion
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteVan(@PathVariable Long id) {
        log.info("Deleting van with ID: {}", id);
        return reactiveVanService.deleteById(id);
    }

    /**
     * Get Vans by type
     *
     * @param type the van type to filter by
     * @return a Flux of Vans with the specified type
     */
    @GetMapping("/type/{type}")
    public Flux<Van> getVansByType(@PathVariable String type) {
        log.info("Getting vans with type: {}", type);
        return reactiveVanService.findByType(type);
    }
}
