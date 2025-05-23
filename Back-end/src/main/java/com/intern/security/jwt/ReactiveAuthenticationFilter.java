package com.intern.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.intern.service.impl.JwtService;

import reactor.core.publisher.Mono;
import java.util.Optional;

public class ReactiveAuthenticationFilter implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveAuthenticationFilter.class);

    @Autowired
    private JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            Optional<String> jwt = parseJwt(exchange.getRequest());
            
            if (jwt.isPresent() && jwtService.validateJwtToken(jwt.get())) {
                // JWT is valid, continue with chain
                return chain.filter(exchange);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        // Continue with chain regardless of JWT validity
        return chain.filter(exchange);
    }

    private Optional<String> parseJwt(ServerHttpRequest request) {
        String headerAuth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return Optional.of(headerAuth.substring(7));
        }

        return Optional.empty();
    }
}
