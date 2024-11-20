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
        // Check if a user with the same email already exists
        SystemUser newUser = null;
        try {
            newUser = daoController.systemUserDAO.findByEmail(email);
        } catch (SystemException ignored){}

        if(newUser != null) throw new UserAlreadyExistsException("User with the following email already exists: " + email, null);

        newUser = PersistenciaInterface.createSystemUser();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setCpf(cpf);
        newUser.setEmail(email);
        newUser.setPassword(password);
        daoController.systemUserDAO.save(newUser);
    }

    public void createNewAdmin(String name, String surname, String cpf, String email, String password) throws SystemException{
        // Check if a user with the same email already exists
        SystemAdmin newUser = null;
        try {
            newUser = daoController.systemAdminDAO.findByEmail(email);
        } catch (SystemException ignored){}

        if(newUser != null) throw new UserAlreadyExistsException("User with the following email already exists: " + email, null);

        newUser = PersistenciaInterface.createSystemAdmin();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setCpf(cpf);
        newUser.setEmail(email);
        newUser.setPassword(password);
        daoController.systemAdminDAO.save(newUser);
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
