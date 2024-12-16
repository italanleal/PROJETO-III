package br.upe.facade;

import br.upe.entities.Event;
import br.upe.entities.Session;
import br.upe.util.controllers.ControllersInterface;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

public class FacadeTest extends TestingFeatures {

    public final Facade facade = ControllersInterface.newFacade(PersistenciaInterface.getDevelopEMF_lambda());

    String eventTitle1 = "SU";
    String eventDescription1 = "Semana universitária dos estudantes da UPE";
    String eventDirector1 = "Pontual";
    LocalDate startDate1 = LocalDate.parse("2024-12-20");
    LocalDate endDate1 = LocalDate.parse("2024-12-24");


    @Nested
    @DisplayName("StateController tests")
    class StateControllerTest{
        //----------------------------------StateController tests----------------------------------
        @Test
        @Disabled
        @DisplayName("State: currentUser != null")
        void currentUserNotNullTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            Assertions.assertNotNull(facade.stateController.currentUser);
            Assertions.assertEquals(cpf, facade.stateController.currentUser.getCpf());
        }

        @Test

        @DisplayName("State: CurrentEvent != null")
        @Disabled
        void currentEventNotNullTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);

            Assertions.assertNotNull(facade.stateController.currentEvent);
            Assertions.assertEquals(eventDirector1, facade.stateController.currentEvent.getDirector());
        }
        @Test
        @DisplayName("State: CurrentSubEvent != null")
        void currentSubEventNotNullTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            String subeventTitle1 = "JJJ";

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);

            facade.subEventController.createNewSubEvent(subeventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);

            Assertions.assertNotNull(facade.stateController.currentSubEvent);
            Assertions.assertEquals(subeventTitle1, facade.stateController.currentSubEvent.getTitle());
        }

        @Test
        @Disabled
        @DisplayName("State: currentSession != null")
        void currentSessionNotNullTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String name2 = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);

            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);

            facade.sessionController.createNewSession(eventTitle1, eventDescription1, name2, "UPE");
            Assertions.assertNotNull(facade.stateController.currentSession);
            Assertions.assertEquals("UPE", facade.stateController.currentSession.getLocal());
        }

        @Test
        @Disabled
        @DisplayName("State: currentSubscription != null")
        void currentSubscriptionNotNullTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String name2 = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);

            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);

            facade.sessionController.createNewSession(eventTitle1, eventDescription1, name2, "UPE");
            Session session = facade.stateController.currentSession;

            facade.authController.logout();

            String name3 = "name#" + randomAlphaDecimalText(11);
            String surname3 = "surname#" + randomAlphaDecimalText(11);
            String cpf3 = "cpf#" + randomAlphaDecimalText(11);
            String email3 = "email#" + randomAlphaDecimalText(11);
            String password3 = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name3, surname3, cpf3, email3, password3);
            facade.authController.login(email3, password3);

            facade.stateController.currentSession= session;
            facade.sessionController.addSubscriptionToSession();
            Assertions.assertNotNull(facade.stateController.currentSubscription);
        }

        @Test
        @Disabled
        @DisplayName("State: currentSubscription != null")
        void currentSubmissionNotNullTest() throws SystemException, IOException {
            String name = "name#" + randomAlphaDecimalText(11);
            String name2 = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);
            facade.sessionController.createNewSession(eventTitle1, eventDescription1, name2, "UPE");
            Event event = facade.stateController.currentEvent;

            facade.authController.logout();

            String name3 = "name#" + randomAlphaDecimalText(11);
            String surname3 = "surname#" + randomAlphaDecimalText(11);
            String cpf3 = "cpf#" + randomAlphaDecimalText(11);
            String email3 = "email#" + randomAlphaDecimalText(11);
            String password3 = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewUser(name3, surname3, cpf3, email3, password3);
            facade.authController.login(email3, password3);

            facade.stateController.currentEvent=event;

            File tempFile = File.createTempFile("testFile", ".txt");
            tempFile.deleteOnExit();

            Files.write(tempFile.toPath(), "Test content".getBytes());

            facade.submissionController.submitFile(tempFile);

            Assertions.assertNotNull(facade.stateController.currentSubscription);
        }
    }

    @Nested
    @DisplayName("AuthController tests")
    class AuthControllerTest{
        @Test
        @DisplayName("Test create admin")
        void givenParams_whenCreateAdmin_thenProceed() throws SystemException{
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewAdmin(name, surname, cpf, email, password);

            Assertions.assertTrue(true);
        }
        @Test
        @DisplayName("Test create admin when email is already in use")
        void givenUsedParams_whenCreateAdmin_thenFails() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);

            Assertions.assertThrows(SystemException.class, () -> facade.authController.createNewAdmin(name, surname, cpf, email, password));
        }
        @Test
        @DisplayName("Test create user")
        void givenParams_whenCreateUser_thenProceed() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name, surname, cpf, email, password);
            Assertions.assertTrue(true);
        }
        @Test
        @DisplayName("Test create user when email is already in use")
        void givenUsedParams_whenCreateUser_thenFails() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name, surname, cpf, email, password);

            Assertions.assertThrows(SystemException.class, () -> facade.authController.createNewUser(name, surname, cpf, email, password));
        }

        @Test
        @DisplayName("Test change User")
        void changeUserTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            String name2 = "name#" + randomAlphaDecimalText(11);
            String surname2 = "surname#" + randomAlphaDecimalText(11);
            String cpf2 = "cpf#" + randomAlphaDecimalText(11);
            String email2 = "email#" + randomAlphaDecimalText(11);
            String password2 = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name, surname, cpf, email, password);

            facade.authController.createNewUser(name2, surname2, cpf2, email2, password2);

            facade.authController.login(email, password);

            Assertions.assertEquals(facade.daoController.systemUserDAO.findByCPF(cpf), facade.stateController.currentUser);
            facade.authController.logout();

            facade.authController.login(email2, password2);
            Assertions.assertEquals(facade.daoController.systemUserDAO.findByCPF(cpf2), facade.stateController.currentUser);

        }

        @Test
        @DisplayName("Valid login Test")
        void validLoginTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name, surname, cpf, email, password);

            facade.authController.login(email, password);

            Assertions.assertTrue(true);
        }

        @Test
        @DisplayName("Invalid login actin Test")
        void invalidLoginTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name, surname, cpf, email, password);

            Assertions.assertThrows(SystemException.class, () -> facade.authController.login(email, "abluble"));
        }
        @Test
        @DisplayName("LogOut")
        void logOutTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name, surname, cpf, email, password);

            facade.authController.login(email, password);

            facade.authController.logout();
            Assertions.assertNull(facade.stateController.currentUser);
        }
    }
    @Nested
    @DisplayName("UserController tests")
    class UserControllerTest{

    }

    @Nested
    @DisplayName("EventController tests")
    class EventControllerTest{
        @Test
        @DisplayName("EventController: Invalid date for event")
        void setInvalidDateForEvent() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDateBeforeStartDate = LocalDate.parse("2024-12-19");

            Assertions.assertThrows(SystemException.class, () -> facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDateBeforeStartDate));

            Assertions.assertNull(facade.stateController.currentEvent);
        }
    }

    @Nested
    @DisplayName("SessionController tests")
    class SessionControllerTest{

    }
    @Nested
    @DisplayName("SubEventController tests")
    class SubEventControllerTest{

    }
    @Nested
    @DisplayName("SubscriptionController tests")
    class SubscriptionControllerTest{

    }
    @Nested
    @DisplayName("SubmissionController tests")
    class SubmissionControllerTest{

    }
}
