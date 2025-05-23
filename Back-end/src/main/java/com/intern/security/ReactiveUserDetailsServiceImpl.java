package com.intern.security;

import com.intern.carRental.primary.Account;
import com.intern.repository.ReactiveAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Reactive user details service for authentication.
 * Loads user details from the account repository.
 */
@Service
@RequiredArgsConstructor
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final ReactiveAccountRepository accountRepository;

    /**
     * Find a user by username
     *
     * @param username the username
     * @return a Mono emitting the user details
     */
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return accountRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .map(this::createUserDetails);
    }

    /**
     * Create user details from an account
     *
     * @param account the account
     * @return the user details
     */
    private UserDetails createUserDetails(Account account) {
        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole().split(","))
                .disabled(!account.getIsActive())
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked("LOCKED".equals(account.getStatus()))
                .build();
    }
}
