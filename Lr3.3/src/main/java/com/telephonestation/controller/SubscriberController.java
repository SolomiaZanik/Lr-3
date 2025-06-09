package com.telephonestation.controller;

import com.telephonestation.model.Subscriber;
import com.telephonestation.repository.SubscriberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {
    private final SubscriberRepository repository;

    public SubscriberController(SubscriberRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Subscriber createSubscriber(@RequestBody Subscriber subscriber) {
        return repository.save(subscriber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscriber> getSubscriber(@PathVariable Long id) {
        Optional<Subscriber> subscriberOpt = repository.findById(id);
        return subscriberOpt.isPresent()
                ? ResponseEntity.ok(subscriberOpt.get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Subscriber> getAllSubscribers() {
        return repository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscriber> updateSubscriber(@PathVariable Long id, @RequestBody Subscriber updated) {
        Optional<Subscriber> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Subscriber existing = existingOpt.get();
        existing.setName(updated.getName());
        existing.setBlocked(updated.isBlocked());
        Subscriber savedSubscriber = repository.save(existing);
        return ResponseEntity.ok(savedSubscriber);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        Optional<Subscriber> subscriberOpt = repository.findById(id);
        if (subscriberOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}