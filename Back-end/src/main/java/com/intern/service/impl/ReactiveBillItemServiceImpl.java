package com.intern.service.impl;

import com.intern.carRental.primary.BillItem;
import com.intern.repository.ReactiveBillItemRepository;
import com.intern.service.ReactiveBillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ReactiveBillItemService interface.
 */
@Service
public class ReactiveBillItemServiceImpl implements ReactiveBillItemService {

    private final ReactiveBillItemRepository billItemRepository;

    @Autowired
    public ReactiveBillItemServiceImpl(ReactiveBillItemRepository billItemRepository) {
        this.billItemRepository = billItemRepository;
    }

    @Override
    public Flux<BillItem> findAll() {
        return billItemRepository.findAll();
    }

    @Override
    public Mono<BillItem> findById(Long id) {
        return billItemRepository.findById(id);
    }

    @Override
    public Mono<BillItem> save(BillItem billItem) {
        return billItemRepository.save(billItem);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return billItemRepository.deleteById(id);
    }

    @Override
    public Flux<BillItem> findByBillId(Long billId) {
        return billItemRepository.findByBillId(billId);
    }

    @Override
    public Mono<Double> calculateTotalAmountByBillId(Long billId) {
        return billItemRepository.calculateTotalAmountByBillId(billId);
    }

    @Override
    public Flux<BillItem> findByItemType(String itemType) {
        return billItemRepository.findByItemType(itemType);
    }

    @Override
    public Mono<BillItem> addToBill(Long billId, BillItem billItem) {
        billItem.setBillId(billId);
        return billItemRepository.save(billItem);
    }
}
