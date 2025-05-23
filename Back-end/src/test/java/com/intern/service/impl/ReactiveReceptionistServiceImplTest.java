package com.intern.service.impl;

import com.intern.carRental.primary.Receptionist;
import com.intern.repository.ReactiveReceptionistRepository;
import com.intern.service.ReactiveReceptionistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class ReactiveReceptionistServiceImplTest {

    @Mock
    private ReactiveReceptionistRepository reactiveReceptionistRepository;

    private ReactiveReceptionistService reactiveReceptionistService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        reactiveReceptionistService = new ReactiveReceptionistServiceImpl(reactiveReceptionistRepository);
    }

    @Test
    public void testCreateReceptionist() {
        // Create a test receptionist
        Receptionist receptionist = new Receptionist();
        receptionist.setId(1L);
        receptionist.setFirstName("Test");
        receptionist.setLastName("User");
        receptionist.setEmail("test@example.com");
        receptionist.setActive(true);
        receptionist.setDateJoinedFromDate(new Date());

        when(reactiveReceptionistRepository.save(any(Receptionist.class))).thenReturn(Mono.just(receptionist));

        // Test the service method
        Mono<Receptionist> result = reactiveReceptionistService.createReceptionist(receptionist);

        // Verify the result
        StepVerifier.create(result)
                .expectNextMatches(saved -> saved.getId().equals(1L) && 
                                         saved.getFirstName().equals("Test") &&
                                         saved.getEmail().equals("test@example.com"))
                .verifyComplete();
    }

    @Test
    public void testFindByActiveStatus() {
        // Create test receptionists
        Receptionist receptionist1 = new Receptionist();
        receptionist1.setId(1L);
        receptionist1.setActive(true);

        Receptionist receptionist2 = new Receptionist();
        receptionist2.setId(2L);
        receptionist2.setActive(true);

        when(reactiveReceptionistRepository.findByActive(true)).thenReturn(Flux.just(receptionist1, receptionist2));

        // Test the service method
        Flux<Receptionist> result = reactiveReceptionistService.findByActiveStatus(true);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testUpdateReceptionist() {
        // Create a test receptionist
        Receptionist receptionist = new Receptionist();
        receptionist.setId(1L);
        receptionist.setFirstName("Updated");
        receptionist.setLastName("User");
        receptionist.setEmail("updated@example.com");
        receptionist.setActive(true);

        when(reactiveReceptionistRepository.findById(1L)).thenReturn(Mono.just(receptionist));
        when(reactiveReceptionistRepository.save(any(Receptionist.class))).thenReturn(Mono.just(receptionist));

        // Test the service method
        Mono<Receptionist> result = reactiveReceptionistService.updateReceptionist(receptionist);

        // Verify the result
        StepVerifier.create(result)
                .expectNextMatches(updated -> updated.getFirstName().equals("Updated") &&
                                            updated.getEmail().equals("updated@example.com"))
                .verifyComplete();
    }
}
