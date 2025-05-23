package com.intern.controller;

import com.intern.carRental.primary.vehicletypes.Truck;
import com.intern.service.ReactiveTruckService;
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
 * Test class for ReactiveTruckController.
 */
@WebFluxTest(ReactiveTruckController.class)
@ActiveProfiles("test")
public class ReactiveTruckControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveTruckService reactiveTruckService;

    @Test
    public void testGetAllTrucks() {
        // Create test trucks
        Truck truck1 = new Truck();
        truck1.setId(1L);
        truck1.setLicensePlate("T123");
        truck1.setType("PICKUP");

        Truck truck2 = new Truck();
        truck2.setId(2L);
        truck2.setLicensePlate("T456");
        truck2.setType("DELIVERY");

        when(reactiveTruckService.findAll()).thenReturn(Flux.just(truck1, truck2));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/trucks")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Truck.class)
                .hasSize(2);
    }

    @Test
    public void testGetTruckById() {
        // Create a test truck
        Truck truck = new Truck();
        truck.setId(1L);
        truck.setLicensePlate("T123");
        truck.setModel("Test Model");
        truck.setType("PICKUP");

        when(reactiveTruckService.findById(1L)).thenReturn(Mono.just(truck));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/trucks/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Truck.class)
                .value(t -> t.getId().equals(1L))
                .value(t -> t.getLicensePlate().equals("T123"));
    }

    @Test
    public void testCreateTruck() {
        // Create a test truck
        Truck truck = new Truck();
        truck.setLicensePlate("TNEW123");
        truck.setModel("New Model");
        truck.setType("HEAVY_DUTY");

        Truck savedTruck = new Truck();
        savedTruck.setId(1L);
        savedTruck.setLicensePlate("TNEW123");
        savedTruck.setModel("New Model");
        savedTruck.setType("HEAVY_DUTY");

        when(reactiveTruckService.addTruck(any(Truck.class))).thenReturn(Mono.just(savedTruck));

        // Test the endpoint
        webTestClient.post()
                .uri("/api/v2/trucks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(truck)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Truck.class)
                .value(t -> t.getId().equals(1L));
    }

    @Test
    public void testUpdateTruck() {
        // Create a test truck
        Truck truck = new Truck();
        truck.setId(1L);
        truck.setLicensePlate("TUPD123");
        truck.setModel("Updated Model");
        truck.setType("BOX_TRUCK");

        when(reactiveTruckService.updateTruck(any(Truck.class))).thenReturn(Mono.just(truck));

        // Test the endpoint
        webTestClient.put()
                .uri("/api/v2/trucks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(truck)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Truck.class)
                .value(t -> t.getLicensePlate().equals("TUPD123"))
                .value(t -> t.getModel().equals("Updated Model"));
    }

    @Test
    public void testDeleteTruck() {
        when(reactiveTruckService.deleteById(anyLong())).thenReturn(Mono.empty());

        // Test the endpoint
        webTestClient.delete()
                .uri("/api/v2/trucks/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void testGetTrucksByType() {
        // Create test trucks of the same type
        Truck truck1 = new Truck();
        truck1.setId(1L);
        truck1.setLicensePlate("T111");
        truck1.setType("PICKUP");

        Truck truck2 = new Truck();
        truck2.setId(2L);
        truck2.setLicensePlate("T222");
        truck2.setType("PICKUP");

        when(reactiveTruckService.findByType("PICKUP")).thenReturn(Flux.just(truck1, truck2));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/trucks/type/PICKUP")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Truck.class)
                .hasSize(2)
                .contains(truck1, truck2);
    }
}
