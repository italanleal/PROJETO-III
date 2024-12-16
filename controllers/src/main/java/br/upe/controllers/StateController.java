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

    public Event getCurrentEvent() {
        return currentEvent;
    }
    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }
    public Session getCurrentSession() {
        return currentSession;
    }
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }
    public Certification getCurrentCertification() {
        return currentCertification;
    }
    public void setCurrentCertification(Certification currentCertification) {
        this.currentCertification = currentCertification;
    }
    public Subscription getCurrentSubscription() {
        return currentSubscription;
    }
    public void setCurrentSubscription(Subscription currentSubscription) {
        this.currentSubscription = currentSubscription;
    }
    public SubEvent getCurrentSubEvent() {
        return currentSubEvent;
    }
    public void setCurrentSubEvent(SubEvent currentSubEvent) {
        this.currentSubEvent = currentSubEvent;
    }
    public void setCurrentSubmission(Submission currentSubmission) {
        this.currentSubmission = currentSubmission;
    }
    public Submission getCurrentSubmission() {
        return currentSubmission;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}