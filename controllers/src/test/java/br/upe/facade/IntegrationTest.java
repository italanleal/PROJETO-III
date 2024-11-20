package br.upe.facade;

import br.upe.controllers.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

class IntegrationTest {
    /*-------------------SetUp variables-------------------*/
    private static final String STATE_PATH = ".\\state";
    private static final String USERS_PATH = STATE_PATH+"\\users.csv";
    private static final String EVENTS_PATH = STATE_PATH+"\\events.csv";
    private static final String SUBSCRIPTIONS_PATH = STATE_PATH+"\\subscriptions.csv";
    private static final String SESSIONS_PATH = STATE_PATH+"\\sessions.csv";
    private static final String SUBMISSIONS_PATH = STATE_PATH+"\\submissions.csv";
    private static final Logger logger = Logger.getLogger(IntegrationTest.class.getName());

    private CRUDController crudController;
    private StateController stateController;
    private AuthController authController;

    /*-------------------SetUp methods-------------------*/

    @AfterEach
    void clearFiles() {
        try {
            Files.deleteIfExists(Paths.get(EVENTS_PATH));
            Files.deleteIfExists(Paths.get(SUBSCRIPTIONS_PATH));
            Files.deleteIfExists(Paths.get(SESSIONS_PATH));
            Files.deleteIfExists(Paths.get(SUBMISSIONS_PATH));
            Files.deleteIfExists(Paths.get(USERS_PATH));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when trying to delete files", e);
        }
        try {
            Files.deleteIfExists(Paths.get(STATE_PATH));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when trying to delete directory", e);
        }
    }

    @BeforeEach
    public void setUp(){
        crudController = ControllersInterface.newCRUDController();
        stateController = ControllersInterface.newStateController();
        authController = ControllersInterface.newAuthController(stateController, crudController);
    }

    /*-------------------Actual Tests-------------------*/
    @Test
    void statePersistenceTest() {
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewAdmin(email, password);
        authController.login(email, password);
        EventController eventController = ControllersInterface.newEventController(stateController, crudController);
        SessionController sessionController = ControllersInterface.newSessionController(stateController, crudController);

        eventController.createNewEvent("Linkin Park", "Chester <3");
        UUID eventUUID = stateController.getCurrentEvent().getUuid();
        sessionController.createNewSession("Given Up");
        UUID sessionUUID = stateController.getCurrentSession().getUuid();
        authController.logout();

        String email1 = "user@upe.br";
        String password1 = "password1";
        authController.createNewUser(email1, password1);
        authController.login(email1, password1);
        sessionController.addSubscriptionToSession();
        UUID subscriptionUUID = stateController.getCurrentSubscription().getUuid();
        authController.logout();

        Assertions.assertEquals(eventUUID, crudController.eventCRUD.returnEvent().iterator().next().getUuid());
        Assertions.assertEquals(sessionUUID, crudController.sessionCRUD.returnSession().iterator().next().getUuid());
        Assertions.assertEquals(subscriptionUUID, crudController.subscriptionCRUD.returnSubscription().iterator().next().getUuid());
    }

    @Test
    void createNewAdminUserTest(){
        String email = "admin@upe.br";
        String password = "password";

        boolean isCreated = authController.createNewAdmin(email, password);

        Assertions.assertTrue(isCreated);
    }
    @Test
    void createNewUserTest(){
        String email = "user@upe.br";
        String password = "password";

        boolean isCreated = authController.createNewUser(email, password);

        Assertions.assertTrue(isCreated);
    }

    @Test
    void updateUserTest(){
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewAdmin(email, password);
        authController.login(email, password);

        UserController userController = ControllersInterface.newUserController(stateController, crudController);

        String newEmail = "user@upe.br";
        userController.updateUserEmail(newEmail);

        Assertions.assertEquals(newEmail, stateController.getCurrentUser().getEmail());
    }

