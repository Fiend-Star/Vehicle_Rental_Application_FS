package com.intern.service.impl;

import com.intern.carRental.primary.vehicletypes.Motorcycle;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveMotorcycleRepository;
import com.intern.service.ReactiveMotorcycleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveMotorcycleService interface.
 */
@Service
@Slf4j
@Transactional
public class ReactiveMotorcycleServiceImpl extends ReactiveBaseServiceImpl<Motorcycle, Long, ReactiveMotorcycleRepository>
        implements ReactiveMotorcycleService {

    /**
     * Constructor with repository dependency
     *
     * @param reactiveMotorcycleRepository the reactive motorcycle repository
     */
    public ReactiveMotorcycleServiceImpl(ReactiveMotorcycleRepository reactiveMotorcycleRepository) {
        super(reactiveMotorcycleRepository);
    }

    @Override
    protected Class<Motorcycle> getEntityClass() {
        return Motorcycle.class;
    }

    @Override
    protected String getEntityName() {
        return "Motorcycle";
    }

    @Override
    public Flux<Motorcycle> findByType(String type) {
        log.debug("Finding motorcycles by type: {}", type);
        return reactiveRepository.findByType(type);
    }

    @Override
    public Mono<Motorcycle> addMotorcycle(Motorcycle motorcycle) {
        log.debug("Adding new motorcycle: {}", motorcycle);
        return reactiveRepository.save(motorcycle);
    }

    @Override
    public Mono<Motorcycle> updateMotorcycle(Motorcycle motorcycle) {
        log.debug("Updating motorcycle with ID: {}", motorcycle.getId());
        return reactiveRepository.findById(motorcycle.getId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Motorcycle not found with id: " + motorcycle.getId())))
                .flatMap(existingMotorcycle -> {
                    // All fields are updated from the provided motorcycle
                    return reactiveRepository.save(motorcycle);
                });
    }
}
