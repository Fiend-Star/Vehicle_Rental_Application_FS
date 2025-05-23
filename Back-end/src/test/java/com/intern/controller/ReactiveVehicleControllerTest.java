package com.intern.controller;

import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.carRental.primary.vehicletypes.Car;
import com.intern.exception.ResourceNotFoundException;
import com.intern.service.ReactiveVehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for ReactiveVehicleController.
 * Tests the controller's behavior in isolation using WebTestClient.
 */
@ExtendWith(MockitoExtension.class)
class ReactiveVehicleControllerTest {

    @Mock
    private ReactiveVehicleService vehicleService;

    @InjectMocks
    private ReactiveVehicleController vehicleController;

    private WebTestClient webTestClient;
    private Vehicle testVehicle;

    @BeforeEach
    void setUp() {
        // Initialize WebTestClient
        webTestClient = WebTestClient.bindToController(vehicleController).build();

        // Set up test data
        testVehicle = new Car();
        testVehicle.setId(1L);
        testVehicle.setNumberPlate("TEST123");
        testVehicle.setStockNumber("STOCK123");
        testVehicle.setModel("Test Model");
        testVehicle.setMake("Test Make");
        testVehicle.setYearOfManufacture(2023);
        testVehicle.setPassengerCapacity(5);
        testVehicle.setMileage(1000.0);
    }

    @Test
    @DisplayName("Should get all vehicles")
    void testGetAllVehicles() {
        // Given
        Vehicle vehicle2 = new Car();
        vehicle2.setId(2L);
        vehicle2.setNumberPlate("TEST456");
        
        when(vehicleService.getAllVehicles()).thenReturn(Flux.just(testVehicle, vehicle2));

        // When/Then
        webTestClient.get()
                .uri("/api/v2/vehicles")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Vehicle.class)
                .hasSize(2)
                .contains(testVehicle, vehicle2);

        verify(vehicleService).getAllVehicles();
    }

    @Test
    @DisplayName("Should get vehicle by ID")
    void testGetVehicleById() {
        // Given
        when(vehicleService.getVehicleById(1L)).thenReturn(Mono.just(testVehicle));

        // When/Then
        webTestClient.get()
                .uri("/api/v2/vehicles/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vehicle.class)
                .isEqualTo(testVehicle);

        verify(vehicleService).getVehicleById(1L);
    }

    @Test
    @DisplayName("Should return 404 when vehicle not found")
    void testGetVehicleByIdNotFound() {
        // Given
        when(vehicleService.getVehicleById(999L)).thenReturn(Mono.error(new ResourceNotFoundException("Vehicle not found")));

        // When/Then
        webTestClient.get()
                .uri("/api/v2/vehicles/999")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();

        verify(vehicleService).getVehicleById(999L);
    }

    @Test
    @DisplayName("Should create a new vehicle")
    void testCreateVehicle() {
        // Given
        Map<String, Object> vehicleData = new HashMap<>();
        vehicleData.put("vtype", "Car");
        vehicleData.put("numberPlate", "NEW123");
        vehicleData.put("stockNumber", "STOCK456");
        vehicleData.put("passengerCapacity", "5");
        vehicleData.put("make", "New Make");
        vehicleData.put("model", "New Model");
        vehicleData.put("yearOfManufacture", "2023");
        vehicleData.put("mileage", "500");

        Vehicle newVehicle = new Car();
        newVehicle.setNumberPlate("NEW123");
        newVehicle.setStockNumber("STOCK456");
        newVehicle.setPassengerCapacity(5);
        newVehicle.setMake("New Make");
        newVehicle.setModel("New Model");
        newVehicle.setYearOfManufacture(2023);
        newVehicle.setMileage(500.0);

        when(vehicleService.addVehicle(any(Vehicle.class))).thenReturn(Mono.just(newVehicle));

        // When/Then
        webTestClient.post()
                .uri("/api/v2/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(vehicleData)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Vehicle.class);

        verify(vehicleService).addVehicle(any(Vehicle.class));
    }

    @Test
    @DisplayName("Should update a vehicle")
    void testUpdateVehicle() {
        // Given
        when(vehicleService.updateVehicle(any(Vehicle.class))).thenReturn(Mono.just(testVehicle));

        // When/Then
        webTestClient.put()
                .uri("/api/v2/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testVehicle)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vehicle.class)
                .isEqualTo(testVehicle);

        verify(vehicleService).updateVehicle(any(Vehicle.class));
    }

    @Test
    @DisplayName("Should delete a vehicle")
    void testDeleteVehicle() {
        // Given
        when(vehicleService.removeVehicle(anyString())).thenReturn(Mono.empty());

        // When/Then
        webTestClient.delete()
                .uri("/api/v2/vehicles/TEST123")
                .exchange()
                .expectStatus().isNoContent();

        verify(vehicleService).removeVehicle("TEST123");
    }

    @Test
    @DisplayName("Should search vehicles by model")
    void testSearchByModel() {
        // Given
        when(vehicleService.searchByModel(anyString())).thenReturn(Flux.just(testVehicle));

        // When/Then
        webTestClient.get()
                .uri("/api/v2/vehicles/search/model/Test Model")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Vehicle.class)
                .hasSize(1)
                .contains(testVehicle);

        verify(vehicleService).searchByModel("Test Model");
    }

    @Test
    @DisplayName("Should search vehicles by make")
    void testSearchByMake() {
        // Given
        when(vehicleService.searchByMake(anyString())).thenReturn(Flux.just(testVehicle));

        // When/Then
        webTestClient.get()
                .uri("/api/v2/vehicles/search/make/Test Make")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Vehicle.class)
                .hasSize(1)
                .contains(testVehicle);

        verify(vehicleService).searchByMake("Test Make");
    }
}
