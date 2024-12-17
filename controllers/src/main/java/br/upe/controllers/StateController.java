package br.upe.controllers;

import br.upe.entities.*;

public class StateController {
    public Userd currentUser;
    public Event currentEvent;
    public Session currentSession;
    public Submission currentSubmission;
    public Certification currentCertification;
    public Subscription currentSubscription;
    public SubEvent currentSubEvent;


    private DAOController daoController;

    public StateController(DAOController daoController) {
        this.daoController = daoController;
    }
    private StateController() {
    }

    public Userd getCurrentUser() {
        if(currentUser instanceof SystemAdmin systemAdmin) {
            setCurrentUser(daoController.systemAdminDAO.update(systemAdmin));
        }

        if(currentUser instanceof SystemUser systemUser) {
            setCurrentUser(daoController.systemUserDAO.update(systemUser));
        }

        return currentUser;
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

    public Event getCurrentEvent() {
        if (currentEvent != null) {
            setCurrentEvent(daoController.eventDAO.update(currentEvent));
        }
        return currentEvent;
    }

    public void setCurrentEvent(Event event) {
        if(event != null) {
            daoController.eventDAO.detach(event);
            currentEvent = event;
        } else {
            currentEvent = null;
        }
    }

    public Session getCurrentSession() {
        if (currentSession != null) {
            setCurrentSession(daoController.sessionDAO.update(currentSession));
        }
        return currentSession;
    }

    public void setCurrentSession(Session session) {
        if(session != null) {
            daoController.sessionDAO.detach(session);
            currentSession = session;
        } else {
            currentSession = null;
        }
    }

    public Submission getCurrentSubmission() {
        if (currentSubmission != null) {
            setCurrentSubmission(daoController.submissionDAO.update(currentSubmission));
        }

        return currentSubmission;
    }

    public void setCurrentSubmission(Submission submission) {
        if(submission != null) {
            daoController.submissionDAO.detach(submission);
            currentSubmission = submission;
        } else {
            currentSubmission = null;
        }
    }

    public Certification getCurrentCertification() {
        if (currentCertification != null) {
            setCurrentCertification(daoController.certificationDAO.update(currentCertification));
        }
        return currentCertification;
    }

    public void setCurrentCertification(Certification certification) {
        if(certification != null) {
            daoController.certificationDAO.detach(certification);
            currentCertification = certification;
        } else {
            currentCertification = null;
        }
    }

    public Subscription getCurrentSubscription() {
        if (currentSubscription != null) {
            setCurrentSubscription(daoController.subscriptionDAO.update(currentSubscription));
        }

        return currentSubscription;
    }

    public void setCurrentSubscription(Subscription subscription) {
        if(subscription != null) {
            daoController.subscriptionDAO.detach(subscription);
            currentSubscription = subscription;
        } else {
            currentSubscription = null;
        }

    }

    public SubEvent getCurrentSubEvent() {
        if (currentSubEvent != null) {
            setCurrentSubEvent(daoController.subEventDAO.update(currentSubEvent));
            daoController.subEventDAO.detach(currentSubEvent);
        }

        return currentSubEvent;
    }

    public void setCurrentSubEvent(SubEvent subEvent) {
        if(subEvent != null) {
            daoController.subEventDAO.detach(subEvent);
            currentSubEvent = subEvent;
        } else {
            currentSubEvent = null;
        }

    }
}

