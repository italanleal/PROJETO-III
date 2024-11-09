package br.upe.operations;

import br.upe.pojos.Subscription;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubscriptionCRUDTest {
    private SubscriptionCRUD subscriptionCRUD;
    private final String filePath = ".\\state\\subscriptions.csv";

    @BeforeAll
    public void setup() {
        subscriptionCRUD = new SubscriptionCRUD();
    }

    @BeforeEach
    public void cleanFile() throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
        Files.createFile(Paths.get(filePath));
    }

    @Test
    void testCreateSubscription() throws IOException {
        Subscription subscription = new Subscription();
        subscription.setUuid(UUID.randomUUID());
        subscription.setSessionUuid(UUID.randomUUID());
        subscription.setUserUuid(UUID.randomUUID());
        subscription.setDate(Date.from(Instant.now()));
        subscriptionCRUD.createSubscription(subscription);

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains(subscription.getUuid().toString()));
    }

    @Test
    void testDeleteSubscription() throws IOException {
        UUID uuidToDelete = UUID.randomUUID();
        Subscription subscription1 = new Subscription();
        subscription1.setUuid(uuidToDelete);
        subscription1.setSessionUuid(UUID.randomUUID());
        subscription1.setUserUuid(UUID.randomUUID());
        subscription1.setDate(Date.from(Instant.now()));
        Subscription subscription2 = new Subscription();
        subscription2.setUuid(UUID.randomUUID());
        subscription2.setSessionUuid(UUID.randomUUID());
        subscription2.setUserUuid(UUID.randomUUID());
        subscription2.setDate(Date.from(Instant.now()));
        subscriptionCRUD.createSubscription(subscription1);
        subscriptionCRUD.createSubscription(subscription2);

        subscriptionCRUD.deleteSubscription(uuidToDelete);

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        assertEquals(1, lines.size());
        assertFalse(lines.get(0).contains(uuidToDelete.toString()));
    }

    @Test
    void testUpdateSubscription() throws IOException {
        UUID uuidToUpdate = UUID.randomUUID();
        Subscription original = new Subscription();
        original.setUuid(uuidToUpdate);
        original.setSessionUuid(UUID.randomUUID());
        original.setUserUuid(UUID.randomUUID());
        original.setDate(Date.from(Instant.now()));
        Subscription updated = new Subscription();
        updated.setUuid(uuidToUpdate);
        updated.setSessionUuid(UUID.randomUUID());
        updated.setUserUuid(UUID.randomUUID());
        updated.setDate(Date.from(Instant.now().plusSeconds(3600)));
        subscriptionCRUD.createSubscription(original);

        subscriptionCRUD.updateSubscription(uuidToUpdate, updated);

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains(updated.getDate().toInstant().toString()));
    }

    @Test
    void testReturnSubscriptionByUuid() {
        UUID uuidToReturn = UUID.randomUUID();
        Subscription subscription = new Subscription();
        subscription.setUuid(uuidToReturn);
        subscription.setSessionUuid(UUID.randomUUID());
        subscription.setUserUuid(UUID.randomUUID());
        subscription.setDate(Date.from(Instant.now()));
        subscriptionCRUD.createSubscription(subscription);

        Subscription returnedSubscription = SubscriptionCRUD.returnSubscription(uuidToReturn);
        assertNotNull(returnedSubscription);
        assertEquals(uuidToReturn, returnedSubscription.getUuid());
    }

    @Test
    void testReturnAllSubscriptions() {
        Subscription subscription1 = new Subscription();
        subscription1.setUuid(UUID.randomUUID());
        subscription1.setSessionUuid(UUID.randomUUID());
        subscription1.setUserUuid(UUID.randomUUID());
        subscription1.setDate(Date.from(Instant.now()));
        Subscription subscription2 = new Subscription();
        subscription2.setUuid(UUID.randomUUID());
        subscription2.setSessionUuid(UUID.randomUUID());
        subscription2.setUserUuid(UUID.randomUUID());
        subscription2.setDate(Date.from(Instant.now()));
        subscriptionCRUD.createSubscription(subscription1);
        subscriptionCRUD.createSubscription(subscription2);

        Collection<Subscription> subscriptions = SubscriptionCRUD.returnSubscription();
        assertEquals(2, subscriptions.size());
    }
}