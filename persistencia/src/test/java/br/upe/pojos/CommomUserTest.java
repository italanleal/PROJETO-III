package br.upe.pojos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommomUserTest {

    private CommomUser commonUser;

    @BeforeEach
    public void setUp() {
        commonUser = new CommomUser();
    }

    @Test
    void testSetAndGetEmail() {
        String email = "user@example.com";
        commonUser.setEmail(email);
        assertEquals(email, commonUser.getEmail(), "O email não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetName() {
        String name = "John Doe";
        commonUser.setName(name);
        assertEquals(name, commonUser.getName(), "O nome não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetPassword() {
        String password = "password123";
        commonUser.setPassword(password);
        assertEquals(password, commonUser.getPassword(), "A senha não foi configurada corretamente.");
    }

    @Test
    void testSetAndGetUuid() {
        UUID uuid = UUID.randomUUID();
        commonUser.setUuid(uuid);
        assertEquals(uuid, commonUser.getUuid(), "O UUID não foi configurado corretamente.");
    }

    @Test
    void testAddSubscription() {
        Subscription subscription = new Subscription();
        commonUser.setSubscriptions(Arrays.asList(subscription)); // Inicializa a coleção de subscriptions
        commonUser.addSubscription(subscription);

        Collection<Subscription> subscriptions = commonUser.getSubscriptions();
        assertTrue(subscriptions.contains(subscription), "A assinatura não foi adicionada corretamente.");
    }

    @Test
    void testGetSubscriptions() {
        Subscription subscription1 = new Subscription();
        Subscription subscription2 = new Subscription();
        commonUser.setSubscriptions(Arrays.asList(subscription1, subscription2));

        Collection<Subscription> subscriptions = commonUser.getSubscriptions();
        assertEquals(2, subscriptions.size(), "O número de assinaturas não é o esperado.");
        assertTrue(subscriptions.contains(subscription1), "A assinatura não foi encontrada.");
        assertTrue(subscriptions.contains(subscription2), "A assinatura não foi encontrada.");
    }

    @Test
    void testIsAdmin() {
        assertFalse(commonUser.isAdmin(), "O usuário não deveria ser um administrador.");
    }
}
