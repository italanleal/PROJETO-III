package br.upe.controllers;

import br.upe.entities.*;
import br.upe.util.controllers.CHECKING;
import br.upe.util.controllers.InvalidDateInput;
import br.upe.util.controllers.UserIsNotAdmin;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


public class SessionController {
    private final StateController stateController;
    private final DAOController daoController;
    public SessionController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

    public void createNewSession(String title, String description, String guest, String local, LocalDate startDate, LocalDate endDate) throws SystemException {
        if((!stateController.getCurrentUser().isSu())) throw new UserIsNotAdmin();
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

        SubEvent subEvent = stateController.getCurrentSubEvent();
        Event event = stateController.getCurrentEvent();
        if(subEvent != null){
            session.setEvent(subEvent);
        } else if(event != null){
            session.setEvent(event);
        }
        stateController.setCurrentSession(daoController.sessionDAO.save(session));
        stateController.refresh();

    }

    public void updateSessionDescription(String description) {
        if(!(stateController.getCurrentUser().isSu())) return;
        Session session = stateController.getCurrentSession();
        session.setDescription(description);
        stateController.setCurrentSession(daoController.sessionDAO.update(session));
        stateController.refresh();

    }

    public void updateSessionStartDate(LocalDate startDate) throws SystemException {

        if(!(stateController.getCurrentUser() instanceof SystemAdmin)) throw new UserIsNotAdmin();
        try {
            CHECKING.checkDates(startDate, stateController.getCurrentSession().getEndDate());
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
        stateController.refresh();
    }

    public void updateSessionEndDate(LocalDate endDate) throws SystemException {
        if(!(stateController.getCurrentUser() instanceof SystemAdmin)) return;
        try {
            CHECKING.checkDates(stateController.getCurrentSession().getStartDate(), endDate);
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
        stateController.refresh();
    }
    public void updateSessionTitle(String title){
        if(!(stateController.getCurrentUser().isSu())) return;
        Session session = stateController.getCurrentSession();
        session.setTitle(title);
        stateController.setCurrentSession(daoController.sessionDAO.update(session));
        stateController.refresh();
    }
    public void updateSessionGuest(String guest){
        if(!(stateController.getCurrentUser().isSu())) return;
        Session session = stateController.getCurrentSession();
        session.setTitle(guest);
        stateController.setCurrentSession(daoController.sessionDAO.update(session));
        stateController.refresh();
    }
    public void updateSessionLocal(String local){
        if(!(stateController.getCurrentUser().isSu())) return;
        Session session = stateController.getCurrentSession();
        session.setTitle(local);
        stateController.setCurrentSession(daoController.sessionDAO.update(session));
        stateController.refresh();

    }
    public void addSubscriptionToSession(){
        Session session = stateController.getCurrentSession();

        Subscription subscription = PersistenciaInterface.createSubscription();
        subscription.setSession(session);
        subscription.setUser((SystemUser) stateController.getCurrentUser());
        subscription.setDate(LocalDate.now());

        daoController.subscriptionDAO.save(subscription);
        stateController.setCurrentSubscription(subscription);
        stateController.refresh();
    }
    public boolean userIsSubscribed(Session session){
        List<Subscription> subs = session.getSubscriptions();
        for(Subscription sub : subs){
            if(sub.getUser().getId().equals(stateController.getCurrentUser().getId())) return true;
        }
        return false;
    }
    public void changeCurrentSession(Session session){
        stateController.setCurrentSession(session);
    }

    public void closeCurrentSession(){
        stateController.setCurrentSubscription(null);
    }

    public Collection<Session> getAllEventSessions() {
        if(stateController.getCurrentSubEvent() instanceof SubEvent subEvent) return subEvent.getSessions();
        return stateController.getCurrentEvent().getSessions();
    }

    public void deleteSession(Session session) {
        if(stateController.getCurrentSubEvent() instanceof SubEvent subEvent){
            subEvent.getSessions().remove(session);
        } else {
            stateController.getCurrentEvent().getSessions().remove(session);
        }
        daoController.sessionDAO.delete(session);
        stateController.setCurrentSession(null);
    }
}
