package br.upe.dao;

import br.upe.entities.Session;
import br.upe.entities.SubEvent;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
class JDBCSubEventDAODiffblueTest {
    /**
     * Test {@link JDBCSubEventDAO#JDBCSubEventDAO(LambdaEntityManagerFactory)}.
     */

    private final JDBCSubEventDAO subEventDAO = PersistenciaInterface.createJDBCSubEventDAO(PersistenciaInterface.getDevelopEMF_lambda());
    private final Logger logger = Logger.getLogger(JDBCSubEventDAODiffblueTest.class.getName());


    @Test
    @DisplayName("Test save method for eventDAO")
    public void saveEventTest() {
        SubEvent subEvent = PersistenciaInterface.createSubEvent();

        subEvent.setTitle("TEST1");
        subEvent.setDescription("TEST1");
        subEvent.setStartDate(LocalDate.now());
        subEvent.setEndDate(LocalDate.now().plusDays(1));

        subEventDAO.save(subEvent);

        Assertions.assertNotEquals(subEventDAO.findById(subEvent.getId()), Optional.empty());
    }

    @Test
    @DisplayName("Test update method for subEventDAO")
    public void updateSubEventTest() {
        SubEvent subEvent = PersistenciaInterface.createSubEvent();

        subEvent.setTitle("TEST1");
        subEvent.setDescription("TEST1");
        subEvent.setStartDate(LocalDate.now());
        subEvent.setEndDate(LocalDate.now().plusDays(1));

        subEventDAO.save(subEvent);

        subEvent.setTitle("TEST2");
        subEvent.setDescription("TEST2");
        subEvent.setStartDate(LocalDate.now().plusDays(1));
        subEvent.setEndDate(LocalDate.now());

        subEventDAO.update(subEvent);

        Assertions.assertEquals(subEvent.getTitle(), "TEST2");
    }

    @Test
    @DisplayName("Test delete method for subEventDAO")
    public void deleteSubEventTest() {
        SubEvent subEvent = PersistenciaInterface.createSubEvent();

        subEvent.setTitle("TEST1");
        subEvent.setDescription("TEST1");
        subEvent.setStartDate(LocalDate.now());
        subEvent.setEndDate(LocalDate.now().plusDays(1));

        subEventDAO.save(subEvent);

        Long id = subEvent.getId();
        subEventDAO.delete(subEvent);
        Assertions.assertEquals(subEventDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test deleteById method for subEventDAO")
    public void deleteByIdSubEventTest() {
        SubEvent subEvent = PersistenciaInterface.createSubEvent();

        subEvent.setTitle("TEST1");
        subEvent.setDescription("TEST1");
        subEvent.setStartDate(LocalDate.now());
        subEvent.setEndDate(LocalDate.now().plusDays(1));

        subEventDAO.save(subEvent);

        Long id = subEvent.getId();
        subEventDAO.deleteById(subEvent.getId());
        Assertions.assertEquals(subEventDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test findById method for subEventDAO")
    public void findByIdSubEventTest() {
        SubEvent subEvent = PersistenciaInterface.createSubEvent();

        subEvent.setTitle("TEST1");
        subEvent.setDescription("TEST1");
        subEvent.setStartDate(LocalDate.now());
        subEvent.setEndDate(LocalDate.now().plusDays(1));

        subEventDAO.save(subEvent);

        Long id = subEvent.getId();
        Assertions.assertEquals(subEventDAO.findById(id).get(), subEvent);
    }

    @Test
    @DisplayName("Test findAll method for subEventDAO")
    public void findAllSubEventTest() {
        SubEvent subEvent = PersistenciaInterface.createSubEvent();

        subEvent.setTitle("TEST1");
        subEvent.setDescription("TEST1");
        subEvent.setStartDate(LocalDate.now());
        subEvent.setEndDate(LocalDate.now().plusDays(1));

        subEventDAO.save(subEvent);


        List<SubEvent> subEvents = subEventDAO.findAll();
        Assertions.assertFalse(subEvents.isEmpty());
    }
}
