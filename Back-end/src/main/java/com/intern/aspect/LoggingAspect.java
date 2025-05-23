package com.intern.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging method execution.
 * This aspect logs method entry/exit, execution time, and exceptions.
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Pointcut for all service methods.
     */
    @Pointcut("within(com.intern.service..*)")
    private void forServicePackage() {
    }

    /**
     * Pointcut for all controller methods.
     */
    @Pointcut("within(com.intern.controller..*)")
    private void forControllerPackage() {
    }

    /**
     * Pointcut for all repository methods.
     */
    @Pointcut("within(com.intern.repository..*) || within(com.intern.DAO..*)")
    private void forRepositoryPackage() {
    }

    /**
     * Pointcut that combines multiple pointcuts.
     */
    @Pointcut("forServicePackage() || forControllerPackage()")
    private void forAppFlow() {
    }

    /**
     * Advice that logs method entry, exit and execution time.
     *
     * @param joinPoint join point for advice
     * @return result of proceeding
     * @throws Throwable if an error occurs
     */
    @Around("forAppFlow()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        log.debug("==> Entering: {} with arguments: {}", methodName, Arrays.toString(args));
        
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        
        log.debug("<== Exiting: {} with result: {} (executed in {}ms)", methodName, result, executionTime);
        
        return result;
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param exception exception being thrown
     */
    @AfterThrowing(pointcut = "forAppFlow()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in {}.{}() with cause = {}", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), 
                exception.getMessage() != null ? exception.getMessage() : "NULL");
    }
}
