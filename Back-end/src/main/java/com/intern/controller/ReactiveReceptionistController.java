package com.intern.controller;

import com.intern.carRental.primary.Receptionist;
import com.intern.service.ReactiveReceptionistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for Receptionist operations.
 * Provides reactive endpoints for Receptionist management.
 */
@RestController
@RequestMapping("/api/v2/receptionists")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ReactiveReceptionistController {

    private final ReactiveReceptionistService reactiveReceptionistService;

    /**
     * Get all Receptionists
     *
     * @return a Flux of all Receptionists
     */
    @GetMapping
    public Flux<Receptionist> getAllReceptionists() {
        log.info("Getting all receptionists");
        return reactiveReceptionistService.findAll();
    }

    /**
     * Get a Receptionist by ID
     *
     * @param id the Receptionist ID
     * @return a Mono of the Receptionist with the specified ID
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Receptionist>> getReceptionistById(@PathVariable Long id) {
        log.info("Getting receptionist with ID: {}", id);
        return reactiveReceptionistService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new Receptionist
     *
     * @param receptionist the Receptionist to create
     * @return a Mono of the created Receptionist
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Receptionist> createReceptionist(@RequestBody Receptionist receptionist) {
        log.info("Creating new receptionist");
        return reactiveReceptionistService.createReceptionist(receptionist);
    }

    /**
     * Update an existing Receptionist
     *
     * @param id the Receptionist ID
     * @param receptionist the updated Receptionist data
     * @return a Mono of the updated Receptionist
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Receptionist>> updateReceptionist(@PathVariable Long id, @RequestBody Receptionist receptionist) {
        log.info("Updating receptionist with ID: {}", id);
        receptionist.setId(id);
        return reactiveReceptionistService.updateReceptionist(receptionist)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete a Receptionist
     *
     * @param id the Receptionist ID
     * @return a Mono of Void indicating completion
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteReceptionist(@PathVariable Long id) {
        log.info("Deleting receptionist with ID: {}", id);
        return reactiveReceptionistService.deleteById(id);
    }

    /**
     * Get Receptionists by active status
     *
     * @param active the active status to filter by
     * @return a Flux of Receptionists with the specified active status
     */
    @GetMapping("/status")
    public Flux<Receptionist> getReceptionistsByStatus(@RequestParam boolean active) {
        log.info("Getting receptionists with active status: {}", active);
        return reactiveReceptionistService.findByActiveStatus(active);
    }
}
