package com.intern.controller;

import com.intern.carRental.primary.vehicletypes.SUV;
import com.intern.service.ReactiveSUVService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for SUV operations.
 * Provides reactive endpoints for SUV management.
 */
@RestController
@RequestMapping("/api/v2/suvs")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ReactiveSUVController {

    private final ReactiveSUVService reactiveSUVService;

    /**
     * Get all SUVs
     *
     * @return a Flux of all SUVs
     */
    @GetMapping
    public Flux<SUV> getAllSUVs() {
        log.info("Getting all SUVs");
        return reactiveSUVService.findAll();
    }

    /**
     * Get a SUV by ID
     *
     * @param id the SUV ID
     * @return a Mono of the SUV with the specified ID
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<SUV>> getSUVById(@PathVariable Long id) {
        log.info("Getting SUV with ID: {}", id);
        return reactiveSUVService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new SUV
     *
     * @param suv the SUV to create
     * @return a Mono of the created SUV
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SUV> createSUV(@RequestBody SUV suv) {
        log.info("Creating new SUV");
        return reactiveSUVService.addSUV(suv);
    }

    /**
     * Update an existing SUV
     *
     * @param id the SUV ID
     * @param suv the updated SUV data
     * @return a Mono of the updated SUV
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<SUV>> updateSUV(@PathVariable Long id, @RequestBody SUV suv) {
        log.info("Updating SUV with ID: {}", id);
        suv.setId(id);
        return reactiveSUVService.updateSUV(suv)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete a SUV
     *
     * @param id the SUV ID
     * @return a Mono of Void indicating completion
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteSUV(@PathVariable Long id) {
        log.info("Deleting SUV with ID: {}", id);
        return reactiveSUVService.deleteById(id);
    }

    /**
     * Get SUVs by type
     *
     * @param type the SUV type to filter by
     * @return a Flux of SUVs with the specified type
     */
    @GetMapping("/type/{type}")
    public Flux<SUV> getSUVsByType(@PathVariable String type) {
        log.info("Getting SUVs with type: {}", type);
        return reactiveSUVService.findByType(type);
    }
}
