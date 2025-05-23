package com.intern.controller;

import com.intern.service.ReactiveBaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Base reactive controller class providing standard CRUD operations.
 * This is a template for all reactive controllers to extend.
 *
 * @param <T> Entity type
 * @param <ID> ID type of the entity
 * @param <S> Service type
 */
public abstract class ReactiveBaseController<T, ID, S extends ReactiveBaseService<T, ID>> {

    protected final S service;

    /**
     * Constructor with service dependency
     *
     * @param service the reactive service to use
     */
    protected ReactiveBaseController(S service) {
        this.service = service;
    }

    /**
     * Get all entities
     *
     * @return a Flux of all entities
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<T> getAll() {
        return service.findAll();
    }

    /**
     * Get an entity by its ID
     *
     * @param id the ID of the entity
     * @return a Mono with the requested entity
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<T>> getById(@PathVariable ID id) {
        return service.findById(id)
                .map(entity -> ResponseEntity.ok().body(entity))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new entity
     *
     * @param entity the entity to create
     * @return a Mono with the created entity
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<T> create(@RequestBody T entity) {
        return service.save(entity);
    }

    /**
     * Update an existing entity
     *
     * @param id the ID of the entity to update
     * @param entity the updated entity data
     * @return a Mono with the updated entity
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<T>> update(@PathVariable ID id, @RequestBody T entity) {
        return service.existsById(id)
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        // The setId method must be implemented in concrete controllers
                        // as we can't set an ID on a generic T
                        return setEntityId(entity, id)
                                .flatMap(service::save)
                                .map(updatedEntity -> ResponseEntity.ok().body(updatedEntity));
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    /**
     * Delete an entity by its ID
     *
     * @param id the ID of the entity to delete
     * @return a Mono completing when the entity is deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable ID id) {
        return service.deleteById(id);
    }

    /**
     * Abstract method to set the ID of an entity.
     * This needs to be implemented by concrete controllers.
     *
     * @param entity the entity
     * @param id the ID to set
     * @return a Mono emitting the entity with ID set
     */
    protected abstract Mono<T> setEntityId(T entity, ID id);
}
