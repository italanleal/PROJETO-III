package br.upe.controllers;

public class EventController {
    private final StateController stateController;
    private final DAOController daoController;
    public EventController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }
}
