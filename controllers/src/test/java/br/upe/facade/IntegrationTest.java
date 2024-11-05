package br.upe.facade;

import br.upe.controllers.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntegrationTest {
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
    public void createNewAdminUserTest(){
        String email = "admin@upe.br";
        String password = "password";

        boolean isCreated = authController.createNewAdmin(email, password);

        Assertions.assertTrue(isCreated);
    }
    @Test
    public void createNewUserTest(){
        String email = "user@upe.br";
        String password = "password";

        boolean isCreated = authController.createNewUser(email, password);

        Assertions.assertTrue(isCreated);
    }

    @Test
    public void updateUserTest(){

    }

    @Test
    public void createNewEventTest(){
        String email = "admin@upe.br";
        String password = "password";

        authController.createNewAdmin(email, password);
        authController.login(email, password);
        EventController eventController = ControllersInterface.newEventController(stateController, crudController);
        boolean isCreated = eventController.createNewEvent("super", "jucesa");

        Assertions.assertTrue(isCreated);
    }
    @Test
    public void updateEventTest(){
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
    public void createNewSessionTest() {
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
    public void updateSessionTest(){
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
    public void createNewSubmissionTest(){
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
    public void createNewSubscriptionTest(){
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
