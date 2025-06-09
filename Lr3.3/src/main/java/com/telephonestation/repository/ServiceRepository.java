package com.telephonestation.repository;

import com.telephonestation.model.Service;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ServiceRepository {
    private final List<Service> services = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Service save(Service service) {
        if (service.getId() == null) {
            service.setId(idGenerator.getAndIncrement());
        }
        services.removeIf(s -> s.getId().equals(service.getId()));
        services.add(service);
        return service;
    }

    public Optional<Service> findById(Long id) {
        return services.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public List<Service> findAll() {
        return new ArrayList<>(services);
    }
}