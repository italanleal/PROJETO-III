package br.upe.controllers;

import br.upe.operations.SessionCRUD;
import br.upe.operations.SubscriptionCRUD;
import br.upe.operations.UserCRUD;
import br.upe.pojos.*;
import java.util.UUID;

public class SubscriptionController{
    private final CRUDController crudController;

    public SubscriptionController(CRUDController crudController){
        this.crudController = crudController;
    }

    public void removeSubscription(UUID subscriptionUuid){
        Subscription newSubscription = SubscriptionCRUD.returnSubscription(subscriptionUuid);

        if (newSubscription == null){
            throw new IllegalArgumentException("Subscription não encontrada, possui UUID: " + subscriptionUuid);
        }

        User newUser = UserCRUD.returnUser(newSubscription.getUserUuid());
        if (newUser == null){
            throw new IllegalArgumentException("User não encontrado, possui UUID: " + newSubscription.getUserUuid());
        }

        Session newSession = SessionCRUD.returnSession(newSubscription.getSessionUuid());
        if (newSession == null){
            throw new IllegalArgumentException("Sessão não encontrada, possui UUID: " + newSubscription.getSessionUuid());
        }

        newUser.getSubscriptions().removeIf(subscription -> subscription.getUuid().equals(subscriptionUuid));
        newSession.getSubscriptions().removeIf(subscription -> subscription.getUuid().equals(subscriptionUuid));

        Session sessionHandler = KeeperInterface.createSession();
        User userHandler;
        if(newUser instanceof AdminUser){
            userHandler = KeeperInterface.createAdminUser();
        } else {
            userHandler = KeeperInterface.createCommomUser();
        }

        userHandler.setSubscriptions(newUser.getSubscriptions());
        sessionHandler.setSubscriptions(newSession.getSubscriptions());

        crudController.subscriptionCRUD.deleteSubscription(subscriptionUuid);

        crudController.userCRUD.updateUser(newUser.getUuid(), userHandler);
        crudController.sessionCRUD.updateSession(newSession.getUuid(), sessionHandler);
    }
}