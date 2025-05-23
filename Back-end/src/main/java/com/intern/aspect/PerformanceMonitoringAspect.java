package com.intern.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Aspect for monitoring and logging method execution times.
 * Useful for performance monitoring and identifying bottlenecks.
 */
@Aspect
@Component
@Slf4j
public class PerformanceMonitoringAspect {

    private final ConcurrentMap<String, MethodStats> methodStats = new ConcurrentHashMap<>();

    /**
     * Pointcut for all service methods.
     */
    @Pointcut("within(com.intern.service..*)")
    private void serviceMethod() {
    }

    /**
     * Pointcut for all repository methods.
     */
    @Pointcut("within(com.intern.repository..*) || within(com.intern.DAO..*)")
    private void repositoryMethod() {
    }

    /**
     * Pointcut for controller methods.
     */
    @Pointcut("within(com.intern.controller..*) || within(com.intern.controllers..*)")
    private void controllerMethod() {
    }

    /**
     * Advice that measures and logs method execution times.
     *
     * @param joinPoint join point for advice
     * @return result of proceeding
     * @throws Throwable if an error occurs
     */
    @Around("serviceMethod() || repositoryMethod() || controllerMethod()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodSignature = joinPoint.getSignature().toShortString();
        
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        
        long executionTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        
        // Update method statistics
        MethodStats stats = methodStats.computeIfAbsent(methodSignature, 
                k -> new MethodStats(methodSignature));
        stats.addExecutionTime(executionTime);
        
        // Log if execution time is above a threshold (e.g., 100ms)
        if (executionTime > 100) {
            log.warn("Slow method execution: {} took {}ms", methodSignature, executionTime);
        }
        
        // Every 100 executions, log stats for this method
        if (stats.getExecutionCount() % 100 == 0) {
            log.info("Method stats: {} - avg: {}ms, max: {}ms, count: {}", 
                    methodSignature, 
                    stats.getAverageExecutionTime(), 
                    stats.getMaxExecutionTime(),
                    stats.getExecutionCount());
        }
        
        return result;
    }

    /**
     * Class to hold method execution statistics
     */
    private static class MethodStats {
        private final String methodName;
        private long totalExecutionTime = 0;
        private long executionCount = 0;
        private long maxExecutionTime = 0;

        public MethodStats(String methodName) {
            this.methodName = methodName;
        }

        public synchronized void addExecutionTime(long executionTime) {
            totalExecutionTime += executionTime;
            executionCount++;
            maxExecutionTime = Math.max(maxExecutionTime, executionTime);
        }

        public long getAverageExecutionTime() {
            return executionCount > 0 ? totalExecutionTime / executionCount : 0;
        }

        public long getMaxExecutionTime() {
            return maxExecutionTime;
        }

        public long getExecutionCount() {
            return executionCount;
        }
    }
}
