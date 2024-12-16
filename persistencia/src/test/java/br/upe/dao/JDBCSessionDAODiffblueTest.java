package br.upe.dao;

import br.upe.entities.Event;
import br.upe.entities.Session;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
public class JDBCSessionDAODiffblueTest {
    /**
     * Test {@link JDBCSessionDAO#JDBCSessionDAO(LambdaEntityManagerFactory)}.
     */
    private final JDBCSessionDAO sessionDAO = PersistenciaInterface.createJDBCSessionDAO(PersistenciaInterface.getDevelopEMF_lambda());
    private final Logger logger = Logger.getLogger(JDBCSessionDAODiffblueTest.class.getName());


    @Test
    @DisplayName("Test save method for sessionDAO")
    void saveSessionTest() {

        Session session = PersistenciaInterface.createSession();
        session.setGuest("TEST1");
        session.setLocal("TEST1");
        session.setTitle("TEST1");
        session.setDescription("TEST1");
        session.setStartDate(LocalDate.now());
        session.setEndDate(LocalDate.now().plusDays(1));

        sessionDAO.save(session);
        Assertions.assertNotEquals(sessionDAO.findById(session.getId()), Optional.empty());
    }

    @Test
    @DisplayName("Test update method for sessionDAO")
    public void updateSessionTest() {
        Session session = PersistenciaInterface.createSession();
        session.setGuest("TEST1");
        session.setLocal("TEST1");
        session.setTitle("TEST1");
        session.setDescription("TEST1");
        session.setStartDate(LocalDate.now());
        session.setEndDate(LocalDate.now().plusDays(1));

        sessionDAO.save(session);

        session.setGuest("TEST2");
        session.setLocal("TEST2");
        session.setTitle("TEST2");
        session.setDescription("TEST2");
        session.setStartDate(LocalDate.now().plusDays(1));
        session.setEndDate(LocalDate.now());
        sessionDAO.update(session);

        Assertions.assertEquals(session.getTitle(), "TEST2");
    }

    @Test
    @DisplayName("Test delete method for sessionDAO")
    public void deleteSessionTest() {
        Session session = PersistenciaInterface.createSession();
        session.setGuest("TEST1");
        session.setLocal("TEST1");
        session.setTitle("TEST1");
        session.setDescription("TEST1");
        session.setStartDate(LocalDate.now());
        session.setEndDate(LocalDate.now().plusDays(1));

        sessionDAO.save(session);


        Long id = session.getId();
        sessionDAO.delete(session);
        Assertions.assertEquals(sessionDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test deleteById method for sessionDAO")
    public void deleteByIdSessionTest() {
        Session session = PersistenciaInterface.createSession();
        session.setGuest("TEST1");
        session.setLocal("TEST1");
        session.setTitle("TEST1");
        session.setDescription("TEST1");
        session.setStartDate(LocalDate.now());
        session.setEndDate(LocalDate.now().plusDays(1));

        sessionDAO.save(session);

        Long id = session.getId();
        sessionDAO.deleteById(session.getId());
        Assertions.assertEquals(sessionDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test findById method for sessionDAO")
    public void findByIdSessionTest() {
        Session session = PersistenciaInterface.createSession();
        session.setGuest("TEST1");
        session.setLocal("TEST1");
        session.setTitle("TEST1");
        session.setDescription("TEST1");
        session.setStartDate(LocalDate.now());
        session.setEndDate(LocalDate.now().plusDays(1));
        sessionDAO.save(session);

        Long id = session.getId();
        Assertions.assertEquals(sessionDAO.findById(id).get(), session);
    }

    @Test
    @DisplayName("Test findAll method for sessionDAO")
    public void findAllSessionTest() {
        Session session = PersistenciaInterface.createSession();
        session.setGuest("TEST1");
        session.setLocal("TEST1");
        session.setTitle("TEST1");
        session.setDescription("TEST1");
        session.setStartDate(LocalDate.now());
        session.setEndDate(LocalDate.now().plusDays(1));
        sessionDAO.save(session);


        List<Session> sessions = sessionDAO.findAll();
        Assertions.assertFalse(sessions.isEmpty());
    }
}
