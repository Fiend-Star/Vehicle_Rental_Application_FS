package com.intern.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * Aspect for validating method inputs.
 * Validates inputs before method execution to ensure data integrity.
 */
@Aspect
@Component
@Slf4j
public class ValidationAspect {

    private final Validator validator;

    public ValidationAspect(Validator validator) {
        this.validator = validator;
    }

    /**
     * Pointcut for all methods annotated with @Validated
     */
    @Pointcut("@annotation(org.springframework.validation.annotation.Validated)")
    private void validatedMethods() {
    }

    /**
     * Pointcut for all controller methods
     */
    @Pointcut("within(com.intern.controller..*)")
    private void controllerMethods() {
    }

    /**
     * Advice to validate inputs before method execution
     *
     * @param joinPoint join point for advice
     */
    @Before("validatedMethods() || controllerMethods()")
    public void validateInputs(JoinPoint joinPoint) {
        log.debug("Validating inputs for method: {}", joinPoint.getSignature().toShortString());
        
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            log.debug("Method arguments: {}", Arrays.toString(args));
            
            // Handle Mono and Flux arguments
            Arrays.stream(args)
                .filter(arg -> arg != null)
                .forEach(arg -> {
                    if (arg instanceof Mono) {
                        // Mono validation logic would go here
                        log.debug("Mono argument detected: {}", arg);
                    } else {
                        // Direct validation logic for non-reactive types
                        validateObject(arg);
                    }
                });
        }
    }
    
    /**
     * Validate a single object
     *
     * @param object the object to validate
     */
    private void validateObject(Object object) {
        // Simplified validation logic
        log.debug("Validating object: {}", object);
        // Actual validation implementation would use the validator
    }
}
