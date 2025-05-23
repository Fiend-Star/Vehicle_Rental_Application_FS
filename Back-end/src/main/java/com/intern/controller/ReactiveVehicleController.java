package com.intern.controller;

import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.carRental.primary.vehicletypes.*;
import com.intern.service.ReactiveVehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Reactive REST controller for managing vehicles.
 * Provides endpoints for CRUD operations on vehicles using reactive programming.
 */
@RestController
@RequestMapping("/api/v2/vehicles")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class ReactiveVehicleController {

    private final ReactiveVehicleService vehicleService;

    /**
     * Get all vehicles
     *
     * @return a Flux of all vehicles
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Vehicle> getAllVehicles() {
        log.debug("REST request to get all Vehicles");
        return vehicleService.getAllVehicles();
    }

    /**
     * Get a specific vehicle by ID
     *
     * @param id the ID of the vehicle
     * @return a Mono with the requested vehicle
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Vehicle>> getVehicle(@PathVariable Long id) {
        log.debug("REST request to get Vehicle : {}", id);
        return vehicleService.getVehicleById(id)
                .map(vehicle -> ResponseEntity.ok().body(vehicle))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new vehicle
     *
     * @param payload the vehicle data
     * @return a Mono with the created vehicle
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Vehicle> createVehicle(@RequestBody Map<String, Object> payload) {
        log.debug("REST request to save Vehicle : {}", payload);
        
        // Factory method to create the appropriate vehicle type
        return Mono.fromCallable(() -> createVehicleFromType(payload))
                .flatMap(vehicleService::addVehicle);
    }

    /**
     * Update an existing vehicle
     *
     * @param id the ID of the vehicle to update
     * @param vehicle the updated vehicle data
     * @return a Mono with the updated vehicle
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Vehicle>> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        log.debug("REST request to update Vehicle : {}, {}", id, vehicle);
        vehicle.setId(id);
        return vehicleService.updateVehicle(vehicle)
                .map(updatedVehicle -> ResponseEntity.ok().body(updatedVehicle));
    }

    /**
     * Delete a vehicle
     *
     * @param numberPlate the number plate of the vehicle to delete
     * @return a Mono with void result
     */
    @DeleteMapping("/{numberPlate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteVehicle(@PathVariable String numberPlate) {
        log.debug("REST request to delete Vehicle : {}", numberPlate);
        return vehicleService.removeVehicle(numberPlate);
    }

    /**
     * Search vehicles by model
     *
     * @param model the model to search for
     * @return a Flux of matching vehicles
     */
    @GetMapping(value = "/search/model/{model}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Vehicle> searchByModel(@PathVariable String model) {
        log.debug("REST request to search Vehicles by model: {}", model);
        return vehicleService.searchByModel(model);
    }

    /**
     * Search vehicles by make
     *
     * @param make the make to search for
     * @return a Flux of matching vehicles
     */
    @GetMapping(value = "/search/make/{make}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Vehicle> searchByMake(@PathVariable String make) {
        log.debug("REST request to search Vehicles by make: {}", make);
        return vehicleService.searchByMake(make);
    }

    /**
     * Create a vehicle instance based on the provided type
     *
     * @param payload the vehicle data
     * @return a new Vehicle instance of the appropriate type
     */
    private Vehicle createVehicleFromType(Map<String, Object> payload) {
        String vtype = (String) payload.get("vtype");
        Vehicle vehicle;

        // Factory pattern for creating appropriate vehicle type
        switch (vtype) {
            case "Car":
                vehicle = new Car();
                break;
            case "Truck":
                vehicle = new Truck();
                break;
            case "SUV":
                vehicle = new SUV();
                break;
            case "Van":
                vehicle = new Van();
                break;
            case "Motorcycle":
                vehicle = new Motorcycle();
                break;
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vtype);
        }

        // Set common properties
        vehicle.setNumberPlate((String) payload.get("numberPlate"));
        vehicle.setStockNumber((String) payload.get("stockNumber"));
        vehicle.setPassengerCapacity(Integer.parseInt(String.valueOf(payload.get("passengerCapacity"))));
        vehicle.setMake((String) payload.get("make"));
        vehicle.setModel((String) payload.get("model"));
        vehicle.setYearOfManufacture(Integer.parseInt(String.valueOf(payload.get("yearOfManufacture"))));
        vehicle.setMileage(Double.parseDouble(String.valueOf(payload.get("mileage"))));
        
        return vehicle;
    }
}
