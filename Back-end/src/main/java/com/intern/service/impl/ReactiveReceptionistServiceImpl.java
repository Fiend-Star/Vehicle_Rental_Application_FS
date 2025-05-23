package com.intern.service.impl;

import com.intern.carRental.primary.Receptionist;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveReceptionistRepository;
import com.intern.service.ReactiveReceptionistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveReceptionistService.
 * Provides reactive operations for Receptionist management.
 */
@Service
@Slf4j
@Transactional
public class ReactiveReceptionistServiceImpl extends ReactiveBaseServiceImpl<Receptionist, Long, ReactiveReceptionistRepository> 
        implements ReactiveReceptionistService {

    /**
     * Constructor with repository dependency
     *
     * @param reactiveReceptionistRepository the reactive repository to use
     */
    public ReactiveReceptionistServiceImpl(ReactiveReceptionistRepository reactiveReceptionistRepository) {
        super(reactiveReceptionistRepository);
    }
    
    @Override
    protected Class<Receptionist> getEntityClass() {
        return Receptionist.class;
    }
    
    @Override
    protected String getEntityName() {
        return "Receptionist";
    }

    @Override
    public Flux<Receptionist> findByActiveStatus(boolean active) {
        log.debug("Finding receptionists with active status: {}", active);
        return reactiveRepository.findByActive(active);
    }

    @Override
    public Mono<Receptionist> createReceptionist(Receptionist receptionist) {
        log.debug("Creating new receptionist: {}", receptionist);
        return reactiveRepository.save(receptionist);
    }

    @Override
    public Mono<Receptionist> updateReceptionist(Receptionist receptionist) {
        log.debug("Updating receptionist with ID: {}", receptionist.getId());
        return reactiveRepository.findById(receptionist.getId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Receptionist not found with id: " + receptionist.getId())))
                .flatMap(existingReceptionist -> {
                    // All fields are updated from the provided receptionist
                    return reactiveRepository.save(receptionist);
                });
    }
}
