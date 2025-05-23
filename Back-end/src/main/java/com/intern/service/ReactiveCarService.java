package com.intern.service;

import com.intern.carRental.primary.vehicletypes.Car;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for reactive operations on Car entities.
 */
public interface ReactiveCarService extends ReactiveBaseService<Car, Long> {
    
    /**
     * Find cars by their type
     *
     * @param type the car type string to search for
     * @return a Flux of cars with the specified type
     */
    Flux<Car> findByType(String type);
    
    /**
     * Add a new car
     *
     * @param car the car to add
     * @return a Mono containing the saved car
     */
    Mono<Car> addCar(Car car);
    
    /**
     * Update an existing car
     *
     * @param car the car with updated information
     * @return a Mono containing the updated car
     */
    Mono<Car> updateCar(Car car);
}