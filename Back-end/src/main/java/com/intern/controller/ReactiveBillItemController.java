package com.intern.controller;

import com.intern.carRental.primary.BillItem;
import com.intern.service.ReactiveBillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing BillItem resources reactively.
 */
@RestController
@RequestMapping("/api/reactive/billitems")
public class ReactiveBillItemController {

    private final ReactiveBillItemService billItemService;

    @Autowired
    public ReactiveBillItemController(ReactiveBillItemService billItemService) {
        this.billItemService = billItemService;
    }

    /**
     * GET /api/reactive/billitems : Get all bill items.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bill items
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BillItem> getAllBillItems() {
        return billItemService.findAll();
    }

    /**
     * GET /api/reactive/billitems/:id : Get bill item by id.
     *
     * @param id the id of the bill item to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bill item,
     * or with status 404 (Not Found)
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<BillItem>> getBillItem(@PathVariable Long id) {
        return billItemService.findById(id)
                .map(billItem -> ResponseEntity.ok().body(billItem))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/reactive/billitems : Create a new bill item.
     *
     * @param billItem the bill item to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bill item
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BillItem> createBillItem(@RequestBody BillItem billItem) {
        return billItemService.save(billItem);
    }

    /**
     * PUT /api/reactive/billitems/:id : Update an existing bill item.
     *
     * @param id the id of the bill item to update
     * @param billItem the bill item to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bill item,
     * or with status 404 (Not Found) if the bill item couldn't be updated
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<BillItem>> updateBillItem(
            @PathVariable Long id,
            @RequestBody BillItem billItem) {
        billItem.setId(id);
        return billItemService.save(billItem)
                .map(result -> ResponseEntity.ok().body(result))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/reactive/billitems/:id : Delete a bill item.
     *
     * @param id the id of the bill item to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBillItem(@PathVariable Long id) {
        return billItemService.deleteById(id);
    }

    /**
     * GET /api/reactive/billitems/bill/:billId : Get bill items by bill id.
     *
     * @param billId the id of the bill
     * @return the ResponseEntity with status 200 (OK) and the list of bill items
     */
    @GetMapping(value = "/bill/{billId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BillItem> getBillItemsByBill(@PathVariable Long billId) {
        return billItemService.findByBillId(billId);
    }

    /**
     * GET /api/reactive/billitems/bill/:billId/total : Get the total amount for a bill.
     *
     * @param billId the id of the bill
     * @return the ResponseEntity with status 200 (OK) and the total amount
     */
    @GetMapping(value = "/bill/{billId}/total", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Double> getBillTotal(@PathVariable Long billId) {
        return billItemService.calculateTotalAmountByBillId(billId);
    }

    /**
     * GET /api/reactive/billitems/type/:itemType : Get bill items by item type.
     *
     * @param itemType the type of the item
     * @return the ResponseEntity with status 200 (OK) and the list of bill items
     */
    @GetMapping(value = "/type/{itemType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BillItem> getBillItemsByType(@PathVariable String itemType) {
        return billItemService.findByItemType(itemType);
    }

    /**
     * POST /api/reactive/billitems/bill/:billId : Add a bill item to a bill.
     *
     * @param billId the id of the bill
     * @param billItem the bill item to add
     * @return the ResponseEntity with status 201 (Created) and with body the new bill item
     */
    @PostMapping(value = "/bill/{billId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BillItem> addBillItemToBill(
            @PathVariable Long billId,
            @RequestBody BillItem billItem) {
        return billItemService.addToBill(billId, billItem);
    }
}
