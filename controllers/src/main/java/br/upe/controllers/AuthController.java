package br.upe.controllers;

import br.upe.operations.HasherInterface;
import br.upe.operations.QueryState;
import br.upe.operations.UserCRUD;
import br.upe.pojos.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    private final StateController stateController;
    private final CRUDController crudController;

    public AuthController(StateController stateController, CRUDController crudController) {
        this.stateController = stateController;
        this.crudController = crudController;
    }

    public boolean createNewUser(String email, String password) {
        try {
            if (QueryState.userFromEmail(email) != null) {
                logger.log(Level.WARNING, "Usuário com o email {0} já existe.", email);
                return false;
            }

            CommomUser user = KeeperInterface.createCommomUser();
            user.setUuid(UUID.randomUUID());
            user.setSubscriptions(new ArrayList<>());
            user.setPassword(HasherInterface.hash(password));
            user.setEmail(email);

            crudController.userCRUD.createUser(user);
            logger.log(Level.INFO, "Novo usuário criado com sucesso: {0}", email);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao criar novo usuário: {0}", email);
            logger.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    public boolean createNewAdmin(String email, String password) {
        logger.log(Level.INFO, "email:{0}, user: {1}", new Object[]{email, UserCRUD.returnUser(QueryState.userFromEmail(email))});
        try {
            if (QueryState.userFromEmail(email) != null) {
                logger.log(Level.WARNING, "Administrador com o email {0} já existe.", email);
                return false;
            }

            AdminUser user = KeeperInterface.createAdminUser();
            user.setUuid(UUID.randomUUID());
            user.setSubscriptions(new ArrayList<>());
            user.setEvents(new ArrayList<>());
            user.setPassword(HasherInterface.hash(password));
            user.setEmail(email);

            crudController.userCRUD.createUser(user);
            logger.log(Level.INFO, "Novo administrador criado com sucesso: {0}", email);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao criar novo administrador: {0}, ERRO: {1}", new Object[]{email, e.getMessage()});
            return false;
        }
    }

    public boolean login(String email, String password) {
        logger.log(Level.INFO, "email:{0}, user: {1}", new Object[]{email, UserCRUD.returnUser(QueryState.userFromEmail(email))});
        try {
            User user = UserCRUD.returnUser(QueryState.userFromEmail(email));

            if (user != null && HasherInterface.hash(password).equals(user.getPassword())) {
                stateController.setCurrentUser(user);
                logger.log(Level.INFO, "Usuário logado com sucesso: {0}", email);
                return true;
            } else {
                logger.log(Level.WARNING, "Falha no login. Credenciais inválidas para o email: {0}", email);
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao tentar login com o email: {0}, ERRO: {1}", new Object[]{email, e.getMessage()});
            return false;
        }
    }

    public boolean logout() {
        try {
            if (stateController.getCurrentUser() != null) {
                stateController.setCurrentUser(null);
                logger.log(Level.INFO, "Logout realizado com sucesso.");
                return true;
            } else {
                logger.log(Level.WARNING, "Nenhum usuário está logado para fazer logout.");
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao tentar logout.", e);
            return false;
        }
    }
}
