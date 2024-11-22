package br.upe.dao;

import br.upe.entities.Event;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.util.logging.Logger;
class JDBCEventDAODiffblueTest {
    /**
     * Test {@link JDBCEventDAO#JDBCEventDAO(LambdaEntityManagerFactory)}.
     */
    private final JDBCEventDAO eventDAO = PersistenciaInterface.createJDBCEventDAO(PersistenciaInterface.getDevelopEMF_lambda());
    private final Logger logger = Logger.getLogger(JDBCEventDAODiffblueTest.class.getName());

    @Test
    @DisplayName("Test save method for eventDAO")
    public void saveEventTest() {
        Event event = PersistenciaInterface.createEvent();

        event.setDirector("TEST1");
        event.setTitle("TEST1");
        event.setDescription("TEST1");
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.now().plusDays(1));

        eventDAO.save(event);

        Assertions.assertNotEquals(eventDAO.findById(event.getId()), Optional.empty());
    }

    @Test
    @DisplayName("Test update method for eventDAO")
    public void updateEventTest() {
        Event event = PersistenciaInterface.createEvent();

        event.setDirector("TEST1");
        event.setTitle("TEST1");
        event.setDescription("TEST1");
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.now().plusDays(1));

        eventDAO.save(event);

        event.setDirector("TEST2");
        event.setTitle("TEST2");
        event.setDescription("TEST2");
        event.setStartDate(LocalDate.now().plusDays(1));
        event.setEndDate(LocalDate.now());
        eventDAO.update(event);

        Assertions.assertEquals(event.getTitle(), "TEST2");
    }

    @Test
    @DisplayName("Test delete method for eventDAO")
    public void deleteEventTest() {
        Event event = PersistenciaInterface.createEvent();

        event.setDirector("TEST1");
        event.setTitle("TEST1");
        event.setDescription("TEST1");
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.now().plusDays(1));

        eventDAO.save(event);

        Long id = event.getId();
        eventDAO.delete(event);
        Assertions.assertEquals(eventDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test deleteById method for eventDAO")
    public void deleteByIdEventTest() {
        Event event = PersistenciaInterface.createEvent();

        event.setDirector("TEST1");
        event.setTitle("TEST1");
        event.setDescription("TEST1");
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.now().plusDays(1));

        eventDAO.save(event);

        Long id = event.getId();
        eventDAO.deleteById(event.getId());
        Assertions.assertEquals(eventDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test findById method for eventDAO")
    public void findByIdEventTest() {
        Event event = PersistenciaInterface.createEvent();

        event.setDirector("TEST1");
        event.setTitle("TEST1");
        event.setDescription("TEST1");
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.now().plusDays(1));

        eventDAO.save(event);

        Long id = event.getId();
        Assertions.assertEquals(eventDAO.findById(id).get(), event);
    }

    @Test
    @DisplayName("Test findAll method for eventDAO")
    public void findAllEventTest() {
        Event event = PersistenciaInterface.createEvent();

        event.setDirector("TEST1");
        event.setTitle("TEST1");
        event.setDescription("TEST1");
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.now().plusDays(1));

        eventDAO.save(event);

        List<Event> events = eventDAO.findAll();
        Assertions.assertFalse(events.isEmpty());
    }
}
