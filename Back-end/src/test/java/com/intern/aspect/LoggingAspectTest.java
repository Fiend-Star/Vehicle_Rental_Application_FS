package com.intern.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for LoggingAspect.
 */
@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private LoggingAspect loggingAspect;

    private TestService testService;
    private TestService proxiedTestService;

    @BeforeEach
    void setUp() {
        // Create a real test service
        testService = new TestService();
        
        // Create the proxy using AOP
        AspectJProxyFactory factory = new AspectJProxyFactory(testService);
        factory.addAspect(loggingAspect);
        proxiedTestService = factory.getProxy();
    }

    @Test
    @DisplayName("Should log around method execution")
    void testLogAround() throws Throwable {
        // Given
        when(joinPoint.proceed()).thenReturn("result");
        when(joinPoint.getSignature()).thenReturn(mock(org.aspectj.lang.Signature.class));
        when(joinPoint.getSignature().toShortString()).thenReturn("TestService.testMethod()");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"arg1", "arg2"});

        // When
        Object result = loggingAspect.logAround(joinPoint);

        // Then
        assertEquals("result", result);
        verify(joinPoint).proceed();
        verify(joinPoint, atLeastOnce()).getSignature();
        verify(joinPoint).getArgs();
    }

    @Test
    @DisplayName("Should log exceptions thrown by methods")
    void testLogAfterThrowing() {
        // When/Then
        // This will call the test service method that throws an exception
        // The aspect should log the exception
        Exception exception = assertThrows(RuntimeException.class, 
                () -> proxiedTestService.methodThatThrowsException());
        assertEquals("Test exception", exception.getMessage());
    }

    /**
     * Test service for AOP testing.
     */
    static class TestService {
        
        public String testMethod(String arg1, String arg2) {
            return arg1 + arg2;
        }
        
        public void methodThatThrowsException() {
            throw new RuntimeException("Test exception");
        }
    }
}
