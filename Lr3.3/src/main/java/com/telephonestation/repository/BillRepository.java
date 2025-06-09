package com.telephonestation.repository;

import com.telephonestation.model.Bill;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class BillRepository {
    private final List<Bill> bills = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Bill save(Bill bill) {
        if (bill.getId() == null) {
            bill.setId(idGenerator.getAndIncrement());
        }
        bills.removeIf(b -> b.getId().equals(bill.getId()));
        bills.add(bill);
        return bill;
    }

    public Optional<Bill> findById(Long id) {
        return bills.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }

    public List<Bill> findBySubscriberId(Long subscriberId) {
        return bills.stream()
                .filter(b -> b.getSubscriberId().equals(subscriberId))
                .collect(Collectors.toList());
    }

    public List<Bill> findUnpaidBills() {
        return bills.stream()
                .filter(b -> !b.isPaid())
                .collect(Collectors.toList());
    }
}