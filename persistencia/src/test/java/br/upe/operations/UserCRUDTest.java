package br.upe.operations;

import br.upe.pojos.CommomUser;
import br.upe.pojos.User;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class UserCRUDTest {
    private UserCRUD userCRUD;
    private static final String STATE_PATH = ".\\state";
    private static final String USERS_PATH = STATE_PATH + "\\users.csv";
    private static final Logger logger = Logger.getLogger(UserCRUDTest.class.getName());

    @BeforeEach
    public void setup() {
        userCRUD = new UserCRUD();
    }

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

    @Test
    void testCreateUser() {
        CommomUser user = new CommomUser();
        UUID userUUID = UUID.randomUUID();
        user.setUuid(userUUID);
        user.setEmail("email@email.com");
        user.setName("John Doe");
        user.setPassword("password");
        user.setSubscriptions(new ArrayList<>());
        userCRUD.createUser(user);

        User retrievedUser = userCRUD.returnUser(userUUID);
        assertNotNull(retrievedUser);

        assertEquals(userUUID, retrievedUser.getUuid());
        assertEquals("email@email.com", retrievedUser.getEmail(), "O email deve ser igual ao esperado");
        assertEquals("John Doe", retrievedUser.getName(), "O nome deve ser igual ao esperado");
        assertEquals("password", retrievedUser.getPassword(), "A senha deve ser igual a esperada");
    }

    @Test
    void testUpdateUser() {
        CommomUser user = new CommomUser();
        UUID userUUID = UUID.randomUUID();
        user.setUuid(userUUID);
        user.setEmail("email@email.com");
        user.setName("John Doe");
        user.setPassword("password");
        user.setSubscriptions(new ArrayList<>());
        userCRUD.createUser(user);

        CommomUser aux = new CommomUser();

        aux.setEmail("email1@email.com");
        aux.setName("John Does");
        aux.setPassword("password2");
        aux.setSubscriptions(new ArrayList<>());
        userCRUD.updateUser(userUUID, aux);

        User retrievedUser = userCRUD.returnUser(userUUID);
        assertNotNull(retrievedUser);
        assertEquals(userUUID, retrievedUser.getUuid(), "O UUID do usuário deve ser igual ao esperado");
        assertEquals("email1@email.com", retrievedUser.getEmail(), "Email deve ser igual ao esperado");
        assertEquals("John Does", retrievedUser.getName(), "Nome deve ser igual ao esperado");
        assertEquals("password2", retrievedUser.getPassword(), "A senha mudada deve ser igual ao esperado");
    }

    @Test
    void testDeleteUser() {
        CommomUser user = new CommomUser();
        UUID userUUID = UUID.randomUUID();
        user.setUuid(userUUID);
        user.setEmail("email@email.com");
        user.setName("John Doe");
        user.setPassword("password");
        user.setSubscriptions(new ArrayList<>());
        userCRUD.createUser(user);

        userCRUD.deleteUser(userUUID);
        User retrievedUser = userCRUD.returnUser(userUUID);
        assertNull(retrievedUser, "O usuário removido deve ser nulo");
    }

    @Test
    void testReturnUser() {
        CommomUser user = new CommomUser();
        UUID userUUID = UUID.randomUUID();
        user.setUuid(userUUID);
        user.setEmail("jose.mario@teste.com");
        user.setName("José Mario");
        user.setPassword("jmsenha");
        user.setSubscriptions(new ArrayList<>());

        userCRUD.createUser(user);
        User retrievedUser = UserCRUD.returnUser(userUUID);

        assertNotNull(retrievedUser, "O usuário recuperado não deve ser nulo");
        assertEquals(userUUID, retrievedUser.getUuid(), "O UUID do usuário deve ser igual ao esperado");
        assertEquals("jose.mario@teste.com", retrievedUser.getEmail(), "O email do usuário deve ser igual ao esperado");
        assertEquals("José Mario", retrievedUser.getName(), "O nome do usuário deve ser igual ao esperado");
        assertEquals("jmsenha", retrievedUser.getPassword(), "A senha do usuário deve ser igual ao esperado");
    }

    @Test
    void testReturnAllUsers() {
        CommomUser user = new CommomUser();
        UUID userUUID = UUID.randomUUID();
        user.setUuid(userUUID);
        user.setEmail("email@email.com");
        user.setName("John Doe");
        user.setPassword("password");
        user.setSubscriptions(new ArrayList<>());
        userCRUD.createUser(user);

        CommomUser user1 = new CommomUser();
        UUID userUUID1 = UUID.randomUUID();
        user1.setUuid(userUUID1);
        user1.setEmail("email@email.com");
        user1.setName("John Doe");
        user1.setPassword("password");
        user1.setSubscriptions(new ArrayList<>());
        userCRUD.createUser(user1);

        Collection<User> retrievedUsers = userCRUD.returnUser();

        assertNotNull(retrievedUsers);
        assertEquals(2, retrievedUsers.size(), "Todos os usuarios cadastrados devem ser retornados");
    }
}
