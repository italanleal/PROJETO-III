package br.upe.controllers;

import br.upe.entities.Event;
import br.upe.entities.SubEvent;
import br.upe.entities.SystemAdmin;
import br.upe.util.controllers.UserIsNotAdmin;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;

import java.time.LocalDate;
import static br.upe.util.controllers.CHECKING.checkDates;

public class SubEventController {
    private final StateController stateController;
    private final static String ERROR_MSG = "User isnt an admin";

    private final DAOController daoController;
    public SubEventController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }
    public void createSubEvent(String title, String description, LocalDate startDate, LocalDate endDate) throws SystemException {
        if(!stateController.getCurrentUser().isSu()){
            throw new UserIsNotAdmin(ERROR_MSG, null);
        }
        checkDates(startDate, endDate);
        SubEvent subEvent = PersistenciaInterface.createSubEvent();
        subEvent.setTitle(title);
        subEvent.setDescription(description);
        subEvent.setStartDate(startDate);
        subEvent.setEndDate(endDate);

        stateController.getCurrentSession().setSubEvent(subEvent);
        daoController.subEventDAO.save(subEvent);
        daoController.sessionDAO.update(stateController.getCurrentSession());
        stateController.setCurrentSubEvent(subEvent);
    }

    public void updateSubEventDescription(String description) throws SystemException {
        if(!stateController.getCurrentUser().isSu()){
            throw new UserIsNotAdmin(ERROR_MSG, null);
        }
        stateController.getCurrentSubEvent().setDescription(description);
        stateController.getCurrentSession().setSubEvent(stateController.getCurrentSubEvent());

        daoController.subEventDAO.update(stateController.getCurrentSubEvent());
    }
    public void updateSubEventTitle(String title) throws SystemException {
        if(!stateController.getCurrentUser().isSu()){
            throw new UserIsNotAdmin(ERROR_MSG, null);
        }
        stateController.getCurrentSubEvent().setDescription(title);
        stateController.getCurrentSession().setSubEvent(stateController.getCurrentSubEvent());

        daoController.subEventDAO.update(stateController.getCurrentSubEvent());
    }
    public void updateSubEventStartDate(LocalDate date) throws SystemException {
        if(!stateController.getCurrentUser().isSu()){
            throw new UserIsNotAdmin(ERROR_MSG, null);
        }
        stateController.getCurrentSubEvent().setStartDate(date);
        stateController.getCurrentSession().setSubEvent(stateController.getCurrentSubEvent());

        daoController.subEventDAO.update(stateController.getCurrentSubEvent());
    }
    public void updateSubEventEndDate(LocalDate date) throws SystemException {
        if(!stateController.getCurrentUser().isSu()){
            throw new UserIsNotAdmin(ERROR_MSG, null);
        }
        stateController.getCurrentSubEvent().setEndDate(date);
        stateController.getCurrentSession().setSubEvent(stateController.getCurrentSubEvent());

        daoController.subEventDAO.update(stateController.getCurrentSubEvent());
    }
}
