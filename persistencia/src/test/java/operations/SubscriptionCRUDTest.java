package operations;

import br.upe.operations.SubscriptionCRUD;
import br.upe.pojos.Subscription;
import org.junit.jupiter.api.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.time.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


class SubscriptionCRUDTest {
    private SubscriptionCRUD subscriptionCRUD;
    private static final String STATE_PATH = ".\\state";
    private static final String SUBSCRIPTIONS_PATH = STATE_PATH+"\\subscriptions.csv";
    private static final Logger logger = Logger.getLogger(SubscriptionCRUDTest.class.getName());

    @BeforeEach
    public void setup() {
        subscriptionCRUD = new SubscriptionCRUD();
    }

    @AfterEach
    void clearFiles() {
        try {
            Files.deleteIfExists(Paths.get(SUBSCRIPTIONS_PATH));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when trying to delete files", e);
        }

        try {
            Files.deleteIfExists(Paths.get(STATE_PATH));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when trying to delete directory", e);
        }
    }

    @Test
    void testCreateSubscription() throws IOException {
        Subscription subscription = new Subscription();
        subscription.setUuid(UUID.randomUUID());
        subscription.setSessionUuid(UUID.randomUUID());
        subscription.setUserUuid(UUID.randomUUID());
        subscription.setDate(Date.from(Instant.now()));
        subscriptionCRUD.createSubscription(subscription);

        List<String> lines = Files.readAllLines(Paths.get(SUBSCRIPTIONS_PATH));
        assertEquals(1, lines.size());
        assertTrue(lines.getFirst().contains(subscription.getUuid().toString()));
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

        List<String> lines = Files.readAllLines(Paths.get(SUBSCRIPTIONS_PATH));
        assertEquals(1, lines.size());
        assertFalse(lines.getFirst().contains(uuidToDelete.toString()));
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

        List<String> lines = Files.readAllLines(Paths.get(SUBSCRIPTIONS_PATH));
        assertEquals(1, lines.size());
        assertTrue(lines.getFirst().contains(updated.getDate().toInstant().toString()));
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
