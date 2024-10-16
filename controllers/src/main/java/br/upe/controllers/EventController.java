package br.upe.controllers;

import br.upe.operations.EventCRUD;
import br.upe.pojos.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventController {
    private static final Logger logger = Logger.getLogger(EventController.class.getName());

    private final StateController stateController;
    private final CRUDController crudController;

    public EventController(StateController stateController, CRUDController crudController) {
        this.stateController = stateController;
        this.crudController = crudController;
    }

    public boolean createNewEvent(String descritor, String director) {
        try {
            if (stateController.getCurrentUser() instanceof AdminUser user) {
                GreatEvent event = KeeperInterface.createGreatEvent();
                event.setUuid(UUID.randomUUID());
                event.setDescritor(descritor);
                event.setDirector(director);
                event.setSubmissions(new ArrayList<>());
                event.setSessions(new ArrayList<>());

                user.addEvent(event);
                AdminUser userHandler = KeeperInterface.createAdminUser();
                userHandler.setEvents(user.getEvents());

                stateController.setCurrentEvent(event);
                crudController.eventCRUD.createEvent(event);
                crudController.userCRUD.updateUser(user.getUuid(), userHandler);

                logger.log(Level.INFO, "Novo evento criado: {0}, Diretor: {1}", new Object[]{descritor, director});
                return true;
            }
            logger.log(Level.WARNING, "Usuário atual não é um administrador. Falha ao criar evento.");
            return false;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao criar novo evento: {0}, erro: {1}", new Object[]{descritor, e.getMessage()});
            return false;
        }
    }

    public void updateEventDescritor(String descritor) {
        try {
            GreatEvent source = KeeperInterface.createGreatEvent();
            source.setDescritor(descritor);
            crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), source);
            logger.log(Level.INFO, "Descritor do evento atualizado para: {0}", descritor);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar descritor do evento", e);
        }
    }

    public void updateEventDirector(String director) {
        try {
            GreatEvent source = KeeperInterface.createGreatEvent();
            source.setDirector(director);
            crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), source);
            logger.log(Level.INFO, "Diretor do evento atualizado para: {0}", director);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar diretor do evento", e);
        }
    }

    public void updateEventStartDate(Date startDate) {
        try {
            GreatEvent source = KeeperInterface.createGreatEvent();
            source.setStartDate(startDate);
            crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), source);
            logger.log(Level.INFO, "Data de início do evento atualizada para: {0}", startDate);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar data de início do evento", e);
        }
    }

    public void updateEventEndDate(Date endDate) {
        try {
            GreatEvent source = KeeperInterface.createGreatEvent();
            source.setEndDate(endDate);
            crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), source);
            logger.log(Level.INFO, "Data de término do evento atualizada para: {0}", endDate);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar data de término do evento", e);
        }
    }

    public void addEventSubmission(String descritor) {
        try {
            Submission submission = KeeperInterface.createSubmission();
            submission.setUuid(UUID.randomUUID());
            submission.setUserUuid(stateController.getCurrentUser().getUuid());
            submission.setEventUuid(stateController.getCurrentEvent().getUuid());
            submission.setDescritor(descritor);
            submission.setDate(new Date());

            stateController.getCurrentEvent().addSubmission(submission);

            GreatEvent eventHandler = KeeperInterface.createGreatEvent();
            eventHandler.setSubmissions(stateController.getCurrentEvent().getSubmissions());

            crudController.submissionCRUD.createSubmission(submission);
            crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), eventHandler);

            logger.log(Level.INFO, "Nova submissão adicionada ao evento: {0}", descritor);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao adicionar submissão ao evento", e);
        }
    }

    public void changeCurrentEvent(UUID eventUuid) {
        try {
            stateController.setCurrentEvent(EventCRUD.returnEvent(eventUuid));
            logger.log(Level.INFO, "Evento atual alterado para: {0}", eventUuid);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao alterar evento atual", e);
        }
    }

    public void closeCurrentEvent() {
        try {
            stateController.setCurrentEvent(null);
            logger.log(Level.INFO, "Evento atual fechado.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao fechar evento atual", e);
        }
    }

    public Collection<GreatEvent> getAllEvents() {
        try {
            return EventCRUD.returnEvent();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao obter todos os eventos", e);
            return new ArrayList<>();
        }
    }

    public Collection<GreatEvent> getAllEventsByUser() {
        try {
            if (stateController.getCurrentUser() instanceof AdminUser user) {
                return user.getEvents();
            }
            return getAllEvents();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao obter eventos do usuário", e);
            return new ArrayList<>();
        }
    }
}