package com.intern.controller;

import com.intern.carRental.primary.Bill;
import com.intern.service.ReactiveBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * REST controller for managing Bill resources reactively.
 */
@RestController
@RequestMapping("/api/reactive/bills")
public class ReactiveBillController {

    private final ReactiveBillService billService;

    @Autowired
    public ReactiveBillController(ReactiveBillService billService) {
        this.billService = billService;
    }

    /**
     * GET /api/reactive/bills : Get all bills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bills
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Bill> getAllBills() {
        return billService.findAll();
    }

    /**
     * GET /api/reactive/bills/:id : Get bill by id.
     *
     * @param id the id of the bill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bill,
     * or with status 404 (Not Found)
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Bill>> getBill(@PathVariable Long id) {
        return billService.findById(id)
                .map(bill -> ResponseEntity.ok().body(bill))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/reactive/bills : Create a new bill.
     *
     * @param bill the bill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bill
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Bill> createBill(@RequestBody Bill bill) {
        return billService.createBill(bill);
    }

    /**
     * PUT /api/reactive/bills/:id : Update an existing bill.
     *
     * @param id the id of the bill to update
     * @param bill the bill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bill,
     * or with status 404 (Not Found) if the bill couldn't be updated
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Bill>> updateBill(
            @PathVariable Long id,
            @RequestBody Bill bill) {
        bill.setId(id);
        return billService.save(bill)
                .map(result -> ResponseEntity.ok().body(result))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/reactive/bills/:id : Delete a bill.
     *
     * @param id the id of the bill to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBill(@PathVariable Long id) {
        return billService.deleteById(id);
    }

    /**
     * GET /api/reactive/bills/reservation/:reservationId : Get bills by reservation id.
     *
     * @param reservationId the id of the reservation
     * @return the ResponseEntity with status 200 (OK) and the list of bills
     */
    @GetMapping(value = "/reservation/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Bill> getBillsByReservation(@PathVariable Long reservationId) {
        return billService.findByReservationId(reservationId);
    }

    /**
     * GET /api/reactive/bills/paid : Get all paid bills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paid bills
     */
    @GetMapping(value = "/paid", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Bill> getPaidBills() {
        return billService.findPaidBills();
    }

    /**
     * GET /api/reactive/bills/unpaid : Get all unpaid bills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of unpaid bills
     */
    @GetMapping(value = "/unpaid", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Bill> getUnpaidBills() {
        return billService.findUnpaidBills();
    }

    /**
     * PUT /api/reactive/bills/:id/markpaid : Mark a bill as paid.
     *
     * @param id the id of the bill to mark as paid
     * @return the ResponseEntity with status 200 (OK) and with body the updated bill
     */
    @PutMapping(value = "/{id}/markpaid", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Bill>> markBillAsPaid(@PathVariable Long id) {
        return billService.markAsPaid(id, new Date())
                .map(result -> ResponseEntity.ok().body(result))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
