package br.upe.dao;

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

        subEvent.setTitle("SUPER");
        subEvent.setDescription("...");
        subEvent.setStartDate(LocalDate.now());

        subEventDAO.save(subEvent);

        Assertions.assertNotEquals(subEventDAO.findById(subEvent.getId()), Optional.empty());

    }
}
