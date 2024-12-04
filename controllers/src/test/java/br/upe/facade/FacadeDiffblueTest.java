package br.upe.facade;

import br.upe.util.controllers.ControllersInterface;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacadeDiffblueTest {
    /**
     * Test {@link Facade#printA(String)}.
     */
    public final Logger logger = Logger.getLogger(FacadeDiffblueTest.class.getName());
    public final Facade facade = ControllersInterface.newFacade(PersistenciaInterface.getDevelopEMF_lambda());

    String name = "TESTE1";
    String surname= "TESTE1";
    String cpf = "111.111.111-11";
    String email = "TESTE1@gmail.com";
    String password = "PASSWORD1";

    String name2 = "TESTE2";
    String surname2 = "TESTE2";
    String cpf2 = "222.222.222-22";
    String email2 = "TESTE2@gmail.com";
    String password2 = "PASSWORD2";

    String eventTitle1 = "SU";
    String eventDescription1 = "Semana universitária dos estudantes da UPE";
    String eventDirector1 = "Pontual";
    LocalDate startDate1 = LocalDate.parse("2024-12-20");
    LocalDate endDate1 = LocalDate.parse("2024-12-24");

    //----------------------------------StateController tests----------------------------------
    @Test
    @DisplayName("State: currentUser != null")
    void currentUserNotNullTest(){
        try{
            facade.authController.createNewUser(name, surname, cpf, email, password);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
        Assertions.assertNotNull(facade.stateController.getCurrentUser());
        Assertions.assertEquals(cpf, facade.stateController.getCurrentUser().getCpf());
    }

    @Test
    @DisplayName("State: CurrentEvent != null")
    void currentEventNotNullTest(){
        try{
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
        try {
            facade.authController.login(email, password);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
        try{
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
        Assertions.assertNotNull(facade.stateController.getCurrentEvent());
        Assertions.assertEquals(eventDirector1, facade.stateController.getCurrentEvent().getDirector());
    }

    @Test
    @DisplayName("State: currentSession != null")
    void currentSessionNotNullTest(){
        try{
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
        try {
            facade.authController.login(email, password);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
        try{
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate1, endDate1);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }

        facade.sessionController.createNewSession(eventTitle1, eventDescription1, name2, "UPE");
        Assertions.assertNotNull(facade.stateController.getCurrentSession());
        Assertions.assertEquals("UPE", facade.stateController.getCurrentSession().getTitle());
    }
    //TODO
    //----------------------------------DAOController tests----------------------------------

    //----------------------------------AuthController tests----------------------------------
    @Test
    @DisplayName("Test create admin")
    void givenParams_whenCreateAdmin_thenProceed() {
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

    @Test
    @DisplayName("Test change User")
    void changeUserTest(){
        try {
            facade.authController.createNewUser(name, surname, cpf, email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            Assertions.fail();
        }
        try {
            facade.authController.createNewUser(name2, surname2, cpf2, email2, password2);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            Assertions.fail();
        }

        try{
            facade.authController.login(email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }

        try{
            Assertions.assertEquals(facade.daoController.systemUserDAO.findByCPF(cpf), facade.stateController.getCurrentUser());
        }catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }

        facade.authController.logout();

        try{
            facade.authController.login(email2, password2);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }

        try{
            Assertions.assertEquals(facade.daoController.systemUserDAO.findByCPF(cpf2), facade.stateController.getCurrentUser());
        }catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
    }

    @Test
    @DisplayName("Valid login Test")
    void validLoginTest(){
        try {
            facade.authController.createNewUser(name, surname, cpf, email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            Assertions.fail();
        }

        try{
            facade.authController.login(email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            Assertions.fail();
        }

        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Invalid login Test")
    void invalidLoginTest(){
        try {
            facade.authController.createNewUser(name, surname, cpf, email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }

        try{
            facade.authController.login(email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }

        try {
            facade.authController.createNewUser(name2, surname2, cpf2, email2, password2);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }

        try{
            facade.authController.login(email2, password2);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            Assertions.assertTrue(true);
        }
        Assertions.fail();
    }
    @Test
    @DisplayName("LogOut")
    void logOutTest(){
        try {
            facade.authController.createNewUser(name, surname, cpf, email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }

        try{
            facade.authController.login(email, password);
        } catch(SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }

        facade.authController.logout();
        Assertions.assertNull(facade.stateController.getCurrentUser());
    }
    //todo
    //----------------------------------UserController tests----------------------------------

    //----------------------------------EventControllerTests----------------------------------
    @Test
    @DisplayName("EventController: Invalid date for event")
    void setInvalidDateForEvent(){
        try{
            facade.authController.createNewAdmin(name, surname, cpf, email, password);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
        try {
            facade.authController.login(email, password);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }

        LocalDate startDate = LocalDate.parse("2024-12-20");
        LocalDate endDateBeforeStartDate = LocalDate.parse("2024-12-19");
        try{
            facade.eventController.createNewEvent(eventTitle1, eventDescription1, eventDirector1, startDate, endDateBeforeStartDate);
        } catch (SystemException e){
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            Assertions.fail(e.getCause());
        }
        Assertions.assertNull(facade.stateController.getCurrentEvent());
    }

    //todo
    //----------------------------------SessionController tests----------------------------------
    //----------------------------------SubEvent tests----------------------------------
    //----------------------------------Subscription tests----------------------------------
    //----------------------------------Submission tests----------------------------------
}
