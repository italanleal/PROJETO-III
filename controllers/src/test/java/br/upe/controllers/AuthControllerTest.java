package br.upe.controllers;

import br.upe.pojos.User;
import br.upe.operations.HasherInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {
    /*-------------------SetUp variables-------------------*/
    private static final String STATE_PATH = ".\\state";
    private static final String USERS_PATH = STATE_PATH+"\\users.csv";
    private static final String EVENTS_PATH = STATE_PATH+"\\events.csv";
    private static final String SUBSCRIPTIONS_PATH = STATE_PATH+"\\subscriptions.csv";
    private static final String SESSIONS_PATH = STATE_PATH+"\\sessions.csv";
    private static final String SUBMISSIONS_PATH = STATE_PATH+"\\submissions.csv";
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

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

    @Test
    public void testCreateNewUser_SuccessfulCreation() {
        // Arrange
        stateController = new StateController();
        crudController = new CRUDController();
        authController = new AuthController(stateController, crudController);
        String email = "test@teste1.com";
        String password = "password123";
        String hashedPassword = HasherInterface.hash(password);

        // Act
        boolean result = authController.createNewUser(email, password);
        authController.login(email, password); // Faz o login após a criação do usuário

        // Assert
        assertTrue(result);
        User user = stateController.getCurrentUser(); // Casting para User
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(hashedPassword, user.getPassword());  // Verifica se o hash armazenado é o correto
    }

    @Test
    public void testCreateNewUser_UserAlreadyExists() {
        // Arrange
        String email = "existing@example.com";
        String password = "password123";
        authController.createNewUser(email, password);

        // Act
        boolean result = authController.createNewUser(email, password);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testCreateNewAdmin_SuccessfulCreation() {
        stateController = new StateController();
        crudController = new CRUDController();
        authController = new AuthController(stateController, crudController);
        // Arrange
        String email = "admin@teste2.com";
        String password = "admin123";
        String hashedPassword = HasherInterface.hash(password);

        // Act
        boolean result = authController.createNewAdmin(email, password);
        authController.login(email, password);

        // Assert
        assertTrue(result);
        User admin = stateController.getCurrentUser();
        assertNotNull(admin);
        assertEquals(email, admin.getEmail());
        assertEquals(hashedPassword, admin.getPassword());
    }

    @Test
    public void testCreateNewAdmin_AdminAlreadyExists() {
        // Arrange
        String email = "existingadmin@example.com";
        String password = "admin123";
        authController.createNewAdmin(email, password);

        // Act
        boolean result = authController.createNewAdmin(email, password);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testLogin_SuccessfulLogin() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        authController.createNewUser(email, password);

        // Act
        boolean result = authController.login(email, password);

        // Assert
        assertTrue(result);
        User user = stateController.getCurrentUser();
        assertNotNull(user);
    }

    @Test
    public void testLogin_IncorrectPassword() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        authController.createNewUser(email, password);

        // Act
        boolean result = authController.login(email, "incorrectpassword");

        // Assert
        assertFalse(result);
        assertNull(stateController.getCurrentUser());
    }

    @Test
    public void testLogin_UserDoesNotExist() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password123";

        // Act
        boolean result = authController.login(email, password);

        // Assert
        assertFalse(result);
        assertNull(stateController.getCurrentUser());
    }

    @Test
    public void testLogout_SuccessfulLogout() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        authController.createNewUser(email, password);
        authController.login(email, password);

        // Act
        boolean result = authController.logout();

        // Assert
        assertTrue(result);
        assertNull(stateController.getCurrentUser());
    }

    @Test
    public void testLogout_UserNotLoggedIn() {
        // Act
        boolean result = authController.logout();

        // Assert
        assertFalse(result);
        assertNull(stateController.getCurrentUser());
    }
}
