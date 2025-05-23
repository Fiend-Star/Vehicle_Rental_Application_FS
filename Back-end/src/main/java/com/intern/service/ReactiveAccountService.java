package com.intern.service;

import com.intern.carRental.primary.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing accounts in the rental system.
 * Uses reactive types (Mono/Flux) for reactive programming support.
 */
public interface ReactiveAccountService extends ReactiveBaseService<Account, Long> {
    
    /**
     * Find an account by username
     * 
     * @param username the username to search for
     * @return a Mono emitting the found account or empty if not found
     */
    Mono<Account> findByUsername(String username);
    
    /**
     * Register a new account
     * 
     * @param account the account to register
     * @return a Mono emitting the registered account
     */
    Mono<Account> registerAccount(Account account);
    
    /**
     * Update an existing account
     * 
     * @param id the ID of the account to update
     * @param account the account with updated properties
     * @return a Mono emitting the updated account
     */
    Mono<Account> updateAccount(Long id, Account account);
    
    /**
     * Find accounts by status
     * 
     * @param status the status to search for
     * @return a Flux emitting the matching accounts
     */
    Flux<Account> findByStatus(String status);
    
    /**
     * Find all active accounts
     * 
     * @return a Flux emitting all active accounts
     */
    Flux<Account> findActiveAccounts();
    
    /**
     * Check if a username is available
     * 
     * @param username the username to check
     * @return a Mono emitting true if the username is available
     */
    Mono<Boolean> isUsernameAvailable(String username);
    
    /**
     * Check if an email is available
     * 
     * @param email the email to check
     * @return a Mono emitting true if the email is available
     */
    Mono<Boolean> isEmailAvailable(String email);
    
    /**
     * Change account status
     * 
     * @param id the ID of the account
     * @param status the new status
     * @return a Mono emitting the updated account
     */
    Mono<Account> changeStatus(Long id, String status);
}
