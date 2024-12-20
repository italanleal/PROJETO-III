package br.upe.controllers;

import br.upe.entities.SystemAdmin;
import br.upe.entities.SystemUser;
import br.upe.entities.Userd;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;
import br.upe.util.controllers.UserAlreadyExistsException;
import br.upe.util.persistencia.UserNotFoundException;
import br.upe.util.controllers.IncorrectPasswordException;

import java.util.Optional;

public class AuthController {
    public AuthController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }
    private final StateController stateController;
    private final DAOController daoController;

    public void createNewUser(String name, String surname, String cpf, String email, String password) throws SystemException {
        // Check if a user with the same email already exists
        Userd qUser = null;
        try {
            qUser = daoController.userDAO.findByEmail(email);
        } catch (SystemException ignored){}

        if(qUser != null) throw new UserAlreadyExistsException("Userd with the following email already exists: " + email, null);

        SystemUser newUser = PersistenciaInterface.createSystemUser();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setCpf(cpf);
        newUser.setEmail(email);
        newUser.setPassword(password);
        daoController.systemUserDAO.save(newUser);
    }

    public void createNewAdmin(String name, String surname, String cpf, String email, String password) throws SystemException{
        // Check if a user with the same email already exists
        Userd qUser = null;
        try {
            qUser = daoController.userDAO.findByEmail(email);
        } catch (SystemException ignored){}

        if(qUser != null) throw new UserAlreadyExistsException("Userd with the following email already exists: " + email, null);

        SystemAdmin newUser = PersistenciaInterface.createSystemAdmin();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setCpf(cpf);
        newUser.setEmail(email);
        newUser.setPassword(password);
        daoController.systemAdminDAO.save(newUser);
    }

    public void login(String email, String password) throws SystemException {

        Userd qUser = null;
        try {
            qUser = daoController.userDAO.findByEmail(email);
        } catch (SystemException e){
            throw new UserNotFoundException(e.getMessage(), e.getCause());
        }

        if(qUser instanceof SystemAdmin admin){
            Optional<SystemAdmin> systemAdmin = daoController.systemAdminDAO.findById(qUser.getId());
            if(!(password.equals(systemAdmin.get().getPassword()))) throw new IncorrectPasswordException("Incorrect Password", null);
            systemAdmin.ifPresent(stateController::setCurrentUser);
            daoController.systemAdminDAO.detach(admin);
            stateController.refresh();
            return;
        }
        if(qUser instanceof SystemUser user){
            Optional<SystemUser> fUser = daoController.systemUserDAO.findById(user.getId());
            if(!(password.equals(fUser.get().getPassword()))) throw new IncorrectPasswordException("Incorrect Password", null);
            fUser.ifPresent(stateController::setCurrentUser);
            daoController.systemUserDAO.detach(user);
            stateController.refresh();
        }
    }

    public void logout(){
        stateController.setCurrentUser(null);
    }
}
