package br.upe.controllers;

import br.upe.entities.Submission;
import br.upe.entities.SystemUser;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;
import br.upe.util.controllers.SystemIOException;
import br.upe.util.controllers.UnauthenticatedUserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SubmissionController {
    private final StateController stateController;
    private final DAOController daoController;

    public SubmissionController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

    public void submitFile(File file) throws SystemException {
        if(stateController.currentUser == null) throw new UnauthenticatedUserException();

        Submission submission = PersistenciaInterface.createSubmission();
        submission.setUser((SystemUser) stateController.currentUser);
        submission.setEvent(stateController.currentEvent);
        try {
            submission.setFilename(file.getName());
            submission.setContent(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new SystemIOException(e.getMessage(), e.getCause());
        }
        stateController.setCurrentSubmission(submission);
        stateController.getCurrentEvent().getSubmissions().add(submission);
        daoController.eventDAO.update(stateController.getCurrentEvent());
        daoController.submissionDAO.save(submission);
    }
}
