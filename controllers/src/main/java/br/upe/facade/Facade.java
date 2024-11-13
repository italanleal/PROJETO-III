package br.upe.facade;

import br.upe.controllers.AuthController;
import br.upe.controllers.DAOController;
import br.upe.controllers.StateController;
import br.upe.util.ControllersInterface;
import br.upe.util.LambdaEntityManagerFactory;
import br.upe.util.PersistenciaInterface;

public class Facade {
    public final StateController stateController = ControllersInterface.newStateController();
    public final DAOController daoController = ControllersInterface.newDAOController(PersistenciaInterface.getDevelopEMF_lambda());
    public final AuthController authController = ControllersInterface.newAuthController(stateController, daoController);
}