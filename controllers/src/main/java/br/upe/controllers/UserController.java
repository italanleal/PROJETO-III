package br.upe.controllers;

import br.upe.entities.Subscription;
import br.upe.entities.SystemAdmin;
import br.upe.entities.SystemUser;
import br.upe.entities.Userd;
import br.upe.util.controllers.EmailAlreadyInUse;
import br.upe.util.persistencia.SystemException;

public class UserController {
    private final StateController stateController;
    private final DAOController daoController;

    public UserController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

    public void updateUserName(String userName){

        if(stateController.getCurrentUser() instanceof SystemAdmin admin){
            admin.setName(userName);
            stateController.setCurrentUser(daoController.systemAdminDAO.update(admin));
            return;
        }

        if(stateController.getCurrentUser() instanceof SystemUser user) {
            user.setName(userName);
            stateController.setCurrentUser(daoController.systemUserDAO.update(user));
        }
    }
    public void updateUserEmail(String email) throws SystemException {
        Userd qUser = null;
        try {
            qUser = daoController.userDAO.findByEmail(email);
        } catch (SystemException ignored){}

        if(qUser != null) throw new EmailAlreadyInUse("Email used by other user", null);

        if(stateController.getCurrentUser() instanceof SystemAdmin admin){
            admin.setEmail(email);
            stateController.setCurrentUser(daoController.systemAdminDAO.update(admin));
            return;
        }
        if(stateController.getCurrentUser() instanceof SystemUser user) {
            user.setEmail(email);
            stateController.setCurrentUser(daoController.systemUserDAO.update(user));
        }
    }

    public void updateUserPassword(String password){
        if(stateController.getCurrentUser() instanceof SystemAdmin admin){
            admin.setPassword(password);
            stateController.setCurrentUser(daoController.systemAdminDAO.update(admin));
            return;
        }

        if(stateController.getCurrentUser() instanceof SystemUser user) {
            user.setPassword(password);
            stateController.setCurrentUser(daoController.systemUserDAO.update(user));
        }
    }
    public void updateUserSurname(String surname){
        if(stateController.getCurrentUser() instanceof SystemAdmin admin){
            admin.setSurname(surname);
            stateController.setCurrentUser(daoController.systemAdminDAO.update(admin));
            return;
        }

        if(stateController.getCurrentUser() instanceof SystemUser user) {
            user.setSurname(surname);
            stateController.setCurrentUser(daoController.systemUserDAO.update(user));
        }

    }
    public void removeSubscriptionFromUser(Subscription subscription){
       daoController.subscriptionDAO.delete(subscription);
    }
}
