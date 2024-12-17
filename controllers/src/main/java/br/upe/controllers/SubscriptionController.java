package br.upe.controllers;

import br.upe.entities.Session;
import br.upe.entities.Subscription;
import br.upe.entities.SystemAdmin;
import br.upe.entities.SystemUser;
import br.upe.util.persistencia.PersistenciaInterface;

public class SubscriptionController {
    private final StateController stateController;
    private final DAOController daoController;
    public SubscriptionController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }
}
