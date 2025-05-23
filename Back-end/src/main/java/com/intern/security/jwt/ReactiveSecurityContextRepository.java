package com.intern.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.intern.service.ReactiveUserDetailsService;
import com.intern.service.impl.JwtService;

import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class ReactiveSecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        // We don't need to save the SecurityContext as we are using JWT
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String jwt = parseJwt(request);
        
        if (jwt != null && jwtService.validateJwtToken(jwt)) {
            String username = jwtService.getUserNameFromJwtToken(jwt);
            return userDetailsService.findByUsername(username)
                .map(userDetails -> {
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                    return new SecurityContextImpl(auth);
                });
        }
        
        return Mono.empty();
    }
    
    private String parseJwt(ServerHttpRequest request) {
        String headerAuth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        
        // Check for cookie
        List<HttpCookie> cookies = request.getCookies().get("intern");
        if (cookies != null && !cookies.isEmpty()) {
            return cookies.get(0).getValue();
        }
        
        return null;
    }
}
