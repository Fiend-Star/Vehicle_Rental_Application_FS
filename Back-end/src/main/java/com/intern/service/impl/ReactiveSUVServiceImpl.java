package com.intern.service.impl;

import com.intern.carRental.primary.vehicletypes.SUV;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveSUVRepository;
import com.intern.service.ReactiveSUVService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveSUVService interface.
 */
@Service
@Slf4j
@Transactional
public class ReactiveSUVServiceImpl extends ReactiveBaseServiceImpl<SUV, Long, ReactiveSUVRepository>
        implements ReactiveSUVService {

    /**
     * Constructor with repository dependency
     *
     * @param reactiveSUVRepository the reactive SUV repository
     */
    public ReactiveSUVServiceImpl(ReactiveSUVRepository reactiveSUVRepository) {
        super(reactiveSUVRepository);
    }

    @Override
    protected Class<SUV> getEntityClass() {
        return SUV.class;
    }

    @Override
    protected String getEntityName() {
        return "SUV";
    }

    @Override
    public Flux<SUV> findByType(String type) {
        log.debug("Finding SUVs by type: {}", type);
        return reactiveRepository.findByType(type);
    }

    @Override
    public Mono<SUV> addSUV(SUV suv) {
        log.debug("Adding new SUV: {}", suv);
        return reactiveRepository.save(suv);
    }

    @Override
    public Mono<SUV> updateSUV(SUV suv) {
        log.debug("Updating SUV with ID: {}", suv.getId());
        return reactiveRepository.findById(suv.getId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("SUV not found with id: " + suv.getId())))
                .flatMap(existingSUV -> {
                    // All fields are updated from the provided SUV
                    return reactiveRepository.save(suv);
                });
    }
}
