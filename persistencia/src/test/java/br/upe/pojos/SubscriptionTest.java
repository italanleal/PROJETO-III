package br.upe.pojos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriptionTest {

    private Subscription subscription;

    @BeforeEach
    public void setUp() {
        subscription = new Subscription();
    }

    @Test
    void testSetAndGetUuid() {
        UUID uuid = UUID.randomUUID();
        subscription.setUuid(uuid);
        assertEquals(uuid, subscription.getUuid(), "O UUID não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetSessionUuid() {
        UUID sessionUuid = UUID.randomUUID();
        subscription.setSessionUuid(sessionUuid);
        assertEquals(sessionUuid, subscription.getSessionUuid(), "O Session UUID não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetUserUuid() {
        UUID userUuid = UUID.randomUUID();
        subscription.setUserUuid(userUuid);
        assertEquals(userUuid, subscription.getUserUuid(), "O User UUID não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetDate() {
        Date date = new Date();
        subscription.setDate(date);
        assertEquals(date, subscription.getDate(), "A data não foi configurada corretamente.");
    }
}
