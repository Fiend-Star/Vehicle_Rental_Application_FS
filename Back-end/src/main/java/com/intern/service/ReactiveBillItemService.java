package com.intern.service;

import com.intern.carRental.primary.BillItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for reactive operations on BillItem entities.
 */
public interface ReactiveBillItemService {
    
    /**
     * Retrieve all bill items.
     * 
     * @return Flux of all bill items
     */
    Flux<BillItem> findAll();
    
    /**
     * Find a bill item by its ID.
     * 
     * @param id the bill item ID
     * @return Mono containing the found bill item or empty
     */
    Mono<BillItem> findById(Long id);
    
    /**
     * Save a new or updated bill item.
     * 
     * @param billItem the bill item to save
     * @return Mono containing the saved bill item
     */
    Mono<BillItem> save(BillItem billItem);
    
    /**
     * Delete a bill item by its ID.
     * 
     * @param id the bill item ID to delete
     * @return Mono completing when deletion is done
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * Find bill items by bill ID.
     * 
     * @param billId the bill ID
     * @return Flux of bill items for the specified bill
     */
    Flux<BillItem> findByBillId(Long billId);
    
    /**
     * Calculate the total amount for a bill.
     * 
     * @param billId the bill ID
     * @return Mono containing the total amount
     */
    Mono<Double> calculateTotalAmountByBillId(Long billId);
    
    /**
     * Find bill items by item type.
     * 
     * @param itemType the item type
     * @return Flux of bill items with the specified type
     */
    Flux<BillItem> findByItemType(String itemType);
    
    /**
     * Add a bill item to a bill.
     * 
     * @param billId the bill ID
     * @param billItem the bill item to add
     * @return Mono containing the saved bill item
     */
    Mono<BillItem> addToBill(Long billId, BillItem billItem);
}
