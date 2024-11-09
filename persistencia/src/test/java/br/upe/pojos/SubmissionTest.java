package br.upe.pojos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubmissionTest {

    private Submission submission;

    @BeforeEach
    public void setUp() {
        // Inicializa a instância de Submission antes de cada teste
        submission = new Submission();
    }

    @Test
    void testSetAndGetDescritor() {
        String descritor = "Novo Evento";
        submission.setDescritor(descritor);
        assertEquals(descritor, submission.getDescritor(), "O descritor não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetUuid() {
        UUID uuid = UUID.randomUUID();
        submission.setUuid(uuid);
        assertEquals(uuid, submission.getUuid(), "O UUID não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetEventUuid() {
        UUID eventUuid = UUID.randomUUID();
        submission.setEventUuid(eventUuid);
        assertEquals(eventUuid, submission.getEventUuid(), "O Event UUID não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetUserUuid() {
        UUID userUuid = UUID.randomUUID();
        submission.setUserUuid(userUuid);
        assertEquals(userUuid, submission.getUserUuid(), "O User UUID não foi configurado corretamente.");
    }

    @Test
    void testSetAndGetDate() {
        Date date = new Date();
        submission.setDate(date);
        assertEquals(date, submission.getDate(), "A data não foi configurada corretamente.");
    }
}
