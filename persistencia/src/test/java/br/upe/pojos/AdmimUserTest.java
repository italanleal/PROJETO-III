package br.upe.pojos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminUserTest {

    private AdminUser adminUser;

    @BeforeEach
    public void setUp() {
        adminUser = new AdminUser();
    }

    @Test
    void testSetAndGetEmail() {
        String email = "admin@example.com";
        adminUser.setEmail(email);
        assertEquals(email, adminUser.getEmail(), "O email não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetName() {
        String name = "Admin User";
        adminUser.setName(name);
        assertEquals(name, adminUser.getName(), "O nome não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetPassword() {
        String password = "adminpassword123";
        adminUser.setPassword(password);
        assertEquals(password, adminUser.getPassword(), "A senha não foi configurada corretamente.");
    }

    @Test
    void testSetAndGetUuid() {
        UUID uuid = UUID.randomUUID();
        adminUser.setUuid(uuid);
        assertEquals(uuid, adminUser.getUuid(), "O UUID não foi configurado corretamente.");
    }

    @Test
    void testAddSubscription() {
        Subscription subscription = new Subscription();
        adminUser.setSubscriptions(List.of(subscription));
        adminUser.addSubscription(subscription);

        Collection<Subscription> subscriptions = adminUser.getSubscriptions();
        assertTrue(subscriptions.contains(subscription), "A assinatura não foi adicionada corretamente.");
    }

    @Test
    void testGetSubscriptions() {
        Subscription subscription1 = new Subscription();
        Subscription subscription2 = new Subscription();
        adminUser.setSubscriptions(Arrays.asList(subscription1, subscription2));

        Collection<Subscription> subscriptions = adminUser.getSubscriptions();
        assertEquals(2, subscriptions.size(), "O número de assinaturas não é o esperado.");
        assertTrue(subscriptions.contains(subscription1), "A assinatura não foi encontrada.");
        assertTrue(subscriptions.contains(subscription2), "A assinatura não foi encontrada.");
    }

    @Test
    void testAddEvent() {
        GreatEvent event = new GreatEvent();
        adminUser.setEvents(List.of(event));
        adminUser.addEvent(event);

        Collection<GreatEvent> events = adminUser.getEvents();
        assertTrue(events.contains(event), "O evento não foi adicionado corretamente.");
    }

    @Test
    void testGetEvents() {
        GreatEvent event1 = new GreatEvent();
        GreatEvent event2 = new GreatEvent();
        adminUser.setEvents(Arrays.asList(event1, event2));

        Collection<GreatEvent> events = adminUser.getEvents();
        assertEquals(2, events.size(), "O número de eventos não é o esperado.");
        assertTrue(events.contains(event1), "O evento não foi encontrado.");
        assertTrue(events.contains(event2), "O evento não foi encontrado.");
    }

    @Test
    void testIsAdmin() {
        assertTrue(adminUser.isAdmin(), "O usuário deveria ser um administrador.");
    }
}
