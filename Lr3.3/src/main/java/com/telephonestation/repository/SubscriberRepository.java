package com.telephonestation.repository;

import com.telephonestation.model.Subscriber;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class SubscriberRepository {
    private final List<Subscriber> subscribers = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Subscriber save(Subscriber subscriber) {
        if (subscriber.getId() == null) {
            subscriber.setId(idGenerator.getAndIncrement());
        }
        subscribers.removeIf(s -> s.getId().equals(subscriber.getId()));
        subscribers.add(subscriber);
        return subscriber;
    }

    public Optional<Subscriber> findById(Long id) {
        return subscribers.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public List<Subscriber> findAll() {
        return new ArrayList<>(subscribers);
    }

    public void deleteById(Long id) {
        subscribers.removeIf(s -> s.getId().equals(id));
    }
}