package br.upe.controllers;

import br.upe.entities.*;
import br.upe.util.controllers.CHECKING;
import br.upe.util.controllers.InvalidDateInput;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;

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
        if(!(stateController.currentUser instanceof SystemAdmin)) return;
        Session currentSession = PersistenciaInterface.createSession();
        currentSession.setTitle(title);
        currentSession.setDescription(description);
        currentSession.setGuest(guest);
        currentSession.setLocal(local);

        daoController.sessionDAO.save(currentSession);

        if(stateController.currentSubEvent != null){
            stateController.currentSubEvent.getSessions().add(currentSession);
        } else {
            stateController.currentEvent.getSessions().add(currentSession);
        }

        stateController.currentSession = currentSession;
    }

    public void updateSessionDescription(String description) {
        if(!(stateController.currentUser instanceof SystemAdmin)) return;
        Session currentSession = stateController.currentSession;
        stateController.currentEvent.getSessions().remove(currentSession);
        currentSession.setDescription(description);
        daoController.sessionDAO.update(currentSession);
        stateController.currentSession =currentSession;
        stateController.currentEvent.getSessions().add(currentSession);
    }
    public void updateSessionStartDate(LocalDate startDate) throws SystemException {
        if(!(stateController.currentUser instanceof SystemAdmin)) return;
        Session currentSession = stateController.currentSession;
        try{
            CHECKING.checkDates(startDate, currentSession.getEndDate());
        } catch (SystemException e){
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }
        stateController.currentEvent.getSessions().remove(currentSession);
        currentSession.setStartDate(startDate);
        daoController.sessionDAO.update(currentSession);
        stateController.currentSession=currentSession;
        stateController.currentEvent.getSessions().add(currentSession);
    }
    public void updateSessionEndDate(LocalDate endDate) throws InvalidDateInput {
        if(!(stateController.currentUser instanceof SystemAdmin)) return;
        Session currentSession = stateController.currentSession;
        try{
            CHECKING.checkDates(currentSession.getStartDate(), endDate);
        } catch (SystemException e){
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }
        stateController.currentEvent.getSessions().remove(currentSession);
        currentSession.setEndDate(endDate);
        daoController.sessionDAO.update(currentSession);
        stateController.currentSession=currentSession;
        stateController.currentEvent.getSessions().add(currentSession);
    }
    public void addSubscriptionToSession(){
        Session currentSession = stateController.currentSession;
        stateController.currentEvent.getSessions().remove(currentSession);

        Subscription subscription = PersistenciaInterface.createSubscription();
        subscription.setSession(currentSession);
        subscription.setUser((SystemUser) stateController.currentUser);
        subscription.setDate(LocalDate.now());

        currentSession.getSubscriptions().add(subscription);
        daoController.sessionDAO.update(currentSession);
        stateController.currentSession=currentSession;
        stateController.currentEvent.getSessions().add(currentSession);
        daoController.subscriptionDAO.save(subscription);

    }
    public void changeCurrentSession(Session session){
        stateController.currentSession =session;
    }

    public void closeCurrentSession(){
        stateController.currentSession=null;
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
