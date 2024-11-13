package br.upe.controllers;

import br.upe.entities.SystemAdmin;
import br.upe.entities.SystemUser;
import br.upe.entities.User;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;
import br.upe.util.controllers.UserAlreadyExistsException;
import br.upe.util.persistencia.UserNotFoundException;
import br.upe.util.controllers.IncorrectPasswordException;

public class AuthController {
    public AuthController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }
    private final StateController stateController;
    private final DAOController daoController;

    public void createNewUser(String name, String surname, String cpf, String email, String password) throws SystemException {
        try {
            if (daoController.systemUserDAO.findByCPF(email) == null) {
                SystemUser newUser = PersistenciaInterface.createSystemUser();
                newUser.setName(name);
                newUser.setSurname(surname);
                newUser.setCpf(cpf);
                newUser.setEmail(email);
                newUser.setPassword(password);
                daoController.systemUserDAO.save(newUser);
            }
        } catch (UserNotFoundException e) {
            throw new UserAlreadyExistsException("User already exists", null);
        }
    }

    public void createNewAdmin(String name, String surname, String cpf, String email, String password) throws SystemException{
        // Check if a user with the same email already exists
        try {
            if (daoController.systemUserDAO.findByEmail(email) == null) {
                SystemAdmin newUser = PersistenciaInterface.createSystemAdmin();
                newUser.setName(name);
                newUser.setSurname(surname);
                newUser.setCpf(cpf);
                newUser.setEmail(email);
                newUser.setPassword(password);
                daoController.systemAdminDAO.save(newUser);
            }
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException("User already exists", e.getCause());
        }
    }

    public void login(String email, String password) throws SystemException {
        User user = null;
        try {
            user = daoController.systemAdminDAO.findByEmail(email);
        } catch(SystemException ignored) {
        }
        if(user == null ){
            try {
                user = daoController.systemUserDAO.findByEmail(email);
            } catch(SystemException e) {
                throw new UserNotFoundException(e.getMessage(), e.getCause());
            }
        }
        if(password.equals(user.getPassword())) {
            stateController.setCurrentUser(user);
        } else {
            throw new IncorrectPasswordException("Incorrect Password", null);
        }
    }

    public void logout(){
        stateController.setCurrentUser(null);
    }
}
