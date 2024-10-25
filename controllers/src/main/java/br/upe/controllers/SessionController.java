package br.upe.controllers;

import br.upe.pojos.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class SessionController {

    public SessionController(StateController stateController, CRUDController crudController) {
        this.stateController = stateController;
        this.crudController = crudController;
    }
    private final StateController stateController;
    private final CRUDController crudController;

    public boolean createNewSession(String descritor){
        if(stateController.getCurrentUser() instanceof AdminUser user){
            Session session = KeeperInterface.createSession();
            session.setUuid(UUID.randomUUID());
            session.setDescritor(descritor);
            session.setEventUuid(stateController.getCurrentEvent().getUuid());
            session.setSubscriptions(new ArrayList<>());

            GreatEvent eventHandler = KeeperInterface.createGreatEvent();
            eventHandler.setSessions(stateController.getCurrentEvent().getSessions());
            eventHandler.getSessions().add(session);

            stateController.setCurrentSession(session);
            crudController.sessionCRUD.createSession(session);
            crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), eventHandler);
            stateController.setCurrentEvent(crudController.eventCRUD.returnEvent(stateController.getCurrentEvent().getUuid()));
            Collection<GreatEvent> events = user.getEvents();
            for(GreatEvent event : events){
                if(event.getUuid().equals(stateController.getCurrentEvent().getUuid())){
                    events.remove(event);
                    events.add(stateController.getCurrentEvent());
                }
            }
            ((AdminUser) stateController.getCurrentUser()).setEvents(events);

            return true;
        }
        return false;
    }
    public void updateSessionDescritor(String descritor){
        Session source = KeeperInterface.createSession();
        source.setDescritor(descritor);
        crudController.sessionCRUD.updateSession(stateController.getCurrentSession().getUuid(), source);
        stateController.setCurrentSession(crudController.sessionCRUD.returnSession(stateController.getCurrentSession().getUuid()));
    }
    public boolean updateSessionStartDate(Date startDate){
        Session source = KeeperInterface.createSession();
        source.setStartDate(startDate);
        if(startDate != null){
            crudController.sessionCRUD.updateSession(stateController.getCurrentSession().getUuid(), source);
            stateController.setCurrentSession(crudController.sessionCRUD.returnSession(stateController.getCurrentSession().getUuid()));
            return true;
        } return false;
    }
    public boolean updateSessionEndDate(Date endDate){
        Session source = KeeperInterface.createSession();
        source.setEndDate(endDate);
        if(endDate != null){
            crudController.sessionCRUD.updateSession(stateController.getCurrentSession().getUuid(), source);
            stateController.setCurrentSession(crudController.sessionCRUD.returnSession(stateController.getCurrentSession().getUuid()));
            return true;
        } return false;
    }
    public void addSessionsSubscription(){
        Subscription subscription = KeeperInterface.createSubscription();
        subscription.setUuid(UUID.randomUUID());
        subscription.setSessionUuid(stateController.getCurrentSession().getUuid());
        subscription.setUserUuid(stateController.getCurrentUser().getUuid());
        subscription.setDate(new Date());

        stateController.getCurrentSession().getSubscriptions().add(subscription);
        stateController.getCurrentUser().getSubscriptions().add(subscription);

        User userHandler;
        if(stateController.getCurrentUser() instanceof AdminUser){
            userHandler = KeeperInterface.createAdminUser();
        } else {
            userHandler = KeeperInterface.createCommomUser();
        } userHandler.setSubscriptions(stateController.getCurrentUser().getSubscriptions());

        Session sessionHandler = KeeperInterface.createSession();
        sessionHandler.setSubscriptions(stateController.getCurrentSession().getSubscriptions());

        crudController.subscriptionCRUD.createSubscription(subscription);
        crudController.sessionCRUD.updateSession(stateController.getCurrentSession().getUuid(), sessionHandler);
        crudController.userCRUD.updateUser(stateController.getCurrentUser().getUuid(), userHandler);
    }
    public void changeCurrentSession(UUID sessionUuid){
        stateController.setCurrentSession(crudController.sessionCRUD.returnSession(sessionUuid));
    }
    public void closeCurrentSession(){
        stateController.setCurrentSession(null);
    }

    public Collection<Session> getAllEventSessions(UUID eventUuid) {
        Collection<Session> sessions = crudController.sessionCRUD.returnSession();
        Collection<Session> filtered = new ArrayList<>();

        for (Session session : sessions) {
            if (session != null && session.getEventUuid().toString().equals(eventUuid.toString())) {
                filtered.add(session);
            }
        }

        return filtered;
    }

}


