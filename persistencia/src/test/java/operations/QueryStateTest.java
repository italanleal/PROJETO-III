package operations;
import br.upe.operations.UserCRUD;
import br.upe.operations.QueryState;
import br.upe.pojos.KeeperInterface;
import br.upe.pojos.User;
import org.junit.jupiter.api.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class QueryStateTest {
    private static final String STATE_PATH = ".\\state";
    private static final String USERS_PATH = STATE_PATH+"\\users.csv";
    private static final Logger logger = Logger.getLogger(QueryStateTest.class.getName());
    private UserCRUD userCRUD;
    @AfterEach
    void clearFiles() {
        try {
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
    void setUp() {
        userCRUD = new UserCRUD();
    }
    @Test
    void testUserFromEmail(){
        String userEmail = "test@email.com";
        User user = KeeperInterface.createCommomUser();
        user.setSubscriptions(new ArrayList<>());
        user.setUuid(UUID.randomUUID());
        user.setEmail(userEmail);
        user.setName("John Doe");
        user.setPassword("password");

        userCRUD.createUser(user);

        User retrievedUser = userCRUD.returnUser(QueryState.userFromEmail(userEmail));

        assertNotNull(retrievedUser);
        assertEquals(user.getUuid(), retrievedUser.getUuid());
        assertEquals(userEmail, retrievedUser.getEmail());
        assertEquals(user.getName(), retrievedUser.getName());
        assertEquals(user.getPassword(), retrievedUser.getPassword());
    }

}
