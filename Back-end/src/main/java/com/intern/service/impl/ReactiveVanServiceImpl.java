package com.intern.service.impl;

import com.intern.carRental.primary.vehicletypes.Van;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveVanRepository;
import com.intern.service.ReactiveVanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveVanService interface.
 */
@Service
@Slf4j
@Transactional
public class ReactiveVanServiceImpl extends ReactiveBaseServiceImpl<Van, Long, ReactiveVanRepository>
        implements ReactiveVanService {

    /**
     * Constructor with repository dependency
     *
     * @param reactiveVanRepository the reactive van repository
     */
    public ReactiveVanServiceImpl(ReactiveVanRepository reactiveVanRepository) {
        super(reactiveVanRepository);
    }

    @Override
    protected Class<Van> getEntityClass() {
        return Van.class;
    }

    @Override
    protected String getEntityName() {
        return "Van";
    }

    @Override
    public Flux<Van> findByType(String type) {
        log.debug("Finding vans by type: {}", type);
        return reactiveRepository.findByType(type);
    }

    @Override
    public Mono<Van> addVan(Van van) {
        log.debug("Adding new van: {}", van);
        return reactiveRepository.save(van);
    }

    @Override
    public Mono<Van> updateVan(Van van) {
        log.debug("Updating van with ID: {}", van.getId());
        return reactiveRepository.findById(van.getId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Van not found with id: " + van.getId())))
                .flatMap(existingVan -> {
                    // All fields are updated from the provided van
                    return reactiveRepository.save(van);
                });
    }
}
