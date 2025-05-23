package com.intern.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for GlobalExceptionHandler.
 */
@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Test
    @DisplayName("Should handle ResourceNotFoundException")
    void testHandleResourceNotFoundException() {
        // Given
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");

        // When
        GlobalExceptionHandler.ApiError apiError = exceptionHandler.handleResourceNotFoundException(ex);

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), apiError.getStatus());
        assertEquals("Resource not found", apiError.getMessage());
        assertNotNull(apiError.getTimestamp());
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException")
    void testHandleIllegalArgumentException() {
        // Given
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");

        // When
        GlobalExceptionHandler.ApiError apiError = exceptionHandler.handleIllegalArgumentException(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), apiError.getStatus());
        assertEquals("Invalid argument", apiError.getMessage());
        assertNotNull(apiError.getTimestamp());
    }

    @Test
    @DisplayName("Should handle validation exceptions")
    void testHandleValidationExceptions() {
        // Given
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("object", "field1", "Error message 1"));
        fieldErrors.add(new FieldError("object", "field2", "Error message 2"));
        
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(new ArrayList<>(fieldErrors));
        
        // When
        GlobalExceptionHandler.ValidationError validationError = exceptionHandler.handleValidationExceptions(ex);
        
        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), validationError.getStatus());
        assertEquals("Validation failed", validationError.getMessage());
        assertNotNull(validationError.getTimestamp());
        assertEquals(2, validationError.getErrors().size());
        assertEquals("Error message 1", validationError.getErrors().get("field1"));
        assertEquals("Error message 2", validationError.getErrors().get("field2"));
    }

    @Test
    @DisplayName("Should handle global exceptions")
    void testHandleGlobalException() {
        // Given
        Exception ex = new Exception("Unexpected error");
        
        // When
        ResponseEntity<GlobalExceptionHandler.ApiError> responseEntity = exceptionHandler.handleGlobalException(ex);
        
        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        GlobalExceptionHandler.ApiError apiError = responseEntity.getBody();
        assertNotNull(apiError);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiError.getStatus());
        assertEquals("An unexpected error occurred", apiError.getMessage());
        assertNotNull(apiError.getTimestamp());
    }
}
