package br.upe.controllers;

import br.upe.entities.User;
import br.upe.util.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class SystemUserController {
    private final StateController stateController;
    private final DAOController daoController;

    public SystemUserController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

}
