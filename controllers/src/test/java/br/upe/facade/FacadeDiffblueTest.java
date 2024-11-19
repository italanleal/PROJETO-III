package br.upe.facade;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.upe.util.controllers.ControllersInterface;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;
import org.apache.openjpa.persistence.EntityManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

class FacadeDiffblueTest {
    /**
     * Test {@link Facade#printA(String)}.
     */
    public final Logger logger = Logger.getLogger(FacadeDiffblueTest.class.getName());
    public final Facade facade = ControllersInterface.newFacade(PersistenciaInterface.getDevelopEMF_lambda());
    @Test
    @DisplayName("Test create admin")
    void givenParams_whenCreateAdmin_thenProceed() {
        String name = "TESTE1";
        String surname= "TESTE1";
        String cpf = "111.111.111-11";
        String email = "TESTE1@gmail.com";
        String password = "PASSWORD1";
        try {
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            Assertions.fail();
        }
        Assertions.assertTrue(true);
    }
    @Test
    @DisplayName("Test create admin when email is already in use")
    void givenUsedParams_whenCreateAdmin_thenFails() {
        String name = "TESTE10";
        String surname= "TESTE10";
        String cpf = "100.100.100-10";
        String email = "TESTE10@gmail.com";
        String password = "PASSWORD10";
        try {
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            Assertions.fail();
        }
        try {
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
            Assertions.fail();
        } catch(SystemException e){
            Assertions.assertTrue(true);
        }
    }
    @Test
    @DisplayName("Test create user")
    void givenParams_whenCreateUser_thenProceed() {
        String name = "TESTE2";
        String surname= "TESTE2";
        String cpf = "222.222.222-22";
        String email = "TESTE2@gmail.com";
        String password = "PASSWORD2";
        try {
            facade.authController.createNewUser(name, surname, cpf, email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            Assertions.fail();
        }
        Assertions.assertTrue(true);
    }
    @Test
    @DisplayName("Test create user when email is already in use")
    void givenUsedParams_whenCreateUser_thenFails() {
        String name = "TESTE20";
        String surname= "TESTE20";
        String cpf = "200.200.200-20";
        String email = "TESTE20@gmail.com";
        String password = "PASSWORD20";
        try {
            facade.authController.createNewUser(name, surname, cpf, email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            Assertions.fail();
        }
        try {
            facade.authController.createNewUser(name, surname, cpf, email, password);
            Assertions.fail();
        } catch(SystemException e){
            Assertions.assertTrue(true);
        }
    }
}
