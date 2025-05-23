package com.intern.controller;

import com.intern.carRental.primary.vehicletypes.Motorcycle;
import com.intern.service.ReactiveMotorcycleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Test class for ReactiveMotorcycleController.
 */
@WebFluxTest(ReactiveMotorcycleController.class)
@ActiveProfiles("test")
public class ReactiveMotorcycleControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveMotorcycleService reactiveMotorcycleService;

    @Test
    public void testGetAllMotorcycles() {
        // Create test motorcycles
        Motorcycle motorcycle1 = new Motorcycle();
        motorcycle1.setId(1L);
        motorcycle1.setLicensePlate("M123");
        motorcycle1.setType("CRUISER");

        Motorcycle motorcycle2 = new Motorcycle();
        motorcycle2.setId(2L);
        motorcycle2.setLicensePlate("M456");
        motorcycle2.setType("SPORT");

        when(reactiveMotorcycleService.findAll()).thenReturn(Flux.just(motorcycle1, motorcycle2));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/motorcycles")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Motorcycle.class)
                .hasSize(2);
    }

    @Test
    public void testGetMotorcycleById() {
        // Create a test motorcycle
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId(1L);
        motorcycle.setLicensePlate("M123");
        motorcycle.setModel("Test Model");
        motorcycle.setType("CRUISER");

        when(reactiveMotorcycleService.findById(1L)).thenReturn(Mono.just(motorcycle));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/motorcycles/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Motorcycle.class)
                .value(m -> m.getId().equals(1L))
                .value(m -> m.getLicensePlate().equals("M123"));
    }

    @Test
    public void testCreateMotorcycle() {
        // Create a test motorcycle
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setLicensePlate("MNEW123");
        motorcycle.setModel("New Model");
        motorcycle.setType("TOURING");

        Motorcycle savedMotorcycle = new Motorcycle();
        savedMotorcycle.setId(1L);
        savedMotorcycle.setLicensePlate("MNEW123");
        savedMotorcycle.setModel("New Model");
        savedMotorcycle.setType("TOURING");

        when(reactiveMotorcycleService.addMotorcycle(any(Motorcycle.class))).thenReturn(Mono.just(savedMotorcycle));

        // Test the endpoint
        webTestClient.post()
                .uri("/api/v2/motorcycles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(motorcycle)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Motorcycle.class)
                .value(m -> m.getId().equals(1L));
    }

    @Test
    public void testUpdateMotorcycle() {
        // Create a test motorcycle
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId(1L);
        motorcycle.setLicensePlate("MUPD123");
        motorcycle.setModel("Updated Model");
        motorcycle.setType("SPORT");

        when(reactiveMotorcycleService.updateMotorcycle(any(Motorcycle.class))).thenReturn(Mono.just(motorcycle));

        // Test the endpoint
        webTestClient.put()
                .uri("/api/v2/motorcycles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(motorcycle)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Motorcycle.class)
                .value(m -> m.getLicensePlate().equals("MUPD123"))
                .value(m -> m.getModel().equals("Updated Model"));
    }

    @Test
    public void testDeleteMotorcycle() {
        when(reactiveMotorcycleService.deleteById(anyLong())).thenReturn(Mono.empty());

        // Test the endpoint
        webTestClient.delete()
                .uri("/api/v2/motorcycles/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void testGetMotorcyclesByType() {
        // Create test motorcycles of the same type
        Motorcycle motorcycle1 = new Motorcycle();
        motorcycle1.setId(1L);
        motorcycle1.setLicensePlate("M111");
        motorcycle1.setType("CRUISER");

        Motorcycle motorcycle2 = new Motorcycle();
        motorcycle2.setId(2L);
        motorcycle2.setLicensePlate("M222");
        motorcycle2.setType("CRUISER");

        when(reactiveMotorcycleService.findByType("CRUISER")).thenReturn(Flux.just(motorcycle1, motorcycle2));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/motorcycles/type/CRUISER")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Motorcycle.class)
                .hasSize(2)
                .contains(motorcycle1, motorcycle2);
    }
}
