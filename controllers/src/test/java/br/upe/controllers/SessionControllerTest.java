package br.upe.controllers;

import br.upe.operations.SessionCRUD;
import br.upe.pojos.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class SessionControllerTest {
    @Test
    void testCreateNewSession() {
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SessionController session = new SessionController(state, crud);

        auth.createNewAdmin("carlos@upe.br", "apassowrda");
        auth.login("carlos@upe.br", "apassowrda");

        event.createNewEvent("EVENTO 5", "JAckinhos");

        String sessionDescritor = "SESSION 1";
        boolean sessionResult = session.createNewSession(sessionDescritor);
        auth.logout();

        Assertions.assertTrue(sessionResult);
        Assertions.assertNotNull(state.getCurrentSession());
        Assertions.assertEquals(state.getCurrentEvent().getUuid(), state.getCurrentSession().getEventUuid());
        Assertions.assertEquals(sessionDescritor, state.getCurrentSession().getDescritor());
    }

    @Test
    void testUpdateSessionDescritor() {
        // Configuração inicial
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SessionController session = new SessionController(state, crud);


        auth.createNewAdmin("steragg@upe.br", "pwneed1");
        auth.login("steragg@upe.br", "pwneed1");

        event.createNewEvent("papo", "Diretor 1");
        session.createNewSession("Sessão 1");

        Session createdSession = SessionCRUD.returnSession(state.getCurrentSession().getUuid());
        Assertions.assertNotNull(createdSession, "A sessão não deve ser nula");
        Assertions.assertEquals("Sessão 1", createdSession.getDescritor());

        session.updateSessionDescritor("Sessão 1 Atualizada");

        Session updatedSession = SessionCRUD.returnSession(state.getCurrentSession().getUuid());
        Assertions.assertNotNull(updatedSession, "A sessão não deve ser nula");
        Assertions.assertEquals("Sessão 1 Atualizada", updatedSession.getDescritor());

        auth.logout();
    }

    @Test
    void testChangeCurrentSession() {
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SessionController session = new SessionController(state, crud);

        // Criar e autenticar um administrador
        auth.createNewAdmin("admin1@example.com", "adminpassword1");
        auth.login("admin1@example.com", "adminpassword1");

        // Criar um evento e duas sessões
        boolean eventCreated = event.createNewEvent("Evento Teste 1", "Diretor Teste 1");
        Assertions.assertTrue(eventCreated);

        boolean sessionCreated1 = session.createNewSession("Sessão Teste 1");
        Assertions.assertTrue(sessionCreated1);

        boolean sessionCreated2 = session.createNewSession("Sessão Teste 2");
        Assertions.assertTrue(sessionCreated2);

        // Mudar para a segunda sessão
        session.changeCurrentSession(state.getCurrentSession().getUuid());

        // Verificar se a sessão atual foi alterada
        Session currentSession = state.getCurrentSession();
        Assertions.assertNotNull(currentSession);
        Assertions.assertEquals("Sessão Teste 2", currentSession.getDescritor());

        // Logout do administrador
        auth.logout();
    }

    @Test
     void testCloseCurrentSession() {
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SessionController session = new SessionController(state, crud);

        auth.createNewAdmin("admin2@example.com", "adminpassword2");
        auth.login("admin2@example.com", "adminpassword2");

        boolean eventCreated = event.createNewEvent("Evento Teste 2", "Diretor Teste 2");
        Assertions.assertTrue(eventCreated);

        boolean sessionCreated = session.createNewSession("Sessão Teste 3");
        Assertions.assertTrue(sessionCreated);

        session.changeCurrentSession(state.getCurrentSession().getUuid());

        session.closeCurrentSession();

        Assertions.assertNull(state.getCurrentSession());

        auth.logout();
    }
}