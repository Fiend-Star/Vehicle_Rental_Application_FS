package com.intern.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Unit tests for PerformanceMonitoringAspect.
 */
@ExtendWith(MockitoExtension.class)
class PerformanceMonitoringAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private PerformanceMonitoringAspect performanceAspect;

    private TestService testService;
    private TestService proxiedTestService;

    @Test
    @DisplayName("Should measure method execution time")
    void testMeasureMethodExecutionTime() throws Throwable {
        // Given
        when(joinPoint.proceed()).thenReturn("result");
        when(joinPoint.getSignature()).thenReturn(mock(org.aspectj.lang.Signature.class));
        when(joinPoint.getSignature().toShortString()).thenReturn("TestService.testMethod()");

        // When
        Object result = performanceAspect.measureMethodExecutionTime(joinPoint);

        // Then
        assertEquals("result", result);
        verify(joinPoint).proceed();
        verify(joinPoint, atLeastOnce()).getSignature();
    }

    @Test
    @DisplayName("Should correctly measure slow methods")
    void testSlowMethodMeasurement() throws Exception {
        // Create the test service and proxy
        testService = new TestService();
        AspectJProxyFactory factory = new AspectJProxyFactory(testService);
        factory.addAspect(new PerformanceMonitoringAspect());
        proxiedTestService = factory.getProxy();
        
        // When/Then
        assertTimeout(Duration.ofMillis(1100), () -> {
            String result = proxiedTestService.slowMethod();
            assertEquals("Slow method completed", result);
        });
    }

    /**
     * Test service for AOP testing.
     */
    static class TestService {
        
        public String testMethod() {
            return "Test result";
        }
        
        public String slowMethod() {
            try {
                // Simulate a method that takes time to execute
                TimeUnit.MILLISECONDS.sleep(200);
                return "Slow method completed";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Interrupted";
            }
        }
    }
}
