package br.upe.dao;

import br.upe.entities.Session;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;

import java.util.logging.Logger;

class JDBCSessionDAODiffblueTest {
    /**
     * Test {@link JDBCSessionDAO#JDBCSessionDAO(LambdaEntityManagerFactory)}.
     */
    private final JDBCSessionDAO sessionDAO = PersistenciaInterface.createJDBCSessionDAO(PersistenciaInterface.getDevelopEMF_lambda());
    private final Logger logger = Logger.getLogger(JDBCSessionDAODiffblueTest.class.getName());

    @AfterAll
    public static void dropTable(){
        String tableName = "session";
        EntityManager em = PersistenciaInterface.getDevelopEMF_lambda().call();
        em.getTransaction().begin();
        em.createNativeQuery("DROP TABLE " + tableName).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Test save method for sessionDAO")
    void saveSessionTest() {

        Session session = PersistenciaInterface.createSession();
        session.setGuest("jackson");
        session.setLocal("inLab");
        session.setTitle("INOVA ALGO");

        sessionDAO.save(session);
        Assertions.assertNotNull(sessionDAO.findById(session.getId()));
    }
}
