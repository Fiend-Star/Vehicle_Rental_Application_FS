package com.intern.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

/**
 * Global exception handler for reactive endpoints.
 * Provides standardized error responses for different exception types.
 */
@Configuration
@Order(-2)
@Slf4j
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalErrorWebExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("Error occurred: ", ex);

        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        
        // Determine HTTP status based on exception type
        if (ex instanceof ResourceNotFoundException) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
        } else if (ex instanceof IllegalArgumentException) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        // Create error response
        ErrorResponse errorResponse = new ErrorResponse(
                exchange.getResponse().getStatusCode().value(),
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );
        
        // Convert to JSON
        String errorResponseJson;
        try {
            errorResponseJson = objectMapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            errorResponseJson = "{\"status\":" + exchange.getResponse().getStatusCode().value() + 
                               ",\"message\":\"Error processing response\",\"path\":\"" + 
                               exchange.getRequest().getPath().value() + "\"}";
        }
        
        byte[] bytes = errorResponseJson.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = bufferFactory.wrap(bytes);
        
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
    
    /**
     * Inner class representing error response structure
     */
    private static class ErrorResponse {
        private final int status;
        private final String message;
        private final String path;
        
        public ErrorResponse(int status, String message, String path) {
            this.status = status;
            this.message = message;
            this.path = path;
        }
        
        public int getStatus() {
            return status;
        }
        
        public String getMessage() {
            return message;
        }
        
        public String getPath() {
            return path;
        }
    }
}
