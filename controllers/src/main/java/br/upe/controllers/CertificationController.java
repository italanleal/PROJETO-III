package br.upe.controllers;

public class CertificationController {
    private final StateController stateController;
    private final DAOController daoController;
    public CertificationController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }
}
