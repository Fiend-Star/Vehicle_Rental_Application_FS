package com.intern.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class ReactiveAuthEntryPointJwt implements ServerAuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveAuthEntryPointJwt.class);

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException authException) {
        logger.error("Unauthorized error: {}", authException.getMessage());
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
