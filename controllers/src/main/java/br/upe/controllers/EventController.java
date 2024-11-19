package br.upe.controllers;

import br.upe.entities.Event;
import br.upe.entities.SystemAdmin;
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

        if(!stateController.getCurrentUser().isSu()){
            throw new UserIsNotAdmin();
        }
        Event event = PersistenciaInterface.createEvent();
        event.setTitle(title);
        event.setDescription(description);
        event.setDirector(director);
        event.setStartDate(startDate);
        event.setEndDate(endDate);

        event.setAdmin((SystemAdmin) stateController.getCurrentUser());
        ((SystemAdmin) stateController.getCurrentUser()).getEvents().add(event);

        daoController.systemAdminDAO.update((SystemAdmin) stateController.getCurrentUser());
        daoController.eventDAO.save(event);

        stateController.setCurrentEvent(event);
    }
    public void updateEventDescription(String description) throws SystemException {
        if(!stateController.getCurrentUser().isSu()){
            throw new UserIsNotAdmin();
        }
        Event event = stateController.getCurrentEvent();
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().remove(event);
        event.setDescription(description);
        daoController.eventDAO.update(event);
        stateController.setCurrentEvent(event);
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().add(event);
    }
    public void updateEventDirector(String director) throws SystemException {
        if(!stateController.getCurrentUser().isSu()) {
            throw new UserIsNotAdmin();
        }
        Event event = stateController.getCurrentEvent();
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().remove(event);
        event.setDirector(director);
        daoController.eventDAO.update(event);
        stateController.setCurrentEvent(event);
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().add(event);
    }
    public void updateEventTitle(String title)throws SystemException {
        if(!stateController.getCurrentUser().isSu()) {
            throw new UserIsNotAdmin();
        }
        Event event = stateController.getCurrentEvent();
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().remove(event);
        event.setTitle(title);
        daoController.eventDAO.update(event);
        stateController.setCurrentEvent(event);
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().add(event);
    }
    public void updateEventStartDate(LocalDate date) throws SystemException {
        if(!stateController.getCurrentUser().isSu()) {
            throw new UserIsNotAdmin();
        }
        Event event = stateController.getCurrentEvent();
        event.setStartDate(date);
        checkDates(event.getStartDate(), event.getEndDate());
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().remove(stateController.getCurrentEvent());
        daoController.eventDAO.update(event);
        stateController.setCurrentEvent(event);
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().add(event);
    }
    public void updateEventEndDate(LocalDate date) throws SystemException {
        if(!stateController.getCurrentUser().isSu()) {
            throw new UserIsNotAdmin();
        }
        Event event = stateController.getCurrentEvent();
        event.setEndDate(date);
        checkDates(event.getStartDate(), event.getEndDate());
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().remove(stateController.getCurrentEvent());
        daoController.eventDAO.update(event);
        stateController.setCurrentEvent(event);
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().add(event);
    }
    public void deleteEvent(Event event) throws SystemException {
        if(!stateController.getCurrentUser().isSu()) {
            throw new UserIsNotAdmin();
        }
        daoController.eventDAO.delete(event);
        stateController.setCurrentEvent(null);
        ((SystemAdmin)stateController.getCurrentUser()).getEvents().remove(event);
    }
    public Collection<Event> getAllEventsByUser() throws SystemException {
        if(!(stateController.getCurrentUser() instanceof SystemAdmin user)) {
            throw new UserIsNotAdmin();
        }
        return user.getEvents();
    }
    public void changeCurrentEvent(Event event){
        stateController.setCurrentEvent(event);
    }
    public void closeCurrentEvent() {
        stateController.setCurrentEvent(null);
    }
    public List<Event> getAllEvents() {
        return daoController.eventDAO.findAll();
    }
}
