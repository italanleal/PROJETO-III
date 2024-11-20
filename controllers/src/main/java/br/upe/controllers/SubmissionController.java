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
    public boolean updateSubmissionDescritor(String descritor) {
        if(descritor != null){
            Submission source = KeeperInterface.createSubmission();
            source.setDescritor(descritor);
            crudController.submissionCRUD.updateSubmission(stateController.getCurrentSubmission().getUuid(), source);
            stateController.setCurrentSubmission(crudController.submissionCRUD.returnSubmission(stateController.getCurrentSubmission().getUuid()));
            return true;
        } return false;
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
        daoController.submissionDAO.save(submission);
    }
}
