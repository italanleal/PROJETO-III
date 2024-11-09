package br.upe.pojos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GreatEventTest {

    private GreatEvent greatEvent;

    @BeforeEach
    public void setUp() {
        greatEvent = new GreatEvent();
    }

    @Test
    void testSetAndGetDescritor() {
        String descritor = "Evento Excepcional";
        greatEvent.setDescritor(descritor);
        assertEquals(descritor, greatEvent.getDescritor(), "O descritor não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetDirector() {
        String director = "João Silva";
        greatEvent.setDirector(director);
        assertEquals(director, greatEvent.getDirector(), "O diretor não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetStartDate() {
        Date startDate = new Date();
        greatEvent.setStartDate(startDate);
        assertEquals(startDate, greatEvent.getStartDate(), "A data de início não foi configurada corretamente.");
    }

    @Test
    void testSetAndGetEndDate() {
        Date endDate = new Date();
        greatEvent.setEndDate(endDate);
        assertEquals(endDate, greatEvent.getEndDate(), "A data de fim não foi configurada corretamente.");
    }

    @Test
    void testSetAndGetUuid() {
        UUID uuid = UUID.randomUUID();
        greatEvent.setUuid(uuid);
        assertEquals(uuid, greatEvent.getUuid(), "O UUID não foi configurado corretamente.");
    }

    @Test
    void testAddAndGetSessions() {
        Session session = new Session();
        greatEvent.setSessions(Arrays.asList(session));
        assertNotNull(greatEvent.getSessions(), "A coleção de sessões não deveria ser nula.");
        assertTrue(greatEvent.getSessions().contains(session), "A sessão não foi adicionada corretamente.");
    }

    @Test
    void testAddAndGetSubmissions() {
        Submission submission = new Submission();
        greatEvent.setSubmissions(Arrays.asList(submission));
        assertNotNull(greatEvent.getSubmissions(), "A coleção de submissões não deveria ser nula.");
        assertTrue(greatEvent.getSubmissions().contains(submission), "A submissão não foi adicionada corretamente.");
    }

    @Test
    void testAddSession() {
        Session session = new Session();
        greatEvent.setSessions(Arrays.asList(session));
        Session newSession = new Session();
        greatEvent.addSession(newSession);
        assertTrue(greatEvent.getSessions().contains(newSession), "A nova sessão não foi adicionada corretamente.");
    }

    @Test
    void testAddSubmission() {
        Submission submission = new Submission();
        greatEvent.setSubmissions(Arrays.asList(submission));
        Submission newSubmission = new Submission();
        greatEvent.addSubmission(newSubmission);
        assertTrue(greatEvent.getSubmissions().contains(newSubmission), "A nova submissão não foi adicionada corretamente.");
    }
}
