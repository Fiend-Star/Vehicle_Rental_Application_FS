package com.intern.controller;

import com.intern.carRental.primary.Account;
import com.intern.security.JwtUtil;
import com.intern.service.ReactiveAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for authentication operations.
 * Handles login and registration.
 */
@RestController
@RequestMapping("/api/v2/auth")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class ReactiveAuthController {

    private final ReactiveAuthenticationManager authenticationManager;
    private final ReactiveUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final ReactiveAccountService accountService;

    /**
     * Login endpoint
     *
     * @param loginRequest the login request
     * @return a Mono with the authentication response
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, Object>>> login(@RequestBody LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        )
        .flatMap(authentication -> userDetailsService.findByUsername(loginRequest.getUsername()))
        .map(userDetails -> {
            String token = jwtUtil.generateToken(userDetails);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", userDetails.getUsername());
            response.put("roles", userDetails.getAuthorities());
            
            return ResponseEntity.ok(response);
        })
        .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    /**
     * Register endpoint
     *
     * @param account the account to register
     * @return a Mono with the registration response
     */
    @PostMapping("/register")
    public Mono<ResponseEntity<Account>> register(@Valid @RequestBody Account account) {
        return accountService.registerAccount(account)
                .map(savedAccount -> ResponseEntity.status(HttpStatus.CREATED).body(savedAccount))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    /**
     * Login request class
     */
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
