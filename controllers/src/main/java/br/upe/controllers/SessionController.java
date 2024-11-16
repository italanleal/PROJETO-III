package br.upe.controllers;

import br.upe.entities.*;
import br.upe.util.persistencia.PersistenciaInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class SessionController {
    private final StateController stateController;
    private final DAOController daoController;
    public SessionController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

    public void createNewSession(String title, String description, String guest, String local) {
        if(!(stateController.getCurrentUser() instanceof SystemAdmin)) return;
        Session currentSession = PersistenciaInterface.createSession();
        currentSession.setTitle(title);
        currentSession.setDescription(description);
        currentSession.setGuest(guest);
        currentSession.setLocal(local);

        daoController.sessionDAO.save(currentSession);

        stateController.setCurrentSession(currentSession);
        stateController.getCurrentEvent().getSessions().add(currentSession);
    }
    public void updateSessionDescription(String description) {
        if(!(stateController.getCurrentUser() instanceof SystemAdmin)) return;
        Session currentSession = stateController.getCurrentSession();
        stateController.getCurrentEvent().getSessions().remove(currentSession);
        currentSession.setDescription(description);
        daoController.sessionDAO.update(currentSession);
        stateController.setCurrentSession(currentSession);
        stateController.getCurrentEvent().getSessions().add(currentSession);
    }
    public void updateSessionStartDate(LocalDate starDate) {
        if(!(stateController.getCurrentUser() instanceof SystemAdmin)) return;
        Session currentSession = stateController.getCurrentSession();
        stateController.getCurrentEvent().getSessions().remove(currentSession);
        currentSession.setStartDate(starDate);
        daoController.sessionDAO.update(currentSession);
        stateController.setCurrentSession(currentSession);
        stateController.getCurrentEvent().getSessions().add(currentSession);
    }
    public void updateSessionEndDate(LocalDate endDate) {
        if(!(stateController.getCurrentUser() instanceof SystemAdmin)) return;
        Session currentSession = stateController.getCurrentSession();
        stateController.getCurrentEvent().getSessions().remove(currentSession);
        currentSession.setEndDate(endDate);
        daoController.sessionDAO.update(currentSession);
        stateController.setCurrentSession(currentSession);
        stateController.getCurrentEvent().getSessions().add(currentSession);
    }
    public void addSubscriptionToSession(){
        Session currentSession = stateController.getCurrentSession();
        stateController.getCurrentEvent().getSessions().remove(currentSession);

        Subscription subscription = PersistenciaInterface.createSubscription();
        subscription.setSession(currentSession);
        subscription.setUser((SystemUser) stateController.currentUser);
        subscription.setDate(LocalDate.now());

        currentSession.getSubscriptions().add(subscription);
        daoController.sessionDAO.update(currentSession);
        stateController.setCurrentSession(currentSession);
        stateController.getCurrentEvent().getSessions().add(currentSession);
        daoController.subscriptionDAO.save(subscription);
    }
    public void changeCurrentSession(Session session){
        stateController.setCurrentSession(session);
    }

    public void closeCurrentSession(){
        stateController.setCurrentSession(null);
    }

    public Collection<Session> getAllEventSessions(Event event) {
        Collection<Session> sessions = daoController.sessionDAO.findAll();
        Collection<Session> filtered = new ArrayList<>();

        for (Session session : sessions) {
            if (session != null && session.getEvent().getId().equals(event.getId())) {
                filtered.add(session);
            }
        }

        return filtered;
    }
}
