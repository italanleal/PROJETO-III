package br.upe.userinterface;

import br.upe.controllers.*;

public class AppStateController {
    public static final CRUDController crudController = ControllersInterface.newCRUDController();
    public static final StateController stateController = ControllersInterface.newStateController();
    public static final AuthController authController = ControllersInterface.newAuthController(stateController, crudController);
    public static final UserController userController = ControllersInterface.newUserController(stateController, crudController);
    public static final EventController eventController = ControllersInterface.newEventController(stateController, crudController);
    public static final SessionController sessionController = ControllersInterface.newSessionController(stateController, crudController);
    private AppStateController() {}
}
