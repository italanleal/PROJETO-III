package br.upe.operations;

import br.upe.pojos.GreatEvent;
import br.upe.pojos.KeeperInterface;
import org.junit.jupiter.api.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EventCRUDTest {
    private static final String STATE_PATH = ".\\state";
    private static final String EVENTS_PATH = STATE_PATH+"\\events.csv";
    private static final Logger logger = Logger.getLogger(EventCRUDTest.class.getName());
    private EventCRUD eventCRUD;

    @AfterEach
    void clearFiles() {
        try {
            Files.deleteIfExists(Paths.get(EVENTS_PATH));
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
        eventCRUD = CRUDInterface.newEventCRUD();
    }

    @Test
    void testCreateEvent(){
        GreatEvent event = KeeperInterface.createGreatEvent();
        UUID eventUUID = UUID.randomUUID();
        event.setUuid(eventUUID);
        event.setStartDate(new Date());
        event.setEndDate(new Date());
        event.setDirector("Jackson");
        event.setSessions(new ArrayList<>());
        event.setSubmissions(new ArrayList<>());
        eventCRUD.createEvent(event);

        GreatEvent retrievedEvent = eventCRUD.returnEvent(eventUUID);
        assertNotNull(retrievedEvent, "O evento retornado não deve ser null");
        assertEquals(eventUUID, retrievedEvent.getUuid(), "Os UUID dos eventos devem ser iguais");
    }

    @Test
    void testUpdateEvent(){
        GreatEvent event = KeeperInterface.createGreatEvent();
        UUID eventUUID = UUID.randomUUID();
        event.setUuid(eventUUID);
        event.setStartDate(new Date());
        event.setEndDate(new Date());
        event.setDirector("Jackson");
        event.setSessions(new ArrayList<>());
        event.setSubmissions(new ArrayList<>());
        eventCRUD.createEvent(event);

        GreatEvent aux = KeeperInterface.createGreatEvent();
        aux.setUuid(eventUUID);
        aux.setStartDate(new Date());
        aux.setEndDate(new Date());
        aux.setDirector("Elyda");
        aux.setSessions(new ArrayList<>());
        aux.setSubmissions(new ArrayList<>());
        eventCRUD.updateEvent(eventUUID, aux);

        GreatEvent retrievedEvent = eventCRUD.returnEvent(eventUUID);
        assertNotNull(retrievedEvent);
        assertEquals(eventUUID, retrievedEvent.getUuid(), "Os UUID dos eventos devem ser iguais");
        assertEquals("Elyda", retrievedEvent.getDirector(), "O Diretor do evento deve o esperado");
    }
    @Test
    void testDeleteEvent(){
        GreatEvent event = KeeperInterface.createGreatEvent();
        UUID eventUUID = UUID.randomUUID();
        event.setUuid(eventUUID);
        event.setStartDate(new Date());
        event.setEndDate(new Date());
        event.setDirector("Jackson");
        event.setSessions(new ArrayList<>());
        event.setSubmissions(new ArrayList<>());
        eventCRUD.createEvent(event);

        eventCRUD.deleteEvent(eventUUID);
        GreatEvent retrievedEvent = eventCRUD.returnEvent(eventUUID);
        assertNull(retrievedEvent,"O evento retornado após a exclusão deve ser nulo");
    }

    @Test
    void testReturnEventById() {
        GreatEvent event = KeeperInterface.createGreatEvent();
        UUID eventUUID = UUID.randomUUID();
        event.setUuid(eventUUID);
        event.setStartDate(new Date());
        event.setEndDate(new Date());
        event.setDirector("Jackson");
        event.setSessions(new ArrayList<>());
        event.setSubmissions(new ArrayList<>());

        eventCRUD.createEvent(event);
        GreatEvent retrievedEvent = eventCRUD.returnEvent(eventUUID);
        assertNotNull(retrievedEvent, "O evento retornado não deve ser null");
        assertEquals(eventUUID, retrievedEvent.getUuid(), "Os UUID dos eventos devem ser iguais");
        assertEquals("Jackson", retrievedEvent.getDirector(), "O Diretor do evento deve ser o esperado");
    }

    @Test
    void testReturnAllEvents(){
        GreatEvent event = KeeperInterface.createGreatEvent();
        UUID eventUUID = UUID.randomUUID();
        event.setUuid(eventUUID);
        event.setStartDate(new Date());
        event.setEndDate(new Date());
        event.setDirector("Jackson");
        event.setSessions(new ArrayList<>());
        event.setSubmissions(new ArrayList<>());
        eventCRUD.createEvent(event);

        GreatEvent aux = KeeperInterface.createGreatEvent();
        UUID auxUUID = UUID.randomUUID();
        aux.setUuid(auxUUID);
        aux.setStartDate(new Date());
        aux.setEndDate(new Date());
        aux.setDirector("Elyda");
        aux.setSessions(new ArrayList<>());
        aux.setSubmissions(new ArrayList<>());
        eventCRUD.createEvent(aux);

        Collection<GreatEvent> events = eventCRUD.returnEvent();
        assertNotNull(events, "Os eventos não devem ser nulos");
        Collection<UUID> uuids = new ArrayList<>();
        for (GreatEvent e : events) {
            uuids.add(e.getUuid());
        }
        assertEquals(2, uuids.size(), "Os eventos devem ser todos retornados");

    }

}

