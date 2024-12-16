package br.upe.controllers;

import br.upe.entities.*;

public class StateController {
    public User currentUser;
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

    public User getCurrentUser() {
        if(currentUser instanceof SystemAdmin systemAdmin) {
            daoController.systemAdminDAO.update(systemAdmin);
            daoController.systemAdminDAO.detach(systemAdmin);
        }

        if(currentUser instanceof SystemUser user) {
            daoController.systemUserDAO.update(user);
            daoController.systemUserDAO.detach(user);
        }

        return currentUser;
    }

    public void setCurrentUser(User user) {
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
            daoController.eventDAO.update(currentEvent);
            daoController.eventDAO.detach(currentEvent);
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
            daoController.sessionDAO.update(currentSession);
            daoController.sessionDAO.detach(currentSession);
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
            daoController.submissionDAO.update(currentSubmission);
            daoController.submissionDAO.detach(currentSubmission);
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
            daoController.certificationDAO.update(currentCertification);
            daoController.certificationDAO.detach(currentCertification);
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
            daoController.subscriptionDAO.update(currentSubscription);
            daoController.subscriptionDAO.detach(currentSubscription);
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
            daoController.subEventDAO.update(currentSubEvent);
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
