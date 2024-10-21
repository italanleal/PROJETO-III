package br.upe.UserInterface;

import br.upe.controllers.*;

public class AppStateController {
    public static final CRUDController crudController = ControllersInterface.newCRUDController();
    public static final StateController stateController = ControllersInterface.newStateController();
    public static final AuthController authController = ControllersInterface.newAuthController(stateController, crudController);
    public static UserController userController = ControllersInterface.newUserController(stateController, crudController);
    private AppStateController() {}
}
