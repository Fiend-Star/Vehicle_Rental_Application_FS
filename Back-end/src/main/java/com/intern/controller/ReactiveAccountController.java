package com.intern.controller;

import com.intern.carRental.primary.abstrct.Account;
import com.intern.service.ReactiveAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

/**
 * Reactive REST controller for managing accounts.
 * Provides endpoints for CRUD operations on accounts using reactive programming.
 */
@RestController
@RequestMapping("/api/v2/accounts")
@CrossOrigin
@Slf4j
public class ReactiveAccountController {

    private final ReactiveAccountService accountService;

    /**
     * Constructor with service dependency
     *
     * @param accountService the reactive service to use
     */
    public ReactiveAccountController(ReactiveAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Get all accounts
     *
     * @return a Flux of all accounts
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Account> getAllAccounts() {
        log.debug("REST request to get all Accounts");
        return accountService.findAll();
    }

    /**
     * Get a specific account by ID
     *
     * @param id the ID of the account
     * @return a Mono with the requested account
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Account>> getAccount(@PathVariable Long id) {
        log.debug("REST request to get Account : {}", id);
        return accountService.findById(id)
                .map(account -> ResponseEntity.ok().body(account))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Get a specific account by username
     *
     * @param username the username of the account
     * @return a Mono with the requested account
     */
    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Account>> getAccountByUsername(@PathVariable String username) {
        log.debug("REST request to get Account by username : {}", username);
        return accountService.findByUsername(username)
                .map(account -> ResponseEntity.ok().body(account))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new account
     *
     * @param account the account data
     * @return a Mono with the created account
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Account> createAccount(@Valid @RequestBody Account account) {
        log.debug("REST request to save Account : {}", account);
        return accountService.registerAccount(account);
    }

    /**
     * Update an existing account
     *
     * @param id the ID of the account to update
     * @param account the updated account data
     * @return a Mono with the updated account
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Account>> updateAccount(@PathVariable Long id, @Valid @RequestBody Account account) {
        log.debug("REST request to update Account : {}, {}", id, account);
        return accountService.updateAccount(id, account)
                .map(updatedAccount -> ResponseEntity.ok().body(updatedAccount));
    }

    /**
     * Delete an account
     *
     * @param id the ID of the account to delete
     * @return a Mono with void result
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAccount(@PathVariable Long id) {
        log.debug("REST request to delete Account : {}", id);
        return accountService.deleteById(id);
    }

    /**
     * Get accounts by status
     *
     * @param status the status to filter by
     * @return a Flux of matching accounts
     */
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Account> getAccountsByStatus(@PathVariable String status) {
        log.debug("REST request to get Accounts by status : {}", status);
        return accountService.findByStatus(status);
    }

    /**
     * Get all active accounts
     *
     * @return a Flux of active accounts
     */
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Account> getActiveAccounts() {
        log.debug("REST request to get active Accounts");
        return accountService.findActiveAccounts();
    }

    /**
     * Check if a username is available
     *
     * @param username the username to check
     * @return a Mono with the availability result
     */
    @GetMapping(value = "/check-username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> checkUsernameAvailability(@PathVariable String username) {
        log.debug("REST request to check username availability : {}", username);
        return accountService.isUsernameAvailable(username);
    }

    /**
     * Check if an email is available
     *
     * @param email the email to check
     * @return a Mono with the availability result
     */
    @GetMapping(value = "/check-email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> checkEmailAvailability(@PathVariable String email) {
        log.debug("REST request to check email availability : {}", email);
        return accountService.isEmailAvailable(email);
    }

    /**
     * Change account status
     *
     * @param id the ID of the account
     * @param status the new status
     * @return a Mono with the updated account
     */
    @PatchMapping(value = "/{id}/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Account>> changeAccountStatus(@PathVariable Long id, @PathVariable String status) {
        log.debug("REST request to change Account status : {}, {}", id, status);
        return accountService.changeStatus(id, status)
                .map(updatedAccount -> ResponseEntity.ok().body(updatedAccount));
    }
}
