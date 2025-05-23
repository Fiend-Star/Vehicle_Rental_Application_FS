package com.intern.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Generic base service interface for reactive operations.
 * Provides a consistent API for CRUD operations.
 *
 * @param <T> Entity type
 * @param <ID> ID type of the entity
 */
public interface ReactiveBaseService<T, ID> {
    
    /**
     * Save an entity
     *
     * @param entity the entity to save
     * @return a Mono emitting the saved entity
     */
    Mono<T> save(T entity);
    
    /**
     * Find an entity by its ID
     *
     * @param id the ID of the entity
     * @return a Mono emitting the found entity or empty if not found
     */
    Mono<T> findById(ID id);
    
    /**
     * Find all entities
     *
     * @return a Flux emitting all entities
     */
    Flux<T> findAll();
    
    /**
     * Delete an entity by its ID
     *
     * @param id the ID of the entity to delete
     * @return a Mono completing when the entity is deleted
     */
    Mono<Void> deleteById(ID id);
    
    /**
     * Delete an entity
     *
     * @param entity the entity to delete
     * @return a Mono completing when the entity is deleted
     */
    Mono<Void> delete(T entity);
    
    /**
     * Check if an entity exists by its ID
     *
     * @param id the ID of the entity
     * @return a Mono emitting true if the entity exists, false otherwise
     */
    Mono<Boolean> existsById(ID id);
}
