package br.upe.controllers;
import br.upe.pojos.GreatEvent;
import br.upe.pojos.KeeperInterface;
import br.upe.pojos.Submission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class SubmissionController {
    private CRUDController crudController;
    private StateController stateController;

    public SubmissionController(StateController stateController, CRUDController crudController) {
        this.stateController = stateController;
        this.crudController = crudController;
    }

    private Collection<Submission> getAllSubmissions() {
        return crudController.submissionCRUD.returnSubmission();
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

    public Collection<Submission> getAllSubmissionsByEvent(UUID eventUuid) {
        Collection<Submission> submissions = getAllSubmissions();
        Collection<Submission> filtered = new ArrayList<>();

        for(Submission submission : submissions) {
            if(submission.getUserUuid().equals(eventUuid)) {
                filtered.add(submission);
            }
        }
        return filtered;
    }
    public boolean updateSubmissionDescritor(String descritor) {
        if(descritor != null){
            Submission source = KeeperInterface.createSubmission();
            source.setDescritor(descritor);
            crudController.submissionCRUD.updateSubmission(stateController.getCurrentSubmission().getUuid(), source);
            stateController.setCurrentSubmission(crudController.submissionCRUD.returnSubmission(stateController.getCurrentSubmission().getUuid()));
            return true;
        } return false;
    }

    public void removeSubmission(UUID submissionUuid) {
        Submission submission = crudController.submissionCRUD.returnSubmission(submissionUuid);
        GreatEvent event = crudController.eventCRUD.returnEvent(submission.getEventUuid());

        if(event == null){
            return;
        }

        for(Submission submission1 : event.getSubmissions()){
            if(submission1.getUuid().equals(submissionUuid)){
                event.getSubmissions().remove(submission);
            }
        }

        GreatEvent eventHandler = KeeperInterface.createGreatEvent();
        eventHandler.setSubmissions(event.getSubmissions());

        crudController.submissionCRUD.deleteSubmission(submission.getUuid());
        crudController.eventCRUD.updateEvent(event.getUuid(), eventHandler);
    }
}
