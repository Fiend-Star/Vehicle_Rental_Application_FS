package com.intern.service.impl;

import com.intern.carRental.primary.vehicletypes.Car;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveCarRepository;
import com.intern.service.ReactiveCarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveCarService interface.
 */
@Service
@Slf4j
@Transactional
public class ReactiveCarServiceImpl extends ReactiveBaseServiceImpl<Car, Long, ReactiveCarRepository>
        implements ReactiveCarService {

    /**
     * Constructor with repository dependency
     *
     * @param reactiveCarRepository the reactive car repository
     */
    public ReactiveCarServiceImpl(ReactiveCarRepository reactiveCarRepository) {
        super(reactiveCarRepository);
    }

    @Override
    protected Class<Car> getEntityClass() {
        return Car.class;
    }

    @Override
    protected String getEntityName() {
        return "Car";
    }

    @Override
    public Flux<Car> findByType(String type) {
        log.debug("Finding cars by type: {}", type);
        return reactiveRepository.findByType(type);
    }

    @Override
    public Mono<Car> addCar(Car car) {
        log.debug("Adding new car: {}", car);
        return reactiveRepository.save(car);
    }

    @Override
    public Mono<Car> updateCar(Car car) {
        log.debug("Updating car with ID: {}", car.getId());
        return reactiveRepository.findById(car.getId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Car not found with id: " + car.getId())))
                .flatMap(existingCar -> {
                    // All fields are updated from the provided car
                    return reactiveRepository.save(car);
                });
    }
}
