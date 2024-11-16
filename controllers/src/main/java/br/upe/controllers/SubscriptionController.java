package br.upe.controllers;

public class SubscriptionController {
    private final StateController stateController;
    private final DAOController daoController;
    public SubscriptionController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }
}
