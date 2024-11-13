package br.upe.controllers;

import br.upe.entities.SystemAdmin;
import br.upe.entities.SystemUser;
import br.upe.entities.User;
import br.upe.util.UserNotFoundException;
import jakarta.persistence.EntityManager;

public class AuthController {
    public AuthController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }
    private final StateController stateController;
    private final DAOController daoController;

    public boolean createNewUser(String name, String surname, String cpf, String email, String password) throws UserNotFoundException {
        // Check if a user with the same email already exists
        try {
            if (daoController.systemUserDAO.findByEmail(email) != null) {
                throw new UserNotFoundException("User already exists");
            }
        } catch (UserNotFoundException e) {
            // No user found with this email, proceed to create a new one
        }

        // Begin a transaction
        EntityManager entityManager = daoController.systemUserDAO.createEntityManager.call();
        entityManager.getTransaction().begin();

        try {
            // Create and persist the new User
            SystemUser newUser = new SystemUser();
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setCpf(cpf);
            newUser.setEmail(email);
            newUser.setPassword(password);

            entityManager.persist(newUser);

            // Commit the transaction
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            // Rollback in case of an error
            entityManager.getTransaction().rollback();
            throw e; // Optionally handle or rethrow the exception as needed
        } finally {
            // Close the entity manager if it's not managed by a factory
            entityManager.close();
        }
    }


    public boolean createNewAdmin(String name, String surname, String cpf, String email, String password){

    }

    public boolean login(String email, String password){

    }

    public boolean logout(){

    }
}
