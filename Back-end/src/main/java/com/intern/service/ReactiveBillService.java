package com.intern.service;

import com.intern.carRental.primary.Bill;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Service interface for reactive operations on Bill entities.
 */
public interface ReactiveBillService {
    
    /**
     * Retrieve all bills.
     * 
     * @return Flux of all bills
     */
    Flux<Bill> findAll();
    
    /**
     * Find a bill by its ID.
     * 
     * @param id the bill ID
     * @return Mono containing the found bill or empty
     */
    Mono<Bill> findById(Long id);
    
    /**
     * Save a new or updated bill.
     * 
     * @param bill the bill to save
     * @return Mono containing the saved bill
     */
    Mono<Bill> save(Bill bill);
    
    /**
     * Delete a bill by its ID.
     * 
     * @param id the bill ID to delete
     * @return Mono completing when deletion is done
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * Find bills by reservation ID.
     * 
     * @param reservationId the reservation ID
     * @return Flux of bills for the specified reservation
     */
    Flux<Bill> findByReservationId(Long reservationId);
    
    /**
     * Find bills that are paid.
     * 
     * @return Flux of paid bills
     */
    Flux<Bill> findPaidBills();
    
    /**
     * Find bills that are not paid.
     * 
     * @return Flux of unpaid bills
     */
    Flux<Bill> findUnpaidBills();
    
    /**
     * Find bill by invoice number.
     * 
     * @param invoiceNumber the invoice number
     * @return Mono containing the found bill
     */
    Mono<Bill> findByInvoiceNumber(String invoiceNumber);
    
    /**
     * Create a new bill for a reservation.
     * 
     * @param bill the bill details
     * @return Mono containing the created bill
     */
    Mono<Bill> createBill(Bill bill);
    
    /**
     * Mark a bill as paid.
     * 
     * @param id the bill ID
     * @param paymentDate the payment date
     * @return Mono containing the updated bill
     */
    Mono<Bill> markAsPaid(Long id, Date paymentDate);
}
