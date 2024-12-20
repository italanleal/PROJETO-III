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

        stateController.setCurrentEvent(daoController.eventDAO.save(event));
        stateController.refresh();
    }

    public void updateEventDescription(String description) throws SystemException {
        if(!stateController.getCurrentUser().isSu()){
            throw new UserIsNotAdmin();
        }

        Event event = stateController.getCurrentEvent();
        event.setDescription(description);
        stateController.setCurrentEvent(daoController.eventDAO.update(event));
        stateController.refresh();
    }

    public void updateEventDirector(String director) throws SystemException {
        if(!stateController.getCurrentUser().isSu()) {
            throw new UserIsNotAdmin();
        }

        Event event = stateController.getCurrentEvent();
        event.setDirector(director);
        stateController.setCurrentEvent(daoController.eventDAO.update(event));
        stateController.refresh();
    }
    public void updateEventTitle(String title)throws SystemException {
        if(!stateController.getCurrentUser().isSu()) {
            throw new UserIsNotAdmin();
        }

        Event event = stateController.getCurrentEvent();
        event.setTitle(title);
        stateController.setCurrentEvent(daoController.eventDAO.update(event));
        stateController.refresh();
    }

    public void updateEventStartDate(LocalDate startDate) throws SystemException {
        if (!stateController.getCurrentUser().isSu()) {
            throw new UserIsNotAdmin();
        }
        Event event = stateController.getCurrentEvent();
        try {
            CHECKING.checkDates(startDate, event.getEndDate());
        } catch (SystemException e) {
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }

        event.setStartDate(startDate);

        stateController.setCurrentEvent(daoController.eventDAO.update(event));
        stateController.refresh();
    }

    public void updateEventEndDate(LocalDate endDate) throws SystemException {
        if(!stateController.getCurrentUser().isSu()) {
            throw new UserIsNotAdmin();
        }
        Event event = stateController.getCurrentEvent();
        try{
            CHECKING.checkDates(event.getStartDate(), endDate);
        } catch (SystemException e){
            throw new InvalidDateInput(e.getMessage(), e.getCause());
        }

        event.setEndDate(endDate);
        stateController.setCurrentEvent(daoController.eventDAO.update(event));
        stateController.refresh();

    }
    public void deleteEvent(Event event) throws SystemException {
        if(!stateController.getCurrentUser().isSu()) {
            throw new UserIsNotAdmin();
        }
        daoController.eventDAO.deleteById(event.getId());
        stateController.setCurrentEvent(null);
        stateController.refresh();
    }
    public Collection<Event> getAllEventsByUser() throws SystemException {
        Collection<Event> events = getAllEvents();

        if(stateController.getCurrentUser() instanceof SystemAdmin admin) {
            return admin.getEvents();
        } else throw new UserIsNotAdmin();

    }
    public Collection<SubEvent> getAllSubEvents() throws SystemException {
        if (!(stateController.getCurrentUser() instanceof SystemAdmin user)) {
            throw new UserIsNotAdmin();
        }
        return stateController.getCurrentEvent().getSubEvents();
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
