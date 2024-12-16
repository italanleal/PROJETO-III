package br.upe.controllers;

import br.upe.entities.SystemAdmin;
import br.upe.entities.SystemUser;
import br.upe.entities.User;

public class UserController {
    private final StateController stateController;
    private final DAOController daoController;

    public UserController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

    public void updateUserName(String userName){
        User source;
        if(stateController.getCurrentUser() instanceof SystemAdmin){
            source = stateController.getCurrentUser();
            source.setName(userName);
            daoController.systemAdminDAO.update((SystemAdmin) source);
        } else {
            source = stateController.getCurrentUser();
            source.setName(userName);
            daoController.systemUserDAO.update((SystemUser) source);
        }

    }
    public void updateUserEmail(String email){
        User source;
        if(stateController.getCurrentUser() instanceof SystemAdmin){
            source = stateController.getCurrentUser();
            source.setEmail(email);
            daoController.systemAdminDAO.update((SystemAdmin) source);
        } else {
            source = stateController.getCurrentUser();
            source.setEmail(email);
            daoController.systemUserDAO.update((SystemUser) source);
        }

    }
    public void updateUserPassword(String Password){
        User source;
        if(stateController.getCurrentUser() instanceof SystemAdmin){
            source = stateController.getCurrentUser();
            source.setPassword(Password);
            daoController.systemAdminDAO.update((SystemAdmin) source);
        } else {
            source = stateController.getCurrentUser();
            source.setPassword(Password);
            daoController.systemUserDAO.update((SystemUser) source);
        }
    }
    public void updateUserSurname(String surname){
        User source;
        if(stateController.getCurrentUser() instanceof SystemAdmin){
            source = stateController.getCurrentUser();
            source.setSurname(surname);
            daoController.systemAdminDAO.update((SystemAdmin) source);
        } else {
            source = stateController.getCurrentUser();
            source.setSurname(surname);
            daoController.systemUserDAO.update((SystemUser) source);
        }

    }
}
