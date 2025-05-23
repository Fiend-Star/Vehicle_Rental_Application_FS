package com.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.intern.repository.ReactiveAccountRepository;
import com.intern.security.UserDetailsImpl;

import reactor.core.publisher.Mono;

/**
 * Implementation of ReactiveUserDetailsService to authenticate users reactively.
 */
@Service
public class ReactiveUserDetailsService implements org.springframework.security.core.userdetails.ReactiveUserDetailsService {
    
    @Autowired
    private ReactiveAccountRepository accountRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return accountRepository.findByUsername(username)
            .map(UserDetailsImpl::build);
    }
}
