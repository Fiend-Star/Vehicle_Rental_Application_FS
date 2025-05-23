package com.intern.repository;

import com.intern.carRental.primary.BillItem;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for BillItem entities.
 * Provides reactive operations for BillItem data access.
 */
@Repository
public interface ReactiveBillItemRepository extends ReactiveBaseRepository<BillItem, Long> {
    
    /**
     * Find bill items by bill ID
     * 
     * @param billId the ID of the bill
     * @return a Flux emitting bill items for the specified bill
     */
    Flux<BillItem> findByBillId(Long billId);
    
    /**
     * Calculate the total amount for a bill
     * 
     * @param billId the ID of the bill
     * @return a Mono emitting the total amount
     */
    Mono<Double> calculateTotalAmountByBillId(Long billId);
    
    /**
     * Find bill items by item type
     * 
     * @param itemType the type of the item
     * @return a Flux emitting bill items of the specified type
     */
    Flux<BillItem> findByItemType(String itemType);
}
