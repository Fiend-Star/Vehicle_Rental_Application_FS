package com.intern.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Server security context repository for handling JWT tokens.
 * Extracts tokens from requests and creates security contexts.
 */
@Component
public class JwtServerSecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtReactiveAuthenticationManager authenticationManager;

    public JwtServerSecurityContextRepository(JwtReactiveAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Save a security context
     *
     * @param exchange the web exchange
     * @param context the security context
     * @return a Mono completing when the context is saved
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        // We don't need to save the context for token-based authentication
        return Mono.empty();
    }

    /**
     * Load a security context
     *
     * @param exchange the web exchange
     * @return a Mono emitting the security context
     */
    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
            
            return authenticationManager.authenticate(auth)
                    .map(SecurityContextImpl::new);
        }
        
        return Mono.empty();
    }
}
