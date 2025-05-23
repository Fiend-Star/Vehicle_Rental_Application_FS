package com.intern.controller;

import com.intern.carRental.primary.abstrct.Service;
import com.intern.service.ReactiveServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for Service operations.
 * Provides reactive endpoints for Service management.
 * Note: This controller handles the abstract Service class, 
 * which may have limited functionality. For full functionality,
 * use the specific service controllers like ReactiveDriverController.
 */
@RestController
@RequestMapping("/api/v2/services")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ReactiveServiceController {

    private final ReactiveServiceService reactiveServiceService;

    /**
     * Get all Services
     *
     * @return a Flux of all Services
     */
    @GetMapping
    public Flux<Service> getAllServices() {
        log.info("Getting all services");
        return reactiveServiceService.findAll();
    }

    /**
     * Get a Service by ID
     *
     * @param id the Service ID
     * @return a Mono of the Service with the specified ID
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Service>> getServiceById(@PathVariable Long id) {
        log.info("Getting service with ID: {}", id);
        return reactiveServiceService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new Service
     *
     * @param service the Service to create
     * @return a Mono of the created Service
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Service> createService(@RequestBody Service service) {
        log.info("Creating new service");
        return reactiveServiceService.addService(service);
    }

    /**
     * Update an existing Service
     *
     * @param id the Service ID
     * @param service the updated Service data
     * @return a Mono of the updated Service
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Service>> updateService(@PathVariable Long id, @RequestBody Service service) {
        log.info("Updating service with ID: {}", id);
        service.setId(id);
        return reactiveServiceService.updateService(service)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete a Service
     *
     * @param id the Service ID
     * @return a Mono of Void indicating completion
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteService(@PathVariable Long id) {
        log.info("Deleting service with ID: {}", id);
        return reactiveServiceService.deleteById(id);
    }

    /**
     * Get Services by vehicle reservation ID
     *
     * @param reservationId the vehicle reservation ID to filter by
     * @return a Flux of Services associated with the specified vehicle reservation
     */
    @GetMapping("/reservation/{reservationId}")
    public Flux<Service> getServicesByReservationId(@PathVariable Long reservationId) {
        log.info("Getting services for vehicle reservation ID: {}", reservationId);
        return reactiveServiceService.findByVehicleReservationId(reservationId);
    }

    /**
     * Get Services by service ID
     *
     * @param serviceId the service ID to filter by
     * @return a Flux of Services with the specified service ID
     */
    @GetMapping("/serviceId/{serviceId}")
    public Flux<Service> getServicesByServiceId(@PathVariable String serviceId) {
        log.info("Getting services with service ID: {}", serviceId);
        return reactiveServiceService.findByServiceId(serviceId);
    }
}
