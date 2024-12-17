package br.upe.controllers;

import br.upe.entities.Event;
import br.upe.entities.SubEvent;
import br.upe.entities.SystemAdmin;
import br.upe.util.controllers.CHECKING;
import br.upe.util.controllers.InvalidDateInput;
import br.upe.util.controllers.UserIsNotAdmin;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static br.upe.util.controllers.CHECKING.checkDates;

public class SubEventController {
    private final StateController stateController;

    private final DAOController daoController;

    public SubEventController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

    public void createNewSubEvent(String title, String description, String director, LocalDate startDate, LocalDate endDate) throws SystemException {
        checkDates(startDate, endDate);

        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        SubEvent subEvent = PersistenciaInterface.createSubEvent();
        subEvent.setTitle(title);
        subEvent.setDescription(description);
        subEvent.setDirector(director);
        subEvent.setStartDate(startDate);
        subEvent.setEndDate(endDate);
        subEvent.setEvent(stateController.getCurrentEvent());

        daoController.subEventDAO.save(subEvent);
        stateController.setCurrentSubEvent(subEvent);

    }

    public void updateSubEventDescription(String description) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        SubEvent subEvent = stateController.getCurrentSubEvent();
        subEvent.setDescription(description);
        daoController.subEventDAO.update(subEvent);
        stateController.setCurrentSubEvent(subEvent);

    }

    public void updateSubEventDirector(String director) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        SubEvent subEvent = stateController.getCurrentSubEvent();
        subEvent.setDirector(director);
        daoController.subEventDAO.update(subEvent);
        stateController.setCurrentSubEvent(subEvent);
    }

    public void updateSubEventTitle(String title) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }

        SubEvent subEvent = stateController.getCurrentSubEvent();
        subEvent.setTitle(title);
        daoController.subEventDAO.update(subEvent);
        stateController.setCurrentSubEvent(subEvent);
    }

    public void updateSubEventStartDate(LocalDate startDate) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        SubEvent subEvent = stateController.currentSubEvent;
        try {
            CHECKING.checkDates(startDate, subEvent.getEndDate());
        } catch (SystemException e) {
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }
        subEvent.setStartDate(startDate);
        daoController.subEventDAO.update(subEvent);
        stateController.setCurrentSubEvent(subEvent);

    }

    public void updateSubEventEndDate(LocalDate endDate) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        SubEvent subEvent = stateController.currentSubEvent;
        try {
            CHECKING.checkDates(subEvent.getStartDate(), endDate);
        } catch (SystemException e) {
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }
        subEvent.setEndDate(endDate);
        daoController.subEventDAO.update(subEvent);
        stateController.setCurrentSubEvent(subEvent);

    }

    public void deleteSubEvent(SubEvent subEvent) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        daoController.subEventDAO.delete(subEvent);
        stateController.setCurrentSubEvent(null);

    }

    public void changeCurrentSubEvent(SubEvent subEvent) {
        stateController.currentSubEvent=subEvent;
    }

    public void closeCurrentSubEvent() {
        stateController.currentSubEvent=null;
    }

    public List<SubEvent> getAllSubEvents() {
        return daoController.subEventDAO.findAll();
    }
}