package br.upe.controllers;

public class SystemUserController {
    private final StateController stateController;
    private final DAOController daoController;

    public SystemUserController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

}
