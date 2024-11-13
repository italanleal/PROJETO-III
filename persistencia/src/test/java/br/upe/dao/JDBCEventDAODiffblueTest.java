package br.upe.dao;

import br.upe.entities.Event;
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

    @AfterAll
    public static void dropTable(){
        String tableName = "event";
        EntityManager em = PersistenciaInterface.getDevelopEMF_lambda().call();
        em.getTransaction().begin();
        em.createNativeQuery("DROP TABLE " + tableName).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Test save method for eventDAO")
    public void saveEventTest() {
        Event event = PersistenciaInterface.createEvent();

        event.setDirector("TARCISIO");
        event.setTitle("SUPER");
        event.setDescription("...");

        eventDAO.save(event);

        Assertions.assertNotNull(eventDAO.findById(event.getId()));

    }
}
