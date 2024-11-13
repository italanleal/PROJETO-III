package br.upe.util.controllers;

import br.upe.controllers.*;
import br.upe.util.persistencia.LambdaEntityManagerFactory;

public interface ControllersInterface {
    static DAOController newDAOController(LambdaEntityManagerFactory lambdaEntityManagerFactory) {
        return new DAOController(lambdaEntityManagerFactory);
    }
    static StateController newStateController() {
        return new StateController();
    }
    static AuthController newAuthController(StateController stateController, DAOController daoController) {
        return new AuthController(stateController, daoController);
    }
    static SubmissionController newSubmissionController(StateController stateController, DAOController daoController) {
        return new SubmissionController(stateController, daoController);
    }
    static UserController newUserController(StateController stateController, DAOController daoController) {
        return new UserController(stateController, daoController);
    }
}