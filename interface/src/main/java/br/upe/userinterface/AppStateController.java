package br.upe.userinterface;
import br.upe.controllers.*;
import br.upe.facade.Facade;
import br.upe.util.controllers.ControllersInterface;
import br.upe.util.persistencia.PersistenciaInterface;

public class AppStateController {
    public static final StateController stateController;
    public static final DAOController daoController;
    public static final AuthController authController;
    public static final UserController userController;
    public static final EventController eventController;
    public static final SessionController sessionController;
    public static final SubEventController subEventController;
    public static final SubscriptionController subscriptionController;
    public static final SubmissionController submissionController;
    static {
        Facade facade = ControllersInterface.newFacade(PersistenciaInterface.getDefaultEMF_lambda());
        stateController = facade.stateController;
        daoController = facade.daoController;
        authController = facade.authController;
        userController = facade.userController;
        eventController = facade.eventController;
        sessionController = facade.sessionController;
        subEventController = facade.subEventController;
        subscriptionController = facade.subscriptionController;
        submissionController = facade.submissionController;
    }
}
