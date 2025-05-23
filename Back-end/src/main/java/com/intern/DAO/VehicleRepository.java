package com.intern.DAO;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.intern.carRental.primary.abstrct.Vehicle;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Deprecated // Use ReactiveVehicleRepository from com.intern.repository package instead
public interface VehicleRepository extends ReactiveCrudRepository<Vehicle, Long> {
	Mono<Vehicle> findByBarcode(String barcode);
	Mono<Void> deleteByBarcode(String barcode);
	Flux<Vehicle> findAllByModel(String model);
	Flux<Vehicle> findAllByCarRentalLocationId(Long id);
	
}
