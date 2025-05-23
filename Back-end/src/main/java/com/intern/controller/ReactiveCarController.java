package com.intern.controller;

import com.intern.carRental.primary.vehicletypes.Car;
import com.intern.service.ReactiveCarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for Car operations.
 * Provides reactive endpoints for Car management.
 */
@RestController
@RequestMapping("/api/v2/cars")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ReactiveCarController {

    private final ReactiveCarService reactiveCarService;

    /**
     * Get all Cars
     *
     * @return a Flux of all Cars
     */
    @GetMapping
    public Flux<Car> getAllCars() {
        log.info("Getting all cars");
        return reactiveCarService.findAll();
    }

    /**
     * Get a Car by ID
     *
     * @param id the Car ID
     * @return a Mono of the Car with the specified ID
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Car>> getCarById(@PathVariable Long id) {
        log.info("Getting car with ID: {}", id);
        return reactiveCarService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new Car
     *
     * @param car the Car to create
     * @return a Mono of the created Car
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Car> createCar(@RequestBody Car car) {
        log.info("Creating new car");
        return reactiveCarService.addCar(car);
    }

    /**
     * Update an existing Car
     *
     * @param id the Car ID
     * @param car the updated Car data
     * @return a Mono of the updated Car
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Car>> updateCar(@PathVariable Long id, @RequestBody Car car) {
        log.info("Updating car with ID: {}", id);
        car.setId(id);
        return reactiveCarService.updateCar(car)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete a Car
     *
     * @param id the Car ID
     * @return a Mono of Void indicating completion
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCar(@PathVariable Long id) {
        log.info("Deleting car with ID: {}", id);
        return reactiveCarService.deleteById(id);
    }

    /**
     * Get Cars by type
     *
     * @param type the car type to filter by
     * @return a Flux of Cars with the specified type
     */
    @GetMapping("/type/{type}")
    public Flux<Car> getCarsByType(@PathVariable String type) {
        log.info("Getting cars with type: {}", type);
        return reactiveCarService.findByType(type);
    }
}
