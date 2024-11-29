package br.upe.controllers;

import br.upe.entities.*;

public class State {
    public User currentUser;
    public Event currentEvent;
    public Session currentSession;
    public Submission currentSubmission;
    public Certification currentCertification;
    public Subscription currentSubscription;
    public SubEvent currentSubEvent;

    private EventManager events;

}
