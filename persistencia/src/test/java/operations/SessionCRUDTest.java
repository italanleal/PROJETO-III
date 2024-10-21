package operations;

import br.upe.operations.SessionCRUD;
import br.upe.operations.SubscriptionCRUD;
import br.upe.operations.UserCRUD;
import br.upe.pojos.Session;
import br.upe.pojos.Subscription;
import org.junit.jupiter.api.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionCRUDTest {
    private static final String STATE_PATH = ".\\state";
    private static final String SUBSCRIPTIONS_PATH = STATE_PATH+"\\subscriptions.csv";
    private static final String SESSION_PATH = STATE_PATH+"\\sessions.csv";
    private static final Logger logger = Logger.getLogger(SessionCRUDTest.class.getName());
    private SessionCRUD sessionCRUD;
    private SubscriptionCRUD subscriptionCRUD;

    @AfterEach
    void clearFiles() {
        try {
            Files.deleteIfExists(Paths.get(SESSION_PATH));
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

    @BeforeEach
    public void setUp() {
        sessionCRUD = new SessionCRUD();
        subscriptionCRUD = new SubscriptionCRUD();
    }

    @Test
    void testCreateSession() {
        // Criar e salvar uma subscrição
        Subscription subscription = new Subscription();
        UUID subscriptionUuid = UUID.randomUUID();
        subscription.setUuid(subscriptionUuid);
        subscription.setSessionUuid(UUID.randomUUID());
        subscription.setUserUuid(UUID.randomUUID());
        subscription.setDate(new Date());
        subscriptionCRUD.createSubscription(subscription);

        // Criar e salvar uma sessão
        Session session = new Session();
        UUID sessionUuid = UUID.randomUUID();
        session.setUuid(sessionUuid);
        session.setEventUuid(UUID.randomUUID());
        session.setDescritor("Test Session");
        session.setStartDate(new Date());
        session.setEndDate(new Date());
        session.setSubscriptions(Collections.singletonList(subscription));
        sessionCRUD.createSession(session);

        // Recuperar a sessão
        Session retrievedSession = SessionCRUD.returnSession(sessionUuid);
        assertNotNull(retrievedSession, "A sessão retornada não deve ser nula.");
        assertEquals("Test Session", retrievedSession.getDescritor(), "O descritor da sessão deve ser igual ao esperado.");
        List<Subscription> retrievedSubscriptions = (List<Subscription>) retrievedSession.getSubscriptions();
        assertNotNull(retrievedSubscriptions, "A lista de subscrições não deve ser nula.");
        assertEquals(1, retrievedSubscriptions.size(), "A lista de subscrições deve conter um item.");
        assertEquals(subscription.getUuid(), retrievedSubscriptions.getFirst().getUuid(), "O UUID da subscrição deve ser igual ao esperado.");
    }

    @Test
    void testDeleteSession() {
        // Criar e salvar uma subscrição
        Subscription subscription = new Subscription();
        UUID subscriptionUuid = UUID.randomUUID();
        subscription.setUuid(subscriptionUuid);
        subscription.setSessionUuid(UUID.randomUUID());
        subscription.setUserUuid(UUID.randomUUID());
        subscription.setDate(new Date());
        subscriptionCRUD.createSubscription(subscription);

        // Criar e salvar uma sessão
        Session session = new Session();
        UUID sessionUuid = UUID.randomUUID();
        session.setUuid(sessionUuid);
        session.setEventUuid(UUID.randomUUID());
        session.setDescritor("Test Session");
        session.setStartDate(new Date());
        session.setEndDate(new Date());
        session.setSubscriptions(Collections.singletonList(subscription));
        sessionCRUD.createSession(session);

        // Excluir a sessão
        sessionCRUD.deleteSession(sessionUuid);

        // Recuperar a sessão
        Session retrievedSession = SessionCRUD.returnSession(sessionUuid);
        assertNull(retrievedSession, "A sessão retornada após a exclusão deve ser nula.");
    }

    @Test
    void testUpdateSession() {
        // Criar e salvar uma subscrição
        Subscription subscription = new Subscription();
        UUID subscriptionUuid = UUID.randomUUID();
        subscription.setUuid(subscriptionUuid);
        subscription.setSessionUuid(UUID.randomUUID());
        subscription.setUserUuid(UUID.randomUUID());
        subscription.setDate(new Date());
        subscriptionCRUD.createSubscription(subscription);

        // Criar e salvar uma sessão
        Session session = new Session();
        UUID sessionUuid = UUID.randomUUID();
        session.setUuid(sessionUuid);
        session.setEventUuid(UUID.randomUUID());
        session.setDescritor("Old Session");
        session.setStartDate(new Date());
        session.setEndDate(new Date());
        session.setSubscriptions(Collections.singletonList(subscription));
        sessionCRUD.createSession(session);

        // Atualizar a sessão
        Session updatedSession = new Session();
        updatedSession.setUuid(sessionUuid);
        updatedSession.setEventUuid(UUID.randomUUID());
        updatedSession.setDescritor("Updated Session");
        updatedSession.setStartDate(new Date());
        updatedSession.setEndDate(new Date());
        updatedSession.setSubscriptions(Collections.singletonList(subscription));
        sessionCRUD.updateSession(sessionUuid, updatedSession);

        // Recuperar a sessão atualizada
        Session retrievedSession = SessionCRUD.returnSession(sessionUuid);
        assertNotNull(retrievedSession, "A sessão retornada não deve ser nula.");
        assertEquals("Updated Session", retrievedSession.getDescritor(), "O descritor da sessão deve ser atualizado.");
        List<Subscription> retrievedSubscriptions = (List<Subscription>) retrievedSession.getSubscriptions();
        assertNotNull(retrievedSubscriptions, "A lista de subscrições não deve ser nula.");
        assertEquals(1, retrievedSubscriptions.size(), "A lista de subscrições deve conter um item.");
        assertEquals(subscription.getUuid(), retrievedSubscriptions.getFirst().getUuid(), "O UUID da subscrição deve ser igual ao esperado.");
    }

    @Test
    void testReturnSession() {
        // Criar e salvar uma subscrição
        Subscription subscription = new Subscription();
        UUID subscriptionUuid = UUID.randomUUID();
        subscription.setUuid(subscriptionUuid);
        subscription.setSessionUuid(UUID.randomUUID());
        subscription.setUserUuid(UUID.randomUUID());
        subscription.setDate(new Date());
        subscriptionCRUD.createSubscription(subscription);

        // Criar e salvar uma sessão
        Session session = new Session();
        UUID sessionUuid = UUID.randomUUID();
        session.setUuid(sessionUuid);
        session.setEventUuid(UUID.randomUUID());
        session.setDescritor("Session for Return Test");
        session.setStartDate(new Date());
        session.setEndDate(new Date());
        session.setSubscriptions(Collections.singletonList(subscription));
        sessionCRUD.createSession(session);

        // Recuperar a sessão
        Session retrievedSession = SessionCRUD.returnSession(sessionUuid);

        // Verificar a sessão retornada
        assertNotNull(retrievedSession, "A sessão retornada não deve ser nula.");
        assertEquals("Session for Return Test", retrievedSession.getDescritor(), "O descritor da sessão deve ser igual ao esperado.");
        List<Subscription> retrievedSubscriptions = (List<Subscription>) retrievedSession.getSubscriptions();
        assertNotNull(retrievedSubscriptions, "A lista de subscrições não deve ser nula.");
        assertEquals(1, retrievedSubscriptions.size(), "A lista de subscrições deve conter um item.");
        assertEquals(subscription.getUuid(), retrievedSubscriptions.getFirst().getUuid(), "O UUID da subscrição deve ser igual ao esperado.");
    }
}
