package br.upe.userinterface;
import  br.upe.controllers.*;
import br.upe.util.ControllersInterface;
import br.upe.util.PersistenciaInterface;

public class AppStateController {
    public static final DAOController daoController = ControllersInterface.newDAOController(PersistenciaInterface.getDevelopEMF_lambda());
    public static final StateController stateController = ControllersInterface.newStateController();
    public static final AuthController authController = ControllersInterface.newAuthController(stateController, daoController);
    public static final UserController userController = ControllersInterface.newUserController(stateController, daoController);
    public static final EventController eventController = ControllersInterface.newEventController(stateController, daoController);
    public static final SessionController sessionController = ControllersInterface.newSessionController(stateController, daoController);
    private AppStateController() {}
}
