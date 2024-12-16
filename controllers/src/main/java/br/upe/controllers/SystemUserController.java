package br.upe.controllers;

public class SystemUserController extends UserController {
    private final StateController stateController = null;
    private final DAOController daoController = null;

    public SystemUserController(StateController stateController, DAOController daoController) {
        super(stateController, daoController);
    }

}
