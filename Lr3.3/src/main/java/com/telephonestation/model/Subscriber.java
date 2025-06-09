package com.telephonestation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Subscriber {
    private Long id;
    private String name;
    private boolean isBlocked;
    private List<Service> services;

    public Subscriber() {
        this.services = new ArrayList<>();
    }

    public Subscriber(Long id, String name) {
        this.id = id;
        this.name = name;
        this.isBlocked = false;
        this.services = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}