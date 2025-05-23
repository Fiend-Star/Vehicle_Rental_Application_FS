package com.intern.repository;

import com.intern.carRental.primary.ParkingStall;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for ParkingStall entities.
 * Provides reactive operations for ParkingStall data access.
 */
@Repository
public interface ReactiveParkingStallRepository extends ReactiveBaseRepository<ParkingStall, Long> {
    
    /**
     * Find a parking stall by its stall number
     *
     * @param stallNumber the stall number
     * @return a Mono emitting the found parking stall or empty if not found
     */
    Mono<ParkingStall> findByStallNumber(String stallNumber);
    
    /**
     * Find parking stalls by location identifier
     *
     * @param locationIdentifier the location identifier to search for
     * @return a Flux emitting parking stalls matching the location identifier
     */
    Flux<ParkingStall> findByLocationIdentifier(String locationIdentifier);
}
