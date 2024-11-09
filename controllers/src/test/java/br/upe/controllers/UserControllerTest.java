package br.upe.controllers;

import br.upe.pojos.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private static final Logger logger = Logger.getLogger(UserControllerTest.class.getName());

    private StateController stateController;
    private CRUDController crudController;
    private UserController userController;

    /*-------------------SetUp variables-------------------*/
    private static final String STATE_PATH = ".\\state";
    private static final String USERS_PATH = STATE_PATH+"\\users.csv";
    private static final String EVENTS_PATH = STATE_PATH+"\\events.csv";
    private static final String SUBSCRIPTIONS_PATH = STATE_PATH+"\\subscriptions.csv";
    private static final String SESSIONS_PATH = STATE_PATH+"\\sessions.csv";
    private static final String SUBMISSIONS_PATH = STATE_PATH+"\\submissions.csv";



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
        crudController = ControllersInterface.newCRUDController();
        userController = ControllersInterface.newUserController(stateController, crudController);
    }

    @Test
    public void testUpdateUserName() {

        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        UserController user = new UserController(state, crud);

        auth.createNewAdmin("fernando@upe.br", "password15");
        auth.login("fernando@upe.br", "password15");

        user.updateUserName("fernandoétop");

        User updatedUser = crud.userCRUD.returnUser(state.getCurrentUser().getUuid());
        assertNotNull(updatedUser);
        assertEquals("fernandoétop", updatedUser.getName());

        auth.logout();
    }
    @Test
    public void testUpdateUserEmail() {

        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        UserController user = new UserController(state, crud);


        auth.createNewAdmin("mirelemoutinho@upe.br", "senha24");
        auth.login("mirelemoutinho@upe.br", "senha24");

        user.updateUserEmail("jackinho@upe.br");

        User updatedUser = crud.userCRUD.returnUser(state.getCurrentUser().getUuid());
        assertNotNull(updatedUser);
        assertEquals("jackinho@upe.br", updatedUser.getEmail());

        auth.logout();
    }

//    @Test
//    public void testUpdateUserPassword() {
//
//        StateController state = new StateController();
//        CRUDController crud = new CRUDController();
//        AuthController auth = new AuthController(state, crud);
//        UserController user = new UserController(state, crud);
//
//        auth.createNewAdmin("admin.bruce.wayne@upe.br", "securePass3!");
//        auth.login("admin.bruce.wayne@upe.br", "securePass3!");
//
//        user.updateUserPassword("BatMan@2024");
//
//        User updatedUser = crud.userCRUD.returnUser(state.getCurrentUser().getUuid());
//        assertNotNull(updatedUser);
//        assertEquals("BatMan@2024", updatedUser.getPassword());
//
//        auth.logout();
//    }

}



