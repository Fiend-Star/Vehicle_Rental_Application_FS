package com.intern.controller;

import com.intern.carRental.primary.vehicletypes.Car;
import com.intern.service.ReactiveCarService;
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
 * Test class for ReactiveCarController.
 */
@WebFluxTest(ReactiveCarController.class)
@ActiveProfiles("test")
public class ReactiveCarControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveCarService reactiveCarService;

    @Test
    public void testGetAllCars() {
        // Create test cars
        Car car1 = new Car();
        car1.setId(1L);
        car1.setNumberPlate("ABC123");
        car1.setType("ECONOMY");

        Car car2 = new Car();
        car2.setId(2L);
        car2.setNumberPlate("XYZ789");
        car2.setType("LUXURY");

        when(reactiveCarService.findAll()).thenReturn(Flux.just(car1, car2));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/cars")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Car.class)
                .hasSize(2);
    }

    @Test
    public void testGetCarById() {
        // Create a test car
        Car car = new Car();
        car.setId(1L);
        car.setNumberPlate("ABC123");
        car.setModel("Test Model");
        car.setType("ECONOMY");

        when(reactiveCarService.findById(1L)).thenReturn(Mono.just(car));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/cars/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Car.class)
                .value(c -> c.getId().equals(1L))
                .value(c -> c.getNumberPlate().equals("ABC123"));
    }

    @Test
    public void testCreateCar() {
        // Create a test car
        Car car = new Car();
        car.setNumberPlate("NEW123");
        car.setModel("New Model");
        car.setType("PREMIUM");

        Car savedCar = new Car();
        savedCar.setId(1L);
        savedCar.setNumberPlate("NEW123");
        savedCar.setModel("New Model");
        savedCar.setType("PREMIUM");

        when(reactiveCarService.addCar(any(Car.class))).thenReturn(Mono.just(savedCar));

        // Test the endpoint
        webTestClient.post()
                .uri("/api/v2/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(car)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Car.class)
                .value(c -> c.getId().equals(1L))
                .value(c -> c.getNumberPlate().equals("NEW123"));
    }

    @Test
    public void testUpdateCar() {
        // Create a test car for update
        Car car = new Car();
        car.setNumberPlate("UPD123");
        car.setModel("Updated Model");
        car.setType("LUXURY");

        Car updatedCar = new Car();
        updatedCar.setId(1L);
        updatedCar.setNumberPlate("UPD123");
        updatedCar.setModel("Updated Model");
        updatedCar.setType("LUXURY");

        when(reactiveCarService.updateCar(any(Car.class))).thenReturn(Mono.just(updatedCar));

        // Test the endpoint
        webTestClient.put()
                .uri("/api/v2/cars/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(car)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Car.class)
                .value(c -> c.getModel().equals("Updated Model"))
                .value(c -> c.getType().equals("LUXURY"));
    }

    @Test
    public void testDeleteCar() {
        when(reactiveCarService.deleteById(anyLong())).thenReturn(Mono.empty());

        // Test the endpoint
        webTestClient.delete()
                .uri("/api/v2/cars/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void testGetCarsByType() {
        // Create test cars
        Car car1 = new Car();
        car1.setId(1L);
        car1.setType("LUXURY");

        Car car2 = new Car();
        car2.setId(2L);
        car2.setType("LUXURY");

        when(reactiveCarService.findByType("LUXURY")).thenReturn(Flux.just(car1, car2));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/cars/type/LUXURY")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Car.class)
                .hasSize(2);
    }
}
