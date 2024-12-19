package br.upe.facade;

import br.upe.controllers.*;
import br.upe.entities.Event;
import br.upe.entities.Userd;
import br.upe.util.controllers.ControllersInterface;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Facade {
    static Logger logger = Logger.getLogger(Facade.class.getName());
    public final StateController stateController;
    public final DAOController daoController;
    public final AuthController authController;
    public final UserController userController;
    public final EventController eventController;
    public final SessionController sessionController;
    public final SubEventController subEventController;
    public final SubscriptionController subscriptionController;
    public final SubmissionController submissionController;
    public Facade(LambdaEntityManagerFactory lambdaFunction) {
        daoController = ControllersInterface.newDAOController(lambdaFunction);
        stateController = ControllersInterface.newStateController(daoController);
        authController = ControllersInterface.newAuthController(stateController, daoController);
        userController = ControllersInterface.newUserController(stateController, daoController);
        eventController = ControllersInterface.newEventController(stateController, daoController);
        sessionController = ControllersInterface.newSessionController(stateController, daoController);
        subEventController = ControllersInterface.newSubEventController(stateController, daoController);
        subscriptionController = ControllersInterface.newSubscriptionController(stateController, daoController);
        submissionController = ControllersInterface.newSubmissionController(stateController, daoController);
    }
}