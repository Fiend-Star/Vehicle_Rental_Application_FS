package com.intern.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

import com.intern.security.jwt.ReactiveAuthenticationFilter;
import com.intern.security.jwt.ReactiveAuthEntryPointJwt;
import com.intern.security.jwt.ReactiveSecurityContextRepository;
import com.intern.service.ReactiveUserDetailsService;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Autowired
    private ReactiveUserDetailsService userDetailsService;
    
    @Autowired
    private ReactiveAuthEntryPointJwt unauthorizedHandler;
    
    @Autowired
    private ReactiveSecurityContextRepository securityContextRepository;
    
    @Bean
    public ReactiveAuthenticationFilter reactiveAuthenticationFilter() {
      return new ReactiveAuthenticationFilter();
    }
    
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = 
            new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(encoder());
        return authenticationManager;
    }
    
    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .exceptionHandling(exceptionHandlingSpec -> 
                exceptionHandlingSpec.authenticationEntryPoint(unauthorizedHandler))
            .securityContextRepository(securityContextRepository)
            .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                .pathMatchers("/api/v1/**").permitAll()
                .pathMatchers("/api/v1/test/user").hasAnyRole("USER", "ADMIN")
                .pathMatchers("/api/v1/test/admin").hasRole("ADMIN")
                .anyExchange().authenticated())
            .build();
    }
    
    // Bcrypt
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}