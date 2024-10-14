package br.upe.controllers;

import br.upe.operations.EventCRUD;
import br.upe.operations.SubmissionCRUD;
import br.upe.pojos.GreatEvent;
import br.upe.pojos.KeeperInterface;
import br.upe.pojos.Submission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

public class SubmissionController {
    private final CRUDController crudController;

    public SubmissionController(CRUDController crudController){
        this.crudController = crudController;
    }

    private Collection<Submission> getAllSubmissions() {
        return SubmissionCRUD.returnSubmission();
    }

    public Collection<Submission> getAllSubmissionsByUser(UUID userUuid) {
        Collection<Submission> submissions = getAllSubmissions();
        Collection<Submission> filtered = new ArrayList<>();

        for(Submission submission : submissions) {
            if(submission.getUserUuid().equals(userUuid)) {
                filtered.add(submission);
            }
        }
        return filtered;
    }

    public Collection<Submission> getAllSubmissionsByEvent(UUID eventUuid){
        Collection<Submission> submissions = getAllSubmissions();
        Collection<Submission> filtered = new ArrayList<>();

        for(Submission submission : submissions) {
            if(submission.getUserUuid().equals(eventUuid)) {
                filtered.add(submission);
            }
        }
        return filtered;
    }

    public void removeSubmission(UUID submissionUuid) {
        Submission submission = SubmissionCRUD.returnSubmission(submissionUuid);
        if(submission == null){
          return;
        }

        GreatEvent event = EventCRUD.returnEvent(submission.getEventUuid());
        if(event == null){
            return;
        }

        Iterator<Submission> iterator = event.getSubmissions().iterator();
        while (iterator.hasNext()) {
            Submission submission1 = iterator.next();
            if (submission1.getUuid().equals(submissionUuid)) {
                iterator.remove();
                break;
            }
        }

        GreatEvent eventHandler = KeeperInterface.createGreatEvent();
        eventHandler.setSubmissions(event.getSubmissions());

        crudController.submissionCRUD.deleteSubmission(submission.getUuid());
        crudController.eventCRUD.updateEvent(event.getUuid(), eventHandler);
    }
}
