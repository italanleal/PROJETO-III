package br.upe.pojos;

import java.util.Collection;
import java.util.UUID;
import java.util.ArrayList;

public class ConcreteUser extends User{
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public void addSubscription(Subscription subscription) {
        if (this.subscriptions == null) {
            this.subscriptions = new ArrayList<>();
        }
        this.subscriptions.add(subscription);
    }

    @Override
    public Collection<Subscription> getSubscriptions() {
        return this.subscriptions;
    }

    @Override
    public void setSubscriptions(Collection<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }
}
