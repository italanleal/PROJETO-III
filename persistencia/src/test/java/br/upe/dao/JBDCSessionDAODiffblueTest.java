package br.upe.dao;

import br.upe.entities.Event;
import br.upe.entities.Session;
import br.upe.util.PersistenciaInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

class JBDCSessionDAODiffblueTest {
    /**
     * Test {@link JBDCSessionDAO#JBDCSessionDAO(LambdaEntityManagerFactory)}.
     */
    private final JBDCSessionDAO sessionDAO = PersistenciaInterface.createJBDCSessionDAO(PersistenciaInterface.getDevelopEMF_lambda());
    private final Logger logger = Logger.getLogger(JBDCSessionDAODiffblueTest.class.getName());

    @AfterEach
    public void clearSessions(){
        List<Session> sessions = sessionDAO.findAll();
        for(Session session : sessions){
            sessionDAO.deleteById(session.getId());
        }
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
