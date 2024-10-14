package br.upe.pojos;

import java.util.Collection;
import java.util.UUID;

public class AdminUser extends User {
    private static final boolean IS_ADMIN = true;
    private Collection<GreatEvent> events;

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public UUID getUuid() {
        return this.uuid;
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }
    public Collection<Subscription> getSubscriptions() {
        return subscriptions;
    }
    public void setSubscriptions(Collection<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Collection<GreatEvent> getEvents() {
        return events;
    }
    public void setEvents(Collection<GreatEvent> events) {
        this.events = events;
    }
    public void addEvent(GreatEvent event) {
        this.events.add(event);
    }

    public boolean isAdmin() {
        return IS_ADMIN;
    }
}
