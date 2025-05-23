package com.intern.controller;

import com.intern.carRental.primary.abstrct.Service;
import com.intern.primary.addonServices.Driver;
import com.intern.service.ReactiveServiceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
 * Test class for ReactiveServiceController.
 */
@WebFluxTest(ReactiveServiceController.class)
@ActiveProfiles("test")
public class ReactiveServiceControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveServiceService reactiveServiceService;

    @Test
    public void testGetAllServices() {
        // Create test services (using Driver as a concrete implementation for testing)
        Driver service1 = new Driver();
        service1.setId(1L);
        service1.setServiceId("SRV001");
        
        Driver service2 = new Driver();
        service2.setId(2L);
        service2.setServiceId("SRV002");

        when(reactiveServiceService.findAll()).thenReturn(Flux.just(service1, service2));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/services")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Service.class)
                .hasSize(2);
    }

    @Test
    public void testGetServiceById() {
        // Create a test service
        Driver service = new Driver();
        service.setId(1L);
        service.setServiceId("SRV001");
        service.setVehicleReservationId(100L);

        when(reactiveServiceService.findById(1L)).thenReturn(Mono.just(service));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/services/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Service.class)
                .value(s -> s.getId().equals(1L))
                .value(s -> s.getServiceId().equals("SRV001"));
    }

    @Test
    public void testCreateService() {
        // Create a test service
        Driver service = new Driver();
        service.setServiceId("NEWSRV");
        service.setVehicleReservationId(200L);

        Driver savedService = new Driver();
        savedService.setId(1L);
        savedService.setServiceId("NEWSRV");
        savedService.setVehicleReservationId(200L);

        when(reactiveServiceService.addService(any(Service.class))).thenReturn(Mono.just(savedService));

        // Test the endpoint
        webTestClient.post()
                .uri("/api/v2/services")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(service)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Service.class)
                .value(s -> s.getId().equals(1L));
    }

    @Test
    public void testUpdateService() {
        // Create a test service
        Driver service = new Driver();
        service.setId(1L);
        service.setServiceId("UPDSRV");
        service.setVehicleReservationId(300L);

        when(reactiveServiceService.updateService(any(Service.class))).thenReturn(Mono.just(service));

        // Test the endpoint
        webTestClient.put()
                .uri("/api/v2/services/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(service)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Service.class)
                .value(s -> s.getServiceId().equals("UPDSRV"))
                .value(s -> s.getVehicleReservationId().equals(300L));
    }

    @Test
    public void testDeleteService() {
        when(reactiveServiceService.deleteById(anyLong())).thenReturn(Mono.empty());

        // Test the endpoint
        webTestClient.delete()
                .uri("/api/v2/services/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void testGetServicesByReservationId() {
        // Create test services for the same reservation
        Driver service1 = new Driver();
        service1.setId(1L);
        service1.setServiceId("SRV001");
        service1.setVehicleReservationId(100L);

        Driver service2 = new Driver();
        service2.setId(2L);
        service2.setServiceId("SRV002");
        service2.setVehicleReservationId(100L);

        Long reservationId = 100L;

        when(reactiveServiceService.findByVehicleReservationId(reservationId)).thenReturn(Flux.just(service1, service2));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/services/reservation/" + reservationId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Service.class)
                .hasSize(2);
    }

    @Test
    public void testGetServicesByServiceId() {
        // Create test service with specific service ID
        Driver service = new Driver();
        service.setId(1L);
        service.setServiceId("SRV123");
        service.setVehicleReservationId(100L);

        String serviceId = "SRV123";

        when(reactiveServiceService.findByServiceId(serviceId)).thenReturn(Flux.just(service));

        // Test the endpoint
        webTestClient.get()
                .uri("/api/v2/services/serviceId/" + serviceId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Service.class)
                .hasSize(1);
    }
}
