package br.upe.controllers;

public class SubEventController {
    private final StateController stateController;
    private final DAOController daoController;
    public SubEventController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }
}
