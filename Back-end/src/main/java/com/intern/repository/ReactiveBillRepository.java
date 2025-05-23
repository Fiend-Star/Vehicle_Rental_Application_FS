package com.intern.repository;

import com.intern.carRental.primary.Bill;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for Bill entities.
 * Provides reactive operations for Bill data access.
 */
@Repository
public interface ReactiveBillRepository extends ReactiveBaseRepository<Bill, Long> {
    
    /**
     * Find bills by account ID
     * 
     * @param accountId the ID of the account
     * @return a Flux emitting bills for the specified account
     */
    Flux<Bill> findByAccountId(Long accountId);
    
    /**
     * Find bills by reservation ID
     * 
     * @param reservationId the ID of the reservation
     * @return a Flux emitting bills for the specified reservation
     */
    Flux<Bill> findByReservationId(Long reservationId);
    
    /**
     * Find bills that are paid
     * 
     * @return a Flux emitting paid bills
     */
    Flux<Bill> findByIsPaidTrue();
    
    /**
     * Find bills that are not paid
     * 
     * @return a Flux emitting unpaid bills
     */
    Flux<Bill> findByIsPaidFalse();
    
    /**
     * Find bill by invoice number
     * 
     * @param invoiceNumber the invoice number
     * @return a Mono emitting the bill with the specified invoice number
     */
    Mono<Bill> findByInvoiceNumber(String invoiceNumber);
}
