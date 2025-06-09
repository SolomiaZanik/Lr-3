package com.telephonestation.controller;

import com.telephonestation.model.Service;
import com.telephonestation.model.Subscriber;
import com.telephonestation.repository.ServiceRepository;
import com.telephonestation.repository.SubscriberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    private final ServiceRepository serviceRepository;
    private final SubscriberRepository subscriberRepository;

    public ServiceController(ServiceRepository serviceRepository, SubscriberRepository subscriberRepository) {
        this.serviceRepository = serviceRepository;
        this.subscriberRepository = subscriberRepository;
    }

    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @PostMapping("/{serviceId}/subscribers/{subscriberId}")
    public ResponseEntity<Subscriber> assignServiceToSubscriber(@PathVariable Long serviceId, @PathVariable Long subscriberId) {
        Optional<Service> serviceOpt = serviceRepository.findById(serviceId);
        Optional<Subscriber> subscriberOpt = subscriberRepository.findById(subscriberId);

        if (serviceOpt.isEmpty() || subscriberOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Subscriber subscriber = subscriberOpt.get();
        if (subscriber.isBlocked()) {
            return ResponseEntity.badRequest().build();
        }

        subscriber.getServices().add(serviceOpt.get());
        Subscriber updatedSubscriber = subscriberRepository.save(subscriber);
        return ResponseEntity.ok(updatedSubscriber);
    }
}