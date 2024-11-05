package br.upe.dao;

import br.upe.entities.Event;
import br.upe.util.DevelopEntityManagerFactory;
import br.upe.util.PersistenciaInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class JBDCEventDAODiffblueTest {
    /**
     * Test {@link JBDCEventDAO#JBDCEventDAO(LambdaEntityManagerFactory)}.
     */
    private final JBDCEventDAO eventDAO = PersistenciaInterface.createJBDCEventDAO(PersistenciaInterface.getDevelopEntityManagerFactory());
    private final Logger logger = Logger.getLogger(JBDCEventDAODiffblueTest.class.getName());

    @AfterEach
    public void clearEvents(){
        List<Event> events = eventDAO.findAll();
        for(Event event : events){
            eventDAO.deleteById(event.getId());
        }
    }
    @Test
    @DisplayName("Test save method for eventDAO")
    public void saveEventTest() {
        Event event = PersistenciaInterface.createEvent();

        event.setDirector("TARCISIO");
        event.setTitle("SUPER");
        event.setDescription("...");

        eventDAO.save(event);
        logger.log(Level.INFO, event.toString());

        Assertions.assertNotNull(eventDAO.findById(event.getId()));
    }
}
