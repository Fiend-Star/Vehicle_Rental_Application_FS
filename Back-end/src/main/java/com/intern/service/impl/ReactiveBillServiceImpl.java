package com.intern.service.impl;

import com.intern.carRental.primary.Bill;
import com.intern.repository.ReactiveBillRepository;
import com.intern.service.ReactiveBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Implementation of the ReactiveBillService interface.
 */
@Service
public class ReactiveBillServiceImpl implements ReactiveBillService {

    private final ReactiveBillRepository billRepository;

    @Autowired
    public ReactiveBillServiceImpl(ReactiveBillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Flux<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Mono<Bill> findById(Long id) {
        return billRepository.findById(id);
    }

    @Override
    public Mono<Bill> save(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return billRepository.deleteById(id);
    }

    @Override
    public Flux<Bill> findByReservationId(Long reservationId) {
        return billRepository.findByReservationId(reservationId);
    }

    @Override
    public Flux<Bill> findPaidBills() {
        return billRepository.findByIsPaidTrue();
    }

    @Override
    public Flux<Bill> findUnpaidBills() {
        return billRepository.findByIsPaidFalse();
    }

    @Override
    public Mono<Bill> findByInvoiceNumber(String invoiceNumber) {
        return billRepository.findByInvoiceNumber(invoiceNumber);
    }

    @Override
    public Mono<Bill> createBill(Bill bill) {
        // Set creation date and initial status
        bill.setCreationDate(new Date().getTime());
        bill.setStatus("PENDING");
        
        return billRepository.save(bill);
    }

    @Override
    public Mono<Bill> markAsPaid(Long id, Date paymentDate) {
        return billRepository.findById(id)
                .flatMap(bill -> {
                    bill.setStatus("PAID");
                    return billRepository.save(bill);
                });
    }
}
