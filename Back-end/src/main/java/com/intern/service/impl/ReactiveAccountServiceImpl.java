package com.intern.service.impl;

import com.intern.carRental.primary.Account;
import com.intern.exception.ResourceNotFoundException;
import com.intern.repository.ReactiveAccountRepository;
import com.intern.service.ReactiveAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveAccountService.
 * Provides reactive operations for account management.
 */
@Service
@Slf4j
@Transactional
public class ReactiveAccountServiceImpl implements ReactiveAccountService {

    private final ReactiveAccountRepository reactiveAccountRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor with repository and password encoder dependencies
     *
     * @param reactiveAccountRepository the reactive repository to use
     * @param passwordEncoder the password encoder to use
     */
    public ReactiveAccountServiceImpl(ReactiveAccountRepository reactiveAccountRepository,
                                     PasswordEncoder passwordEncoder) {
        this.reactiveAccountRepository = reactiveAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Account> save(Account account) {
        log.debug("Saving account: {}", account);
        return reactiveAccountRepository.save(account);
    }

    @Override
    public Mono<Account> findById(Long id) {
        log.debug("Finding account by ID: {}", id);
        return reactiveAccountRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found with id: " + id)));
    }

    @Override
    public Flux<Account> findAll() {
        log.debug("Finding all accounts");
        return reactiveAccountRepository.findAll();
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        log.debug("Deleting account by ID: {}", id);
        return reactiveAccountRepository.existsById(id)
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return reactiveAccountRepository.deleteById(id);
                    } else {
                        return Mono.error(new ResourceNotFoundException("Account not found with id: " + id));
                    }
                });
    }

    @Override
    public Mono<Void> delete(Account entity) {
        log.debug("Deleting account: {}", entity);
        return reactiveAccountRepository.delete(entity);
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        log.debug("Checking if account exists by ID: {}", id);
        return reactiveAccountRepository.existsById(id);
    }

    @Override
    public Mono<Account> findByUsername(String username) {
        log.debug("Finding account by username: {}", username);
        return reactiveAccountRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found with username: " + username)));
    }

    @Override
    public Mono<Account> registerAccount(Account account) {
        log.debug("Registering new account: {}", account);
        
        // Validate username and email are available
        return Mono.zip(
                isUsernameAvailable(account.getUsername()),
                isEmailAvailable(account.getEmail())
            )
            .flatMap(tuple -> {
                Boolean isUsernameAvailable = tuple.getT1();
                Boolean isEmailAvailable = tuple.getT2();
                
                if (!isUsernameAvailable) {
                    return Mono.error(new IllegalArgumentException("Username already taken"));
                }
                
                if (!isEmailAvailable) {
                    return Mono.error(new IllegalArgumentException("Email already in use"));
                }
                
                // Encode password
                account.setPassword(passwordEncoder.encode(account.getPassword()));
                // Set default values
                account.setIsActive(true);
                account.setStatus("ACTIVE");
                
                return reactiveAccountRepository.save(account);
            });
    }

    @Override
    public Mono<Account> updateAccount(Long id, Account account) {
        log.debug("Updating account with ID {}: {}", id, account);
        return reactiveAccountRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found with id: " + id)))
                .flatMap(existingAccount -> {
                    // Update fields but preserve id and credentials
                    account.setId(id);
                    if (account.getPassword() == null || account.getPassword().isEmpty()) {
                        account.setPassword(existingAccount.getPassword());
                    } else {
                        account.setPassword(passwordEncoder.encode(account.getPassword()));
                    }
                    
                    return reactiveAccountRepository.save(account);
                });
    }

    @Override
    public Flux<Account> findByStatus(String status) {
        log.debug("Finding accounts by status: {}", status);
        return reactiveAccountRepository.findByStatus(status);
    }

    @Override
    public Flux<Account> findActiveAccounts() {
        log.debug("Finding active accounts");
        return reactiveAccountRepository.findByIsActiveTrue();
    }

    @Override
    public Mono<Boolean> isUsernameAvailable(String username) {
        log.debug("Checking if username is available: {}", username);
        return reactiveAccountRepository.existsByUsername(username).map(exists -> !exists);
    }

    @Override
    public Mono<Boolean> isEmailAvailable(String email) {
        log.debug("Checking if email is available: {}", email);
        return reactiveAccountRepository.existsByEmail(email).map(exists -> !exists);
    }

    @Override
    public Mono<Account> changeStatus(Long id, String status) {
        log.debug("Changing status for account with ID {}: {}", id, status);
        return reactiveAccountRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found with id: " + id)))
                .flatMap(account -> {
                    account.setStatus(status);
                    if ("ACTIVE".equals(status)) {
                        account.setIsActive(true);
                    } else if ("INACTIVE".equals(status) || "SUSPENDED".equals(status)) {
                        account.setIsActive(false);
                    }
                    return reactiveAccountRepository.save(account);
                });
    }
}
