package com.intern.repository;

import com.intern.carRental.primary.Receptionist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class ReactiveReceptionistRepositoryTest {

    @Autowired
    private ReactiveReceptionistRepository reactiveReceptionistRepository;

    @Test
    public void testSaveAndFindReceptionist() {
        // Create a receptionist
        Receptionist receptionist = new Receptionist();
        receptionist.setFirstName("John");
        receptionist.setLastName("Doe");
        receptionist.setActive(true);
        receptionist.setEmail("john.doe@example.com");
        receptionist.setPassword("securePassword123");
        receptionist.setDateJoinedFromDate(new Date());

        // Save the receptionist
        Receptionist savedReceptionist = reactiveReceptionistRepository.save(receptionist).block();
        
        // Assertions
        assertNotNull(savedReceptionist);
        assertNotNull(savedReceptionist.getId());
        assertEquals("John", savedReceptionist.getFirstName());
        assertEquals("Doe", savedReceptionist.getLastName());
        assertEquals(true, savedReceptionist.isActive());
        
        // Find by id
        Receptionist foundReceptionist = reactiveReceptionistRepository.findById(savedReceptionist.getId()).block();
        assertNotNull(foundReceptionist);
        assertEquals(savedReceptionist.getId(), foundReceptionist.getId());
        assertEquals("john.doe@example.com", foundReceptionist.getEmail());
        
        // Find by active status
        long countActive = reactiveReceptionistRepository.findByActive(true).count().block();
        assert countActive > 0;
    }
}
