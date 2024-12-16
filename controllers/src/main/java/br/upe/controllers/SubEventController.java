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
        stateController.currentSubEvent = PersistenciaInterface.createSubEvent();
        stateController.currentSubEvent.setTitle(title);
        stateController.currentSubEvent.setDescription(description);
        stateController.currentSubEvent.setDirector(director);
        stateController.currentSubEvent.setStartDate(startDate);
        stateController.currentSubEvent.setEndDate(endDate);

        daoController.subEventDAO.save(stateController.currentSubEvent);

        stateController.currentEvent.setAdmin(null);
        stateController.currentSubEvent.setEvent(stateController.currentEvent);
        stateController.currentEvent.getSubEvents().add(stateController.currentSubEvent);

        daoController.eventDAO.update(stateController.currentEvent);

        ((SystemAdmin)stateController.currentUser).getEvents().removeIf(e -> e.getId().equals(stateController.currentEvent.getId()));
        stateController.currentEvent.setAdmin((SystemAdmin) stateController.currentUser);
        ((SystemAdmin)stateController.currentUser).getEvents().add(stateController.currentEvent);
        daoController.systemAdminDAO.update((SystemAdmin) stateController.currentUser);
    }

    public void updateSubEventDescription(String description) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        SubEvent subEvent = stateController.currentSubEvent;
        stateController.currentEvent.getSubEvents().remove(subEvent);
        subEvent.setDescription(description);
        daoController.subEventDAO.update(subEvent);
        stateController.currentSubEvent=subEvent;
        stateController.currentEvent.getSubEvents().add(subEvent);
    }

    public void updateSubEventDirector(String director) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        SubEvent subEvent = stateController.currentSubEvent;
        stateController.currentEvent.getSubEvents().remove(subEvent);
        subEvent.setDirector(director);
        daoController.subEventDAO.update(subEvent);
        stateController.currentSubEvent=subEvent;
        stateController.currentEvent.getSubEvents().add(subEvent);
    }

    public void updateSubEventTitle(String title) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        SubEvent subEvent = stateController.currentSubEvent;
        stateController.currentEvent.getSubEvents().remove(subEvent);
        subEvent.setTitle(title);
        daoController.subEventDAO.update(subEvent);
        stateController.currentSubEvent=subEvent;
        stateController.currentEvent.getSubEvents().add(subEvent);
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
        stateController.currentEvent.getSubEvents().remove(stateController.currentSubEvent);
        daoController.subEventDAO.update(subEvent);
        stateController.currentSubEvent=subEvent;
        stateController.currentEvent.getSubEvents().add(subEvent);
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
        stateController.currentEvent.getSubEvents().remove(stateController.currentSubEvent);
        daoController.subEventDAO.update(subEvent);
        stateController.currentSubEvent=subEvent;
        stateController.currentEvent.getSubEvents().add(subEvent);
    }

    public void deleteSubEvent(SubEvent subEvent) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        daoController.subEventDAO.delete(subEvent);
        stateController.currentSubEvent=null;
        stateController.currentEvent.getSubEvents().remove(subEvent);
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