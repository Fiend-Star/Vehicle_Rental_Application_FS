package com.intern.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Base interface for all reactive repositories.
 * Extends Spring Data's ReactiveCrudRepository to enable reactive database operations.
 *
 * @param <T> the domain type the repository manages
 * @param <ID> the type of the ID of the entity the repository manages
 */
@NoRepositoryBean
public interface ReactiveBaseRepository<T, ID> extends ReactiveCrudRepository<T, ID> {
    // Common reactive methods can be defined here
}
