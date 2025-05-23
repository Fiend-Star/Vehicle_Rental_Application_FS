package com.intern.controller;

import com.intern.primary.addonServices.Driver;
import com.intern.service.ReactiveDriverService;
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
 * Test class for ReactiveDriverController.
 */
@WebFluxTest(ReactiveDriverController.class)
@ActiveProfiles("test")
public class ReactiveDriverControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveDriverService reactiveDriverService;

    @Test
    public void testGetAllDrivers() {
        // Create test drivers
        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setName("Driver 1");
        driver1.setCost(75.0);

        Driver driver2 = new Driver();
        driver2.setId(2L);
        driver2.setName("Driver 2");
        driver2.setCost(100.0);

        when(reactiveDriverService.findAll()).thenReturn(Flux.just(driver1, driver2));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/drivers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Driver.class)
                .hasSize(2);
    }

    @Test
    public void testGetDriverById() {
        // Create a test driver
        Driver driver = new Driver();
        driver.setId(1L);
        driver.setName("Test Driver");
        driver.setDescription("Professional driver");
        driver.setCost(80.0);

        when(reactiveDriverService.findById(1L)).thenReturn(Mono.just(driver));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/drivers/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Driver.class)
                .value(d -> d.getId().equals(1L))
                .value(d -> d.getName().equals("Test Driver"));
    }

    @Test
    public void testCreateDriver() {
        // Create a test driver
        Driver driver = new Driver();
        driver.setName("New Driver");
        driver.setDescription("Premium driver service");
        driver.setCost(120.0);

        Driver savedDriver = new Driver();
        savedDriver.setId(1L);
        savedDriver.setName("New Driver");
        savedDriver.setDescription("Premium driver service");
        savedDriver.setCost(120.0);

        when(reactiveDriverService.addDriver(any(Driver.class))).thenReturn(Mono.just(savedDriver));

        // Test the endpoint
        webTestClient.post()
                .uri("/api/v2/drivers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(driver)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Driver.class)
                .value(d -> d.getId().equals(1L));
    }

    @Test
    public void testUpdateDriver() {
        // Create a test driver
        Driver driver = new Driver();
        driver.setId(1L);
        driver.setName("Updated Driver");
        driver.setDescription("Updated description");
        driver.setCost(95.0);

        when(reactiveDriverService.updateDriver(any(Driver.class))).thenReturn(Mono.just(driver));

        // Test the endpoint
        webTestClient.put()
                .uri("/api/v2/drivers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(driver)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Driver.class)
                .value(d -> d.getName().equals("Updated Driver"))
                .value(d -> d.getCost().equals(95.0));
    }

    @Test
    public void testDeleteDriver() {
        when(reactiveDriverService.deleteById(anyLong())).thenReturn(Mono.empty());

        // Test the endpoint
        webTestClient.delete()
                .uri("/api/v2/drivers/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void testGetDriversByReservationId() {
        // Create test drivers for the same reservation
        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setName("Reservation Driver 1");

        Driver driver2 = new Driver();
        driver2.setId(2L);
        driver2.setName("Reservation Driver 2");

        Long reservationId = 100L;

        when(reactiveDriverService.findByVehicleReservationId(reservationId)).thenReturn(Flux.just(driver1, driver2));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/drivers/reservation/" + reservationId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Driver.class)
                .hasSize(2)
                .contains(driver1, driver2);
    }
}
