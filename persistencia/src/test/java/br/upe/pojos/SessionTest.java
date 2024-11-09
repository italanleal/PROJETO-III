package br.upe.pojos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionTest {

    private Session session;
    private Subscription subscription;

    @BeforeEach
    public void setUp() {
        session = new Session();
        subscription = new Subscription();
    }

    @Test
    void testSetAndGetDescritor() {
        String descritor = "Sessão de Evento";
        session.setDescritor(descritor);
        assertEquals(descritor, session.getDescritor(), "O descritor não foi configurado corretamente.");
    }

    @Test
    void testAddAndGetSubscriptions() {
        Collection<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription);
        session.setSubscriptions(subscriptions);

        assertTrue(session.getSubscriptions().contains(subscription), "A inscrição não foi adicionada corretamente.");
    }

    @Test
    void testSetAndGetUuid() {
        UUID uuid = UUID.randomUUID();
        session.setUuid(uuid);
        assertEquals(uuid, session.getUuid(), "O UUID não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetEventUuid() {
        UUID eventUuid = UUID.randomUUID();
        session.setEventUuid(eventUuid);
        assertEquals(eventUuid, session.getEventUuid(), "O Event UUID não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetStartDate() {
        Date startDate = new Date();
        session.setStartDate(startDate);
        assertEquals(startDate, session.getStartDate(), "A data de início não foi configurada corretamente.");
    }

    @Test
    void testSetAndGetEndDate() {
        Date endDate = new Date();
        session.setEndDate(endDate);
        assertEquals(endDate, session.getEndDate(), "A data de término não foi configurada corretamente.");
    }
}
