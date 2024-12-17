package br.upe.util.controllers;

import br.upe.controllers.*;
import br.upe.facade.Facade;
import br.upe.util.persistencia.LambdaEntityManagerFactory;

public interface ControllersInterface {
    static DAOController newDAOController(LambdaEntityManagerFactory lambdaEntityManagerFactory) {
        return new DAOController(lambdaEntityManagerFactory);
    }
    static StateController newStateController(DAOController daoController) {
        return new StateController(daoController);
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
    static EventController newEventController(StateController stateController, DAOController daoController) {
        return new EventController(stateController, daoController);
    }
    static SubEventController newSubEventController(StateController stateController, DAOController daoController) {
        return new SubEventController(stateController, daoController);
    }
    static SessionController newSessionController(StateController stateController, DAOController daoController) {
        return new SessionController(stateController, daoController);
    }
    static SubscriptionController newSubscriptionController(StateController stateController, DAOController daoController) {
        return new SubscriptionController(stateController, daoController);
    }

    static Facade newFacade(LambdaEntityManagerFactory lambdaFunction) {
        return new Facade(lambdaFunction);
    }
}