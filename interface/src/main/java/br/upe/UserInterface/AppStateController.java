package br.upe.UserInterface;

import br.upe.controllers.AuthController;
import br.upe.controllers.ControllersInterface;
import br.upe.controllers.CRUDController;
import br.upe.controllers.StateController;

public class AppStateController {
    public static final CRUDController crudController = ControllersInterface.newCRUDController();
    public static final StateController stateController = ControllersInterface.newStateController();
    public static final AuthController authController = ControllersInterface.newAuthController(stateController, crudController);

    private AppStateController() {}
}
