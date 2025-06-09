package com.telephonestation.controller;

import com.telephonestation.model.Bill;
import com.telephonestation.model.Subscriber;
import com.telephonestation.repository.BillRepository;
import com.telephonestation.repository.SubscriberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    private final BillRepository billRepository;
    private final SubscriberRepository subscriberRepository;

    public BillController(BillRepository billRepository, SubscriberRepository subscriberRepository) {
        this.billRepository = billRepository;
        this.subscriberRepository = subscriberRepository;
    }

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        Optional<Subscriber> subscriberOpt = subscriberRepository.findById(bill.getSubscriberId());
        if (subscriberOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Subscriber subscriber = subscriberOpt.get();
        if (subscriber.isBlocked()) {
            return ResponseEntity.badRequest().build();
        }
        bill.setIssueDate(LocalDate.now());
        Bill savedBill = billRepository.save(bill);
        return ResponseEntity.ok(savedBill);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Bill> payBill(@PathVariable Long id) {
        Optional<Bill> billOpt = billRepository.findById(id);
        if (billOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Bill bill = billOpt.get();
        bill.setPaid(true);
        Bill updatedBill = billRepository.save(bill);
        return ResponseEntity.ok(updatedBill);
    }

    @GetMapping("/unpaid")
    public List<Bill> getUnpaidBills() {
        return billRepository.findUnpaidBills();
    }

    @PostMapping("/subscribers/{subscriberId}/block")
    public ResponseEntity<Subscriber> blockSubscriber(@PathVariable Long subscriberId) {
        Optional<Subscriber> subscriberOpt = subscriberRepository.findById(subscriberId);
        if (subscriberOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Subscriber subscriber = subscriberOpt.get();
        subscriber.setBlocked(true);
        Subscriber updatedSubscriber = subscriberRepository.save(subscriber);
        return ResponseEntity.ok(updatedSubscriber);
    }
}