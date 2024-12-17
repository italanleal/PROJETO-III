package br.upe.controllers;

import br.upe.entities.*;
import br.upe.util.persistencia.PersistenciaInterface;

import java.time.LocalDate;

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
            daoController.systemAdminDAO.update(admin);
            return;
        }

        if(stateController.getCurrentUser() instanceof SystemUser user) {
            user.setName(userName);
            daoController.systemUserDAO.update(user);
        }
    }
    public void updateUserEmail(String email){
        if(stateController.getCurrentUser() instanceof SystemAdmin admin){
            admin.setEmail(email);
            daoController.systemAdminDAO.update(admin);
            return;
        }

        if(stateController.getCurrentUser() instanceof SystemUser user) {
            user.setEmail(email);
            daoController.systemUserDAO.update(user);
        }
    }
    public void updateUserPassword(String password){
        if(stateController.getCurrentUser() instanceof SystemAdmin admin){
            admin.setPassword(password);
            daoController.systemAdminDAO.update(admin);
            return;
        }

        if(stateController.getCurrentUser() instanceof SystemUser user) {
            user.setPassword(password);
            daoController.systemUserDAO.update(user);
        }
    }
    public void updateUserSurname(String surname){
        if(stateController.getCurrentUser() instanceof SystemAdmin admin){
            admin.setSurname(surname);
            daoController.systemAdminDAO.update(admin);
            return;
        }

        if(stateController.getCurrentUser() instanceof SystemUser user) {
            user.setSurname(surname);
            daoController.systemUserDAO.update(user);
        }

    }
    public void removeSubscriptionFromUser(Subscription subscription){
       daoController.subscriptionDAO.delete(subscription);
    }
}
