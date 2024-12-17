package br.upe.controllers;

import br.upe.entities.*;
import br.upe.util.controllers.CHECKING;
import br.upe.util.controllers.InvalidDateInput;
import br.upe.util.controllers.UserIsNotAdmin;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;

import java.time.LocalDate;
import java.util.Collection;

public class SessionController {
    private final StateController stateController;
    private final DAOController daoController;
    public SessionController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

    public void createNewSession(String title, String description, String guest, String local, LocalDate startDate, LocalDate endDate) throws SystemException {
        if(!(stateController.currentUser instanceof SystemAdmin)) throw new UserIsNotAdmin();
        try {
            CHECKING.checkDates(startDate, endDate);
        } catch (InvalidDateInput e) {
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }

        Session session = PersistenciaInterface.createSession();
        session.setTitle(title);
        session.setDescription(description);
        session.setGuest(guest);
        session.setLocal(local);
        session.setStartDate(startDate);
        session.setEndDate(endDate);

        if(stateController.getCurrentSubEvent() instanceof SubEvent subEvent){
            session.setEvent(subEvent);
        } else {
            session.setEvent(stateController.getCurrentEvent());
        }

        daoController.sessionDAO.save(session);
        stateController.setCurrentSession(session);
    }

    public void updateSessionDescription(String description) {
        if(!(stateController.currentUser.isSu())) return;
        Session session = stateController.getCurrentSession();
        session.setDescription(description);
        stateController.setCurrentSession(daoController.sessionDAO.update(session));

    }

    public void updateSessionStartDate(LocalDate startDate) throws SystemException {
        if(!(stateController.currentUser instanceof SystemAdmin)) throw new UserIsNotAdmin();
        try {
            CHECKING.checkDates(startDate, stateController.currentSession.getEndDate());
        } catch (InvalidDateInput e){
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }

        Session session = stateController.getCurrentSession();

        try{
            CHECKING.checkDates(startDate, session.getEndDate());
        } catch (SystemException e){
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }

        session.setStartDate(startDate);
        stateController.setCurrentSession(daoController.sessionDAO.update(session));
    }
    public void updateSessionEndDate(LocalDate endDate) throws SystemException {
        if(!(stateController.currentUser instanceof SystemAdmin)) return;
        try {
            CHECKING.checkDates(stateController.currentSession.getStartDate(), endDate);
        } catch (InvalidDateInput e){
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }
        Session session = stateController.getCurrentSession();

        try{
            CHECKING.checkDates(session.getStartDate(), endDate);
        } catch (SystemException e){
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }
        session.setEndDate(endDate);

        stateController.setCurrentSession(daoController.sessionDAO.update(session));
    }
    public void addSubscriptionToSession(){
        Session session = stateController.getCurrentSession();

        Subscription subscription = PersistenciaInterface.createSubscription();
        subscription.setSession(session);
        subscription.setUser((SystemUser) stateController.getCurrentUser());
        subscription.setDate(LocalDate.now());

        daoController.subscriptionDAO.save(subscription);
        stateController.setCurrentSubscription(subscription);
    }
    public void changeCurrentSession(Session session){
        stateController.setCurrentSession(session);
    }

    public void closeCurrentSession(){
        stateController.setCurrentSubscription(null);
    }

    public Collection<Session> getAllEventSessions(BaseEvent event) {
        return event.getSessions();
    }
}