    @Test
    void userLoggedOutTest(){
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewAdmin(email, password);
        authController.login(email, password);
        Assertions.assertNotNull(stateController.getCurrentUser(), "User should be logged in after method call authcontroller.login()");
        authController.logout();
        Assertions.assertNull(stateController.getCurrentUser(), "User should'nt be logged in after method call authcontroller.logout()");
    }
    @Test
    void createNewEventAsCommonUserTest(){
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewUser(email, password);
        authController.login(email, password);
        EventController eventController = ControllersInterface.newEventController(stateController, crudController);

        Assertions.assertFalse(eventController.createNewEvent("eventofalso", "faker"), "Common user should not get access to edit or create events");
    }
    @Test
    void createNewEventAsAdminTest(){
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewAdmin(email, password);
        authController.login(email, password);
        EventController eventController = ControllersInterface.newEventController(stateController, crudController);
        boolean isCreated = eventController.createNewEvent("super", "jucesa");

        Assertions.assertTrue(isCreated);
    }
    @Test
    void updateEventTest(){
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewAdmin(email, password);
        authController.login(email, password);
        EventController eventController = ControllersInterface.newEventController(stateController, crudController);
        eventController.createNewEvent("super", "jucesa");
        eventController.updateEventDirector("italan");
        Assertions.assertEquals("italan", stateController.getCurrentEvent().getDirector());
    }

    @Test
    void createNewSessionTest() {
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewAdmin(email, password);
        authController.login(email, password);
        EventController eventController = ControllersInterface.newEventController(stateController, crudController);
        eventController.createNewEvent("super", "jucesa");

        SessionController sessionController = ControllersInterface.newSessionController(stateController, crudController);
        boolean isCreated = sessionController.createNewSession("Di2win");
        Assertions.assertTrue(isCreated);
    }
    @Test
    void updateSessionTest(){
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewAdmin(email, password);
        authController.login(email, password);
        EventController eventController = ControllersInterface.newEventController(stateController, crudController);
        eventController.createNewEvent("super", "jucesa");

        SessionController sessionController = ControllersInterface.newSessionController(stateController, crudController);
        sessionController.createNewSession("Di2win");
        sessionController.updateSessionDescritor("Di2loose");

        Assertions.assertEquals("Di2loose", stateController.getCurrentSession().getDescritor());
    }
    @Test
    void createNewSubmissionTest(){
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewAdmin(email, password);
        authController.login(email, password);
        EventController eventController = ControllersInterface.newEventController(stateController, crudController);
        eventController.createNewEvent("super", "jucesa");
        authController.logout();

        String emailUser = "admin@upe.br";
        String passwordUser = "password";
        authController.createNewUser(emailUser, passwordUser);
        authController.login(emailUser, passwordUser);

        eventController.addEventSubmission("PDSA");
        Assertions.assertEquals(1, stateController.getCurrentEvent().getSubmissions().size());
    }
    @Test
    void updateSubmissionTest(){
        String adminEmail = "admin@upe.br";
        String adminPassword = "password";
        authController.createNewAdmin(adminEmail, adminPassword);
        authController.login(adminEmail, adminPassword);

        EventController eventController = ControllersInterface.newEventController(stateController, crudController);
        eventController.createNewEvent("super", "jucesa");
        authController.logout();

        String userEmail = "user@upe.br";
        String userPassword = "password";
        authController.createNewUser(userEmail, userPassword);
        authController.login(userEmail, userPassword);

        eventController.addEventSubmission("PDSA");

        SubmissionController submissionController = ControllersInterface.newSubmissionController(stateController, crudController);
        submissionController.updateSubmissionDescritor("SSDP");
        Assertions.assertEquals("SSDP", stateController.getCurrentSubmission().getDescritor());
    }


    @Test
    void createNewSubscriptionTest(){
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewAdmin(email, password);
        authController.login(email, password);
        EventController eventController = ControllersInterface.newEventController(stateController, crudController);
        eventController.createNewEvent("super", "jucesa");

        SessionController sessionController = ControllersInterface.newSessionController(stateController, crudController);
        sessionController.createNewSession("Di2win");

        authController.logout();
        String emailUser = "admin@upe.br";
        String passwordUser = "password";
        authController.createNewUser(emailUser, passwordUser);
        authController.login(emailUser, passwordUser);

        sessionController.addSubscriptionToSession();
        Assertions.assertEquals(1, stateController.getCurrentSession().getSubscriptions().size());
    }

}

/* estacionamento de cursor
*  |----------|
*  |          |
*  |----------|*/
