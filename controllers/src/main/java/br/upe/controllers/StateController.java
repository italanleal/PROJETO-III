package br.upe.controllers;

import br.upe.entities.*;

public class StateController {
    private Userd currentUser;
    private Event currentEvent;
    private Session currentSession;
    private Submission currentSubmission;
    private Certification currentCertification;
    private Subscription currentSubscription;
    private SubEvent currentSubEvent;

    private DAOController daoController;

    public Userd getCurrentUser() {
        return currentUser;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public Submission getCurrentSubmission() {
        return currentSubmission;
    }

    public Certification getCurrentCertification() {
        return currentCertification;
    }

    public Subscription getCurrentSubscription() {
        return currentSubscription;
    }

    public SubEvent getCurrentSubEvent() {
        return currentSubEvent;
    }

    public StateController(DAOController daoController) {
        this.daoController = daoController;
    }
    private StateController() {
    }

    public void refreshCurrentUser() {
        if(currentUser instanceof SystemAdmin systemAdmin) {
            daoController.systemAdminDAO.findById(systemAdmin.getId())
                    .ifPresent(this::setCurrentUser);
        }
        if(currentUser instanceof SystemUser systemUser) {
            daoController.systemUserDAO.findById(systemUser.getId())
                    .ifPresent(this::setCurrentUser);
        }
    }

    public void setCurrentUser(Userd user) {
        if(user instanceof SystemAdmin systemAdmin) {
            daoController.systemAdminDAO.detach(systemAdmin);
        }
        if(user instanceof SystemUser systemUser){
            daoController.systemUserDAO.detach(systemUser);
        }
        currentUser = user;
    }

    public void refreshCurrentEvent() {
        if (currentEvent != null) {
            daoController.eventDAO.findById(currentEvent.getId())
                    .ifPresent(this::setCurrentEvent);
        }
    }

    public void setCurrentEvent(Event event) {
        if(event != null) {
            daoController.eventDAO.detach(event);
            currentEvent = event;
        } else {
            currentEvent = null;
        }
    }

    public void refreshCurrentSession() {
        if (currentSession != null) {
            daoController.sessionDAO.findById(currentSession.getId())
                    .ifPresent(this::setCurrentSession);
        }
    }

    public void setCurrentSession(Session session) {
        if(session != null) {
            daoController.sessionDAO.detach(session);
            currentSession = session;
        } else {
            currentSession = null;
        }
    }

    public void refreshCurrentSubmission() {
        if (currentSubmission != null) {
            daoController.submissionDAO.findById(currentSubmission.getId())
                    .ifPresent(this::setCurrentSubmission);
        }
    }

    public void setCurrentSubmission(Submission submission) {
        if(submission != null) {
            daoController.submissionDAO.detach(submission);
            currentSubmission = submission;
        } else {
            currentSubmission = null;
        }
    }

    public void refreshCurrentCertification() {
        if (currentCertification != null) {
            daoController.certificationDAO.findById(currentCertification.getId())
                    .ifPresent(this::setCurrentCertification);
        }
    }

    public void setCurrentCertification(Certification certification) {
        if(certification != null) {
            daoController.certificationDAO.detach(certification);
            currentCertification = certification;
        } else {
            currentCertification = null;
        }
    }

    public void refreshCurrentSubscription() {
        if (currentSubscription != null) {
            daoController.subscriptionDAO.findById(currentSubscription.getId())
                    .ifPresent(this::setCurrentSubscription);
        }
    }

    public void setCurrentSubscription(Subscription subscription) {
        if(subscription != null) {
            daoController.subscriptionDAO.detach(subscription);
            currentSubscription = subscription;
        } else {
            currentSubscription = null;
        }
    }

    public void refreshCurrentSubEvent() {
        if (currentSubEvent != null) {
            daoController.subEventDAO.findById(currentSubEvent.getId())
                    .ifPresent(subEvent -> {
                        setCurrentSubEvent(subEvent);
                        daoController.subEventDAO.detach(subEvent);
                    });
        }
    }

    public void setCurrentSubEvent(SubEvent subEvent) {
        if(subEvent != null) {
            daoController.subEventDAO.detach(subEvent);
            currentSubEvent = subEvent;
        } else {
            currentSubEvent = null;
        }
    }

    public void close() {
        setCurrentUser(null);
        setCurrentEvent(null);
        setCurrentSession(null);
        setCurrentSubmission(null);
        setCurrentCertification(null);
        setCurrentSubEvent(null);
        setCurrentSubscription(null);
    }


    public void refresh() {
        if(currentUser != null) refreshCurrentUser();
        if(currentCertification != null) refreshCurrentCertification();
        if(currentSubscription != null) refreshCurrentSubscription();
        if(currentSubmission != null) refreshCurrentSubmission();
        if(currentEvent != null) refreshCurrentEvent();
        if(currentSubEvent != null) refreshCurrentSubEvent();
        if(currentSession != null) refreshCurrentSession();
    }


}
