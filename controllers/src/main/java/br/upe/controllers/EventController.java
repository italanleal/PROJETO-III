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

public class EventController {
    private final StateController stateController;
    private final DAOController daoController;

    public EventController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

    public void createNewEvent(String title, String description, String director, LocalDate startDate, LocalDate endDate) throws SystemException {
        checkDates(startDate, endDate);

        if(!stateController.currentUser.isSu()){
            throw new UserIsNotAdmin();
        }
        Event event = PersistenciaInterface.createEvent();
        event.setTitle(title);
        event.setDescription(description);
        event.setDirector(director);
        event.setStartDate(startDate);
        event.setEndDate(endDate);

        daoController.eventDAO.save(event);

        event.setAdmin((SystemAdmin) stateController.currentUser);
        ((SystemAdmin) stateController.currentUser).getEvents().add(event);

        daoController.systemAdminDAO.update((SystemAdmin) stateController.currentUser);
        stateController.currentEvent = event;
    }

    public void updateEventDescription(String description) throws SystemException {
        if(!stateController.currentUser.isSu()){
            throw new UserIsNotAdmin();
        }
        Event event = stateController.currentEvent;
        ((SystemAdmin)stateController.currentUser).getEvents().remove(event);
        event.setDescription(description);
        daoController.eventDAO.update(event);
        stateController.currentEvent = event;
        ((SystemAdmin)stateController.currentUser).getEvents().add(event);
    }
    public void updateEventDirector(String director) throws SystemException {
        if(!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        Event event = stateController.currentEvent;
        ((SystemAdmin)stateController.currentUser).getEvents().remove(event);
        event.setDirector(director);
        daoController.eventDAO.update(event);
        stateController.currentEvent = event;
        ((SystemAdmin)stateController.currentUser).getEvents().add(event);
    }
    public void updateEventTitle(String title)throws SystemException {
        if(!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        Event event = stateController.currentEvent;
        ((SystemAdmin)stateController.currentUser).getEvents().remove(event);
        event.setTitle(title);
        daoController.eventDAO.update(event);
        stateController.currentEvent = event;
        ((SystemAdmin)stateController.currentUser).getEvents().add(event);
    }

    public void updateEventStartDate(LocalDate startDate) throws SystemException {
        if (!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        Event event = stateController.currentEvent;
        try {
            CHECKING.checkDates(startDate, event.getStartDate());
        } catch (SystemException e) {
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }
        event.setStartDate(startDate);
        ((SystemAdmin) stateController.currentUser).getEvents().remove(stateController.currentEvent);
        daoController.eventDAO.update(event);
        stateController.currentEvent = event;
        ((SystemAdmin) stateController.currentUser).getEvents().add(event);
    }

    public void updateEventEndDate(LocalDate endDate) throws SystemException {
        if(!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        Event event = stateController.currentEvent;
        try{
            CHECKING.checkDates(event.getStartDate(), endDate);
        } catch (SystemException e){
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }
        event.setEndDate(endDate);
        ((SystemAdmin)stateController.currentUser).getEvents().remove(stateController.currentEvent);
        daoController.eventDAO.update(event);
        stateController.currentEvent = event;
        ((SystemAdmin)stateController.currentUser).getEvents().add(event);
    }
    public void deleteEvent(Event event) throws SystemException {
        if(!stateController.currentUser.isSu()) {
            throw new UserIsNotAdmin();
        }
        daoController.eventDAO.delete(event);
        stateController.currentEvent = null;
        ((SystemAdmin)stateController.currentUser).getEvents().remove(event);
    }
    public Collection<Event> getAllEventsByUser() throws SystemException {
        if(!(stateController.currentUser instanceof SystemAdmin user)) {
            throw new UserIsNotAdmin();
        }
        return user.getEvents();
    }
    public Collection<SubEvent> getAllSubEvents() throws SystemException {
        if (!(stateController.currentUser instanceof SystemAdmin)) {
            throw new UserIsNotAdmin();
        }
        return stateController.currentEvent.getSubEvents();
    }
    public void changeCurrentEvent(Event event){
        stateController.currentEvent = event;
    }
    public void closeCurrentEvent() {

        stateController.currentEvent = null;
    }
    public List<Event> getAllEvents() {
        return daoController.eventDAO.findAll();
    }
}
