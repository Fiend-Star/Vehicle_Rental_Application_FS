package com.intern.service.impl;

import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveBaseRepository;
import com.intern.service.ReactiveBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Base implementation of the ReactiveBaseService interface.
 * Provides common reactive CRUD operations.
 *
 * @param <T> Entity type
 * @param <ID> ID type of the entity
 * @param <R> Repository type
 */
@Transactional
@RequiredArgsConstructor
public abstract class ReactiveBaseServiceImpl<T, ID, R extends ReactiveCrudRepository<T, ID>> 
        implements ReactiveBaseService<T, ID> {

    protected final R repository;
    
    /**
     * Get the entity class type
     * 
     * @return the entity class
     */
    protected abstract Class<T> getEntityClass();
    
    /**
     * Get the entity name for error messages
     * 
     * @return the entity name
     */
    protected abstract String getEntityName();

    @Override
    public Mono<T> save(T entity) {
        return repository.save(entity);
    }

    @Override
    public Mono<T> findById(ID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(
                        getEntityName() + " not found with id: " + id)));
    }

    @Override
    public Flux<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return repository.existsById(id)
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return repository.deleteById(id);
                    } else {
                        return Mono.error(new ResourceNotFoundException(
                                getEntityName() + " not found with id: " + id));
                    }
                });
    }

    @Override
    public Mono<Void> delete(T entity) {
        return repository.delete(entity);
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return repository.existsById(id);
    }
}
