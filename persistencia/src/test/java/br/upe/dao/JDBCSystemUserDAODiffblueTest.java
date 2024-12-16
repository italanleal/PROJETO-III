package br.upe.dao;

import static org.mockito.Mockito.mock;

import br.upe.entities.SystemAdmin;
import br.upe.entities.SystemUser;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
public class JDBCSystemUserDAODiffblueTest {
    /**
     * Test {@link JDBCSystemUserDAO#JDBCSystemUserDAO(LambdaEntityManagerFactory)}.
     */
    private final Logger logger = Logger.getLogger(JDBCSystemUserDAODiffblueTest.class.getName());
    private final JDBCSystemUserDAO userDAO = PersistenciaInterface.createJDBCSystemUserDAO(PersistenciaInterface.getDevelopEMF_lambda());

    @Test
    @DisplayName("Test save method for userDAO")
    void saveUserTest() {
        SystemUser user = PersistenciaInterface.createSystemUser();
        user.setCpf("111.111.111-11");
        user.setName("USER1");
        user.setSurname("USER1");
        user.setEmail("USER1@EMAIL.COM");
        user.setPassword("PASSWORD1");

        userDAO.save(user);
        Assertions.assertNotEquals(userDAO.findById(user.getId()), Optional.empty());
    }

    @Test
    @DisplayName("Test update method for userDAO")
    public void updateUserTest() {
        SystemUser user = PersistenciaInterface.createSystemUser();
        user.setCpf("111.111.111-11");
        user.setName("USER1");
        user.setSurname("USER1");
        user.setEmail("USER1@EMAIL.COM");
        user.setPassword("PASSWORD");

        userDAO.save(user);

        user.setCpf("222.222.222-22");
        user.setName("USER2");
        user.setSurname("USER2");
        user.setEmail("USER2@EMAIL.COM");
        user.setPassword("PASSWORD2");
        userDAO.update(user);

        Assertions.assertEquals(user.getName(), "USER2");
    }

    @Test
    @DisplayName("Test delete method for userDAO")
    public void deleteUserTest() {
        SystemUser user = PersistenciaInterface.createSystemUser();
        user.setCpf("111.111.111-11");
        user.setName("USER1");
        user.setSurname("USER1");
        user.setEmail("USER1@EMAIL.COM");
        user.setPassword("PASSWORD");

        userDAO.save(user);

        Long id = user.getId();
        userDAO.delete(user);
        Assertions.assertEquals(userDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test deleteById method for userDAO")
    public void deleteByIdUserTest() {
        SystemUser user = PersistenciaInterface.createSystemUser();
        user.setCpf("111.111.111-11");
        user.setName("USER1");
        user.setSurname("USER1");
        user.setEmail("USER1@EMAIL.COM");
        user.setPassword("PASSWORD");

        userDAO.save(user);

        Long id = user.getId();
        userDAO.deleteById(user.getId());
        Assertions.assertEquals(userDAO.findById(id), Optional.empty());
    }
    @Test
    @DisplayName("Test findById method for userDAO")
    void findByIdUserTest(){
        SystemUser user = PersistenciaInterface.createSystemUser();
        user.setCpf("111.111.111-11");
        user.setName("USER1");
        user.setSurname("USER1");
        user.setEmail("USER1@EMAIL.COM");
        user.setPassword("PASSWORD");

        userDAO.save(user);
        Assertions.assertEquals(userDAO.findById(user.getId()).get(), user);
    }

    @Test
    @DisplayName("Test findAll method for userDAO")
    public void findAllUserTest() {
        SystemUser user = PersistenciaInterface.createSystemUser();
        user.setCpf("111.111.111-11");
        user.setName("USER1");
        user.setSurname("USER1");
        user.setEmail("USER1@EMAIL.COM");
        user.setPassword("PASSWORD");

        userDAO.save(user);

        List<SystemUser> users = userDAO.findAll();
        Assertions.assertFalse(users.isEmpty());
    }
}
