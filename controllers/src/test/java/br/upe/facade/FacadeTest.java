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
import java.util.Collection;
import java.util.Optional;


public class FacadeTest extends TestingFeatures {

    public final Facade facade = ControllersInterface.newFacade(PersistenciaInterface.getDevelopEMF_lambda());

    String eventTitle1 = "SU";
    String eventDescription1 = "Semana universitária dos estudantes da UPE";
    String eventDirector1 = "Pontual";
    LocalDate startDate1 = LocalDate.parse("2024-12-20");
    LocalDate endDate1 = LocalDate.parse("2024-12-30");

    @Nested
    @DisplayName("StateController tests")
    class StateControllerTest{
        //----------------------------------StateController tests----------------------------------
        @Test
        @DisplayName("State: currentUser != null")
        void currentUserNotNullTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            Assertions.assertNotNull(facade.stateController.getCurrentUser());
            Assertions.assertEquals(cpf, facade.stateController.getCurrentUser().getCpf());
        }

        @Test
        @DisplayName("State: CurrentEvent != null")
        void currentEventNotNullTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);

            Assertions.assertNotNull(facade.stateController.getCurrentEvent());
            Assertions.assertEquals(eventDirector1, facade.stateController.getCurrentEvent().getDirector());
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

            Assertions.assertNotNull(facade.stateController.getCurrentSubEvent());
            Assertions.assertEquals(subeventTitle1, facade.stateController.getCurrentSubEvent().getTitle());
        }

        @Test
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
            facade.sessionController.createNewSession(eventTitle1, eventDescription1, name2, "UPE", startDate1, endDate1);

            Assertions.assertNotNull(facade.stateController.getCurrentSession());
            Assertions.assertEquals("UPE", facade.stateController.getCurrentSession().getLocal());
        }

        @Test
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

            facade.sessionController.createNewSession(eventTitle1, eventDescription1, name2, "UPE", startDate1, endDate1);

            Session session = facade.stateController.getCurrentSession();

            facade.authController.logout();

            String name3 = "name#" + randomAlphaDecimalText(11);
            String surname3 = "surname#" + randomAlphaDecimalText(11);
            String cpf3 = "cpf#" + randomAlphaDecimalText(11);
            String email3 = "email#" + randomAlphaDecimalText(11);
            String password3 = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name3, surname3, cpf3, email3, password3);
            facade.authController.login(email3, password3);

            facade.stateController.setCurrentSession(session);
            facade.sessionController.addSubscriptionToSession();
            Assertions.assertNotNull(facade.stateController.getCurrentSubscription());
        }

        @Test
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
            facade.sessionController.createNewSession(eventTitle1, eventDescription1, name2, "UPE", startDate1, endDate1);


            Event event = facade.stateController.getCurrentEvent();
            facade.authController.logout();


            String name3 = "name#" + randomAlphaDecimalText(11);
            String surname3 = "surname#" + randomAlphaDecimalText(11);
            String cpf3 = "cpf#" + randomAlphaDecimalText(11);
            String email3 = "email#" + randomAlphaDecimalText(11);
            String password3 = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewUser(name3, surname3, cpf3, email3, password3);
            facade.authController.login(email3, password3);

            facade.stateController.setCurrentEvent(event);

            File tempFile = File.createTempFile("testFile", ".txt");
            tempFile.deleteOnExit();

            Files.write(tempFile.toPath(), "Test content".getBytes());

            facade.submissionController.submitFile(tempFile);

            Assertions.assertNotNull(facade.stateController.getCurrentSubmission());
        }
        @Test
        @DisplayName("State: currentCertification != null")
        void currentCertificationNotNullTest() { /* TODO */ }
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
        @DisplayName("Test change Userd")
        void changeUserTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            String name2 = "name#" + randomAlphaDecimalText(11);
            String surname2 = "surname#" + randomAlphaDecimalText(11);
            String cpf2 = "cpf2#" + randomAlphaDecimalText(11);
            String email2 = "email#" + randomAlphaDecimalText(11);
            String password2 = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewUser(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            Assertions.assertEquals(facade.daoController.userDAO.findByCPF(cpf).getId(), facade.stateController.getCurrentUser().getId());

            facade.authController.logout();

            facade.authController.createNewUser(name2, surname2, cpf2, email2, password2);
            facade.authController.login(email2, password2);
            Assertions.assertEquals(facade.daoController.userDAO.findByCPF(cpf2).getId(), facade.stateController.getCurrentUser().getId());
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

            Assertions.assertEquals(name, facade.stateController.getCurrentUser().getName());
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
    class UserdControllerTest {
        @Test
        @DisplayName("UpdateUserNameTest")
        void updateUserNameTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewUser(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.userController.updateUserName("Concha");
            Assertions.assertEquals("Concha", facade.stateController.getCurrentUser().getName());
        }
        @Test
        @DisplayName("UpdateUserEmailTest")
        void updateUserEmailTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewUser(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            String newEmail = "Concha";
            facade.userController.updateUserEmail(newEmail);
            Assertions.assertEquals(newEmail, facade.stateController.getCurrentUser().getEmail());
        }
        @Test
        @DisplayName("UpdateUserEmailTest")
        void updateUserEmailInvalidTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewUser(name, surname, cpf, email, password);

            String name2 = "name#" + randomAlphaDecimalText(11);
            String surname2 = "surname#" + randomAlphaDecimalText(11);
            String cpf2 = "cpf#" + randomAlphaDecimalText(11);
            String email2 = "email#" + randomAlphaDecimalText(11);
            String password2 = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewUser(name2, surname2, cpf2, email2, password2);

            facade.authController.login(email, password);
            Assertions.assertThrows(SystemException.class, () -> facade.userController.updateUserEmail(email2));
            Assertions.assertEquals(email, facade.stateController.getCurrentUser().getEmail());
        }
        @Test
        @DisplayName("UpdateUserPassword")
        void updateUserPasswordTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewUser(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.userController.updateUserPassword("Concha");
            Assertions.assertEquals("Concha", facade.stateController.getCurrentUser().getPassword());
        }
        @Test
        @DisplayName("UpdateUserSurname")
        void updateUserSurnameTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewUser(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.userController.updateUserSurname("Concha");
            Assertions.assertEquals("Concha", facade.stateController.getCurrentUser().getSurname());
        }
    }

    @Nested
    @DisplayName("EventController tests")
    class EventControllerTest{
        @Test
        @DisplayName("CreateNewEventTest")
        void createNewEventTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);
            Assertions.assertNotNull(facade.stateController.getCurrentEvent());
        }
        @Test
        @DisplayName("UpdateEventDescription")
        void updateEventDescriptionTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);
            facade.eventController.updateEventDescription("Concha");
            Assertions.assertEquals("Concha", facade.stateController.getCurrentEvent().getDescription());
        }
        @Test
        @DisplayName("UpdateEventDirector")
        void updateEventDirectorTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname = "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);
            facade.eventController.updateEventDirector("New Director");
            Assertions.assertEquals("New Director", facade.stateController.getCurrentEvent().getDirector());
        }

        @Test
        @DisplayName("UpdateEventTitle")
        void updateEventTitleTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname = "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);
            facade.eventController.updateEventTitle("New Title");
            Assertions.assertEquals("New Title", facade.stateController.getCurrentEvent().getTitle());
        }

        @Test
        @DisplayName("UpdateEventStartDate to after the end date")
        void updateEventToValidStartDateTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname = "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = startDate.plusDays(10);
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            LocalDate newStartDate = startDate.plusDays(5);

            facade.eventController.updateEventStartDate(newStartDate);
            Assertions.assertEquals(newStartDate, facade.stateController.getCurrentEvent().getStartDate());
        }

        @Test
        @DisplayName("UpdateEventStartDate to after the end date")
        void updateEventToInvalidStartDateTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname = "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = startDate.plusDays(10);
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            LocalDate newStartDate = startDate.plusDays(11);

            Assertions.assertThrows(SystemException.class, () -> facade.eventController.updateEventStartDate(newStartDate));
            Assertions.assertEquals(startDate, facade.stateController.getCurrentEvent().getStartDate());
        }

        @Test
        @DisplayName("UpdateEventEndDate")
        void updateEventToValidEndDateTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname = "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = startDate.plusDays(10);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            LocalDate newEndDate = startDate.plusDays(11);
            facade.eventController.updateEventEndDate(newEndDate);
            Assertions.assertEquals(newEndDate, facade.stateController.getCurrentEvent().getEndDate());
        }

        @Test
        @DisplayName("UpdateEventEndDate")
        void updateEventToInvalidEndDateTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname = "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = startDate.plusDays(10);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            LocalDate newEndDate = endDate.minusDays(11);
            Assertions.assertThrows(SystemException.class, () -> facade.eventController.updateEventEndDate(newEndDate));
            Assertions.assertEquals(endDate, facade.stateController.getCurrentEvent().getEndDate());
        }

        @Test
        @DisplayName("Delete event")
        void deleteEventTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);
            Long id = facade.stateController.getCurrentEvent().getId();
            facade.eventController.deleteEvent(facade.stateController.getCurrentEvent());
            Assertions.assertEquals(Optional.empty(), facade.daoController.eventDAO.findById(id));
        }
        //special cases
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
        @Test
        @DisplayName("GetAllEventByUser Test")
        void getALlEventByUserTest() throws SystemException{
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);
            Collection<Event> events = facade.eventController.getAllEventsByUser();
            Assertions.assertEquals(1, events.size());
        }
    }

    @Nested
    @DisplayName("SessionController tests")
    class SessionControllerTest{

        @Test
        @DisplayName("Create new session test")
        void createNewSessionTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String title = "title#" + randomAlphaDecimalText(11);
            String description = "description#" + randomAlphaDecimalText(11);
            String guest = "guest#" + randomAlphaDecimalText(11);
            String location = "location#" + randomAlphaDecimalText(11);
            facade.sessionController.createNewSession(title, description, guest, location, startDate, endDate);
            Assertions.assertNotNull(facade.stateController.getCurrentSession());
            Assertions.assertEquals(location, facade.stateController.getCurrentSession().getLocal());
        }
        @Test
        @DisplayName("Update Session description")
        void updateSessionDescriptionTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String title = "title#" + randomAlphaDecimalText(11);
            String description = "description#" + randomAlphaDecimalText(11);
            String guest = "guest#" + randomAlphaDecimalText(11);
            String location = "location#" + randomAlphaDecimalText(11);
            facade.sessionController.createNewSession(title, description, guest, location, startDate, endDate);

            String newDescription = "newDescription#" + randomAlphaDecimalText(11);
            facade.sessionController.updateSessionDescription(newDescription);
            Assertions.assertEquals(newDescription, facade.stateController.getCurrentSession().getDescription());
        }
        @Test
        @DisplayName("Update Session local")
        void updateSessionLocalTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String title = "title#" + randomAlphaDecimalText(11);
            String description = "description#" + randomAlphaDecimalText(11);
            String guest = "guest#" + randomAlphaDecimalText(11);
            String location = "location#" + randomAlphaDecimalText(11);
            facade.sessionController.createNewSession(title, description, guest, location, startDate, endDate);

            String newLocal = "newLocal#" + randomAlphaDecimalText(11);
            facade.sessionController.updateSessionDescription(newLocal);
            Assertions.assertEquals(newLocal, facade.stateController.getCurrentSession().getDescription());
        }
        @Test
        @DisplayName("Update Session guest")
        void updateSessionGuestTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String title = "title#" + randomAlphaDecimalText(11);
            String description = "description#" + randomAlphaDecimalText(11);
            String guest = "guest#" + randomAlphaDecimalText(11);
            String location = "location#" + randomAlphaDecimalText(11);
            facade.sessionController.createNewSession(title, description, guest, location, startDate, endDate);

            String newGuest = "newGuest#" + randomAlphaDecimalText(11);
            facade.sessionController.updateSessionDescription(newGuest);
            Assertions.assertEquals(newGuest, facade.stateController.getCurrentSession().getDescription());
        }
        @Test
        @DisplayName("Update Session title")
        void updateSessionTitleTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String title = "title#" + randomAlphaDecimalText(11);
            String description = "description#" + randomAlphaDecimalText(11);
            String guest = "guest#" + randomAlphaDecimalText(11);
            String location = "location#" + randomAlphaDecimalText(11);
            facade.sessionController.createNewSession(title, description, guest, location, startDate, endDate);

            String newTitle = "newTitle#" + randomAlphaDecimalText(11);
            facade.sessionController.updateSessionDescription(newTitle);
            Assertions.assertEquals(newTitle, facade.stateController.getCurrentSession().getDescription());
        }

        @Test
        @DisplayName("Update Session StartDate")
        void updateSessionValidDateTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String title = "title#" + randomAlphaDecimalText(11);
            String description = "description#" + randomAlphaDecimalText(11);
            String guest = "guest#" + randomAlphaDecimalText(11);
            String location = "location#" + randomAlphaDecimalText(11);

            LocalDate sessionStartDate = LocalDate.parse("2024-12-20");
            LocalDate sessionEndDate = LocalDate.parse("2024-12-22");

            facade.sessionController.createNewSession(title, description, guest, location, sessionStartDate, sessionEndDate);

            LocalDate newStartDate = startDate.plusDays(2);
            LocalDate newEndDate =  startDate.plusDays(2);

            facade.sessionController.updateSessionStartDate(newStartDate);
            facade.sessionController.updateSessionEndDate(newEndDate);

            Assertions.assertEquals(newStartDate, facade.stateController.getCurrentSession().getStartDate());
            Assertions.assertEquals(newEndDate, facade.stateController.getCurrentSession().getEndDate());
        }
        @Test
        @DisplayName("Update Session StartDate")
        void updateSessionInvalidDateTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String title = "title#" + randomAlphaDecimalText(11);
            String description = "description#" + randomAlphaDecimalText(11);
            String guest = "guest#" + randomAlphaDecimalText(11);
            String location = "location#" + randomAlphaDecimalText(11);
            LocalDate sessionStartDate = LocalDate.parse("2024-12-20");
            LocalDate sessionEndDate = LocalDate.parse("2024-12-22");
            facade.sessionController.createNewSession(title, description, guest, location,sessionStartDate, sessionEndDate);


            LocalDate newEndDate =  sessionStartDate.minusDays(2);

            Assertions.assertThrows(SystemException.class, () -> facade.sessionController.updateSessionEndDate(newEndDate));
            Assertions.assertEquals(sessionEndDate, facade.stateController.getCurrentSession().getEndDate());

            Assertions.assertThrows(SystemException.class,
                    () -> facade.sessionController.updateSessionStartDate(
                            facade.stateController.getCurrentSession().getEndDate().plusDays(1)
                    )
            , "SessionStartDate should not get updated");
            Assertions.assertEquals(sessionStartDate, facade.stateController.getCurrentSession().getStartDate());
        }
        @Test
        @DisplayName("Add subscription to session Test")
        void addSubscriptionToSessionTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String title = "title#" + randomAlphaDecimalText(11);
            String description = "description#" + randomAlphaDecimalText(11);
            String guest = "guest#" + randomAlphaDecimalText(11);
            String location = "location#" + randomAlphaDecimalText(11);
            LocalDate sessionStartDate = LocalDate.parse("2024-12-20");
            LocalDate sessionEndDate = LocalDate.parse("2024-12-22");

            facade.sessionController.createNewSession(title, description, guest, location, sessionStartDate, sessionEndDate);
            Session session = facade.stateController.getCurrentSession();
            facade.authController.logout();

            String name2 = "name#" + randomAlphaDecimalText(11);
            String surname2 = "surname#" + randomAlphaDecimalText(11);
            String cpf2 = "cpf#" + randomAlphaDecimalText(11);
            String email2 = "email#" + randomAlphaDecimalText(11);
            String password2 = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewUser(name2, surname2, cpf2, email2, password2);
            facade.authController.login(email2, password2);

            facade.stateController.setCurrentSession(session);
            facade.sessionController.addSubscriptionToSession();

            Assertions.assertNotNull(facade.stateController.getCurrentSubscription());
            Assertions.assertEquals(facade.stateController.getCurrentSubscription().getUser().getId(), facade.stateController.getCurrentUser().getId());
        }
        @Test
        @DisplayName("Get all event sessions")
        void getAllEventSessionsTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            String title = "title#" + randomAlphaDecimalText(11);
            String description = "description#" + randomAlphaDecimalText(11);
            String guest = "guest#" + randomAlphaDecimalText(11);
            String location = "location#" + randomAlphaDecimalText(11);
            LocalDate sessionStartDate = LocalDate.parse("2024-12-20");
            LocalDate sessionEndDate = LocalDate.parse("2024-12-22");

            String title2 = "title2#" + randomAlphaDecimalText(11);
            String description2 = "description2#" + randomAlphaDecimalText(11);
            String guest2 = "guest2#" + randomAlphaDecimalText(11);
            String location2 = "location2#" + randomAlphaDecimalText(11);
            LocalDate sessionStartDate2 = LocalDate.parse("2024-12-20");
            LocalDate sessionEndDate2 = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            facade.sessionController.createNewSession(title, description, guest, location, sessionStartDate, sessionEndDate);
            facade.sessionController.createNewSession(title2, description2, guest2, location2, sessionStartDate2, sessionEndDate2);

            Collection<Session> sessions = facade.sessionController.getAllEventSessions();
            Assertions.assertEquals(2, sessions.size());
        }
        @Test
        @DisplayName("Get all subevent sessions")
        void getAllSubEventSessionsTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            String title = "title#" + randomAlphaDecimalText(11);
            String description = "description#" + randomAlphaDecimalText(11);
            String guest = "guest#" + randomAlphaDecimalText(11);
            String location = "location#" + randomAlphaDecimalText(11);
            LocalDate sessionStartDate = LocalDate.parse("2024-12-20");
            LocalDate sessionEndDate = LocalDate.parse("2024-12-22");

            String title2 = "title2#" + randomAlphaDecimalText(11);
            String description2 = "description2#" + randomAlphaDecimalText(11);
            String guest2 = "guest2#" + randomAlphaDecimalText(11);
            String location2 = "location2#" + randomAlphaDecimalText(11);
            LocalDate sessionStartDate2 = LocalDate.parse("2024-12-20");
            LocalDate sessionEndDate2 = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);

            facade.sessionController.createNewSession(title, description, guest, location, sessionStartDate, sessionEndDate);
            facade.sessionController.createNewSession(title2, description2, guest2, location2, sessionStartDate2, sessionEndDate2);

            Collection<Session> sessions = facade.sessionController.getAllEventSessions();
            Assertions.assertEquals(2, sessions.size());
            Assertions.assertEquals(2, facade.stateController.getCurrentSubEvent().getSessions().size());
        }
    }
    @Nested
    @DisplayName("SubEventController tests")

    class SubEventControllerTest{
        @Test
        @DisplayName("Create new subEvent Test")
        void createNewSubEventTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);
            Assertions.assertNotNull(facade.stateController.getCurrentSubEvent());
            Assertions.assertEquals(subEventTitle, facade.stateController.getCurrentSubEvent().getTitle());
        }
        @Test
        @DisplayName("Update Subevent description Test")
        void updateSubEventDescriptionTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);
            String newDescription = "Oii";
            facade.subEventController.updateSubEventDescription(newDescription);
            Assertions.assertEquals(newDescription, facade.stateController.getCurrentSubEvent().getDescription());
        }
        @Test
        @DisplayName("Update SubEvent Director")
        void updateSubEventDirectorTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);
            String newDirector = "Jackito";
            facade.subEventController.updateSubEventDirector(newDirector);
            Assertions.assertEquals(newDirector, facade.stateController.getCurrentSubEvent().getDirector());
        }
        @Test
        @DisplayName("Update SubEvent title")
        void updateSubEventTitleTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);
            String newTitle = "Éeeeh";
            facade.subEventController.updateSubEventTitle(newTitle);
            Assertions.assertEquals(newTitle, facade.stateController.getCurrentSubEvent().getTitle());
        }
        @Test
        @DisplayName("Update SubEvent StartDate")
        void updateSubEventStartDateTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);
            LocalDate newStartDate = endDate.minusDays(1);
            Assertions.assertDoesNotThrow(() -> facade.subEventController.updateSubEventStartDate(newStartDate));
            Assertions.assertEquals(newStartDate, facade.stateController.getCurrentSubEvent().getStartDate());
        }
        @Test
        @DisplayName("Update SubEvent EndDate")
        void updateSubEventEndDateTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);
            LocalDate newEndDate = startDate.plusDays(10);
            Assertions.assertDoesNotThrow(() -> facade.subEventController.updateSubEventEndDate(newEndDate));
            Assertions.assertEquals(newEndDate, facade.stateController.getCurrentSubEvent().getEndDate());
        }
        @Test
        @DisplayName("Update SubEvent Invalid StartDate")
        void updateSubEventInvalidStartDateTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);
            LocalDate newStartDate = endDate.plusDays(10);
            Assertions.assertThrows(SystemException.class, () -> facade.subEventController.updateSubEventStartDate(newStartDate));
            Assertions.assertEquals(startDate, facade.stateController.getCurrentSubEvent().getStartDate());
        }
        @Test
        @DisplayName("Update SubEvent Invalid EndDate")
        void updateSubEventInvalidEndDateTest() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);
            LocalDate newEndDate = startDate.minusDays(10);
            Assertions.assertThrows(SystemException.class, () -> facade.subEventController.updateSubEventEndDate(newEndDate));
            Assertions.assertEquals(endDate, facade.stateController.getCurrentSubEvent().getEndDate());
        }
        @Test
        @DisplayName("Delete Subevent Test")
        void deleteSubEventTest() throws SystemException{
            String name = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);

            facade.subEventController.deleteSubEvent(facade.stateController.getCurrentSubEvent());
            Assertions.assertNull(facade.stateController.getCurrentSubEvent());
            Assertions.assertNotNull(facade.stateController.getCurrentEvent());
        }
        @Test
        @DisplayName("Get all subevents Test")
        void getAllEventSubEvents() throws SystemException {
            String name = "name#" + randomAlphaDecimalText(11);
            String surname = "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            LocalDate startDate = LocalDate.parse("2024-12-20");
            LocalDate endDate = LocalDate.parse("2024-12-22");

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDate);

            String subEventTitle = "title3#" + randomAlphaDecimalText(11);
            String description3 = "description2#" + randomAlphaDecimalText(11);

            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);
            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);
            facade.subEventController.createNewSubEvent(subEventTitle, description3, "Eu", startDate, endDate);

            Assertions.assertEquals(3, facade.subEventController.getAllSubEvents().size());
        }
    }

    @Nested
    @DisplayName("SubscriptionController tests")
    class SubscriptionControllerTest{
        //nothing to test yet
    }

    @Nested
    @DisplayName("SubmissionController tests")
    class SubmissionControllerTest{
        @Test
        @DisplayName("Submit File Test")
        void submitFileTest() throws SystemException, IOException {
            String name = "name#" + randomAlphaDecimalText(11);
            String name2 = "name#" + randomAlphaDecimalText(11);
            String surname= "surname#" + randomAlphaDecimalText(11);
            String cpf = "cpf#" + randomAlphaDecimalText(11);
            String email = "email#" + randomAlphaDecimalText(11);
            String password = "pass#" + randomAlphaDecimalText(11);

            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            facade.authController.login(email, password);

            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);
            facade.sessionController.createNewSession(eventTitle1, eventDescription1, name2, "UPE", startDate1, endDate1);


            Event event = facade.stateController.getCurrentEvent();
            facade.authController.logout();


            String name3 = "name#" + randomAlphaDecimalText(11);
            String surname3 = "surname#" + randomAlphaDecimalText(11);
            String cpf3 = "cpf#" + randomAlphaDecimalText(11);
            String email3 = "email#" + randomAlphaDecimalText(11);
            String password3 = "pass#" + randomAlphaDecimalText(11);
            facade.authController.createNewUser(name3, surname3, cpf3, email3, password3);
            facade.authController.login(email3, password3);

            facade.stateController.setCurrentEvent(event);

            File tempFile = File.createTempFile("testFile", ".txt");
            tempFile.deleteOnExit();

            Files.write(tempFile.toPath(), "Test content".getBytes());

            byte[] bytes = Files.readAllBytes(tempFile.toPath());
            facade.submissionController.submitFile(tempFile);
            Assertions.assertNotNull(facade.stateController.getCurrentSubmission().getContent());
            Assertions.assertEquals(bytes.length, facade.stateController.getCurrentSubmission().getContent().length);
        }
    }
}
