package br.upe.pojos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class EventComponentTest {

    private EventComponentImpl eventComponent;

    @BeforeEach
    public void setUp() {
        eventComponent = new EventComponentImpl();
    }

    @Test
    void testGetAndSetUuid() {
        UUID uuid = UUID.randomUUID();
        eventComponent.setUuid(uuid);
        assertEquals(uuid, eventComponent.getUuid());
    }

    @Test
    void testGetAndSetEventUuid() {
        UUID eventUuid = UUID.randomUUID();
        eventComponent.setEventUuid(eventUuid);
        assertEquals(eventUuid, eventComponent.getEventUuid());
    }

    @Test
    void testGetAndSetDescritor() {
        String descritor = "Evento Teste";
        eventComponent.setDescritor(descritor);
        assertEquals(descritor, eventComponent.getDescritor());
    }
}
