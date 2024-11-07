package br.upe.dao;

import static org.mockito.Mockito.mock;

import br.upe.entities.Event;
import br.upe.entities.SubEvent;
import br.upe.util.PersistenciaInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

class JBDCSubEventDAODiffblueTest {
    /**
     * Test {@link JBDCSubEventDAO#JBDCSubEventDAO(LambdaEntityManagerFactory)}.
     */

    private final JBDCSubEventDAO subEventDAO = PersistenciaInterface.createJBDCSubEventDAO(PersistenciaInterface.getDevelopEMF_lambda());
    private final Logger logger = Logger.getLogger(JBDCSubEventDAODiffblueTest.class.getName());

    @AfterEach
    public void clearEvents(){
        List<SubEvent> subEvents = subEventDAO.findAll();
        for(SubEvent subEvent : subEvents){
            subEventDAO.deleteById(subEvent.getId());
        }
    }

    @Test
    @DisplayName("Test save method for eventDAO")
    public void saveEventTest() {
        SubEvent subEvent = PersistenciaInterface.createSubEvent();

        subEvent.setTitle("SUPER");
        subEvent.setDescription("...");
        subEvent.setStartDate(LocalDate.now());

        subEventDAO.save(subEvent);

        Assertions.assertNotNull(subEventDAO.findById(subEvent.getId()));

    }
}
