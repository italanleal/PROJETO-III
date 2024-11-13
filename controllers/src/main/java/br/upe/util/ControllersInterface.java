package br.upe.util;

import br.upe.controllers.AuthController;
import br.upe.controllers.DAOController;
import br.upe.controllers.StateController;

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
}