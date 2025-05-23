package com.intern.repository;

import com.intern.carRental.primary.Account;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for Account entities.
 * Provides reactive operations for Account data access.
 */
@Repository
public interface ReactiveAccountRepository extends ReactiveBaseRepository<Account, Long> {
    
    /**
     * Find an account by username
     * 
     * @param username the username
     * @return a Mono emitting the account with the specified username
     */
    Mono<Account> findByUsername(String username);
    
    /**
     * Find an account by email
     * 
     * @param email the email
     * @return a Mono emitting the account with the specified email
     */
    Mono<Account> findByEmail(String email);
    
    /**
     * Find accounts by status
     * 
     * @param status the account status
     * @return a Flux emitting accounts with the specified status
     */
    Flux<Account> findByStatus(String status);
    
    /**
     * Find active accounts
     * 
     * @return a Flux emitting active accounts
     */
    Flux<Account> findByIsActiveTrue();
    
    /**
     * Check if an account exists by username
     * 
     * @param username the username
     * @return a Mono emitting true if an account with the specified username exists
     */
    Mono<Boolean> existsByUsername(String username);
    
    /**
     * Check if an account exists by email
     * 
     * @param email the email
     * @return a Mono emitting true if an account with the specified email exists
     */
    Mono<Boolean> existsByEmail(String email);
}
