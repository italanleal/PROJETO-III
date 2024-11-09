package br.upe.controllers;

import br.upe.operations.SubmissionCRUD;
import br.upe.pojos.Submission;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class SubmissionControllerTest {
    private static final Logger logger = Logger.getLogger(SubmissionControllerTest.class.getName());

    private SubmissionController submissionController;
    private File tempFile;
    private static final String TEMP_FILE_PATH = ".\\state\\temp_submissions.csv";

    @Before
    public void setUp() throws IOException {
        tempFile = new File(TEMP_FILE_PATH);
        tempFile.createNewFile();

        CRUDController crudController = new CRUDController();
        StateController stateController = new StateController();
        submissionController = new SubmissionController(stateController, crudController);
    }

    @Test
    public void testGetAllSubmissionsByUser() {
        UUID userUuid = UUID.randomUUID();

        // Criação de submissões de teste
        Submission submission1 = new Submission();
        submission1.setUuid(UUID.randomUUID());
        submission1.setDescritor("Submissão 1");
        submission1.setUserUuid(userUuid); // Atribuindo o mesmo UUID do usuário para este
        submission1.setEventUuid(UUID.randomUUID());

        Submission submission2 = new Submission();
        submission2.setUuid(UUID.randomUUID());
        submission2.setDescritor("Submissão 2");
        submission2.setUserUuid(UUID.randomUUID()); // Atribuindo um UUID diferente
        submission2.setEventUuid(UUID.randomUUID());

        // Criando as submissões no arquivo
        SubmissionCRUD crud = new SubmissionCRUD();
        crud.createSubmission(submission1);
        crud.createSubmission(submission2);

        Collection<Submission> result = submissionController.getAllSubmissionsByUser(userUuid);

        // Verifica se a submissão 1 foi encontrada e a submissão 2 não foi
        try{
            assertTrue(result.contains(submission1));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        assertFalse(result.contains(submission2));
        assertEquals(1, result.size());
    }

    @Test
    public void testRemoveSubmission() {
        UUID userUuid = UUID.randomUUID();
        UUID submissionUuidToRemove = UUID.randomUUID(); // UUID da submissão que será removida

        Submission submission1 = new Submission();
        submission1.setUuid(submissionUuidToRemove); // Esta será a submissão removida
        submission1.setDescritor("Submissão para remover");
        submission1.setUserUuid(userUuid);
        submission1.setEventUuid(UUID.randomUUID());

        Submission submission2 = new Submission();
        submission2.setUuid(UUID.randomUUID()); // Esta submissão não será removida
        submission2.setDescritor("Submissão 2");
        submission2.setUserUuid(userUuid);
        submission2.setEventUuid(UUID.randomUUID());

        SubmissionCRUD crud = new SubmissionCRUD();
        crud.createSubmission(submission1);
        crud.createSubmission(submission2);

        Collection<Submission> allSubmissionsBefore = SubmissionCRUD.returnSubmission();

        try{
            assertTrue(allSubmissionsBefore.contains(submission1));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        try {
            assertTrue(allSubmissionsBefore.contains(submission2));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        crud.deleteSubmission(submissionUuidToRemove);

        Collection<Submission> allSubmissionsAfter = SubmissionCRUD.returnSubmission();
        assertFalse(allSubmissionsAfter.contains(submission1)); // Submissão removida

        try {
            assertTrue(allSubmissionsAfter.contains(submission2));  // Submissão ainda presente
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        assertEquals(1, allSubmissionsAfter.size());
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEMP_FILE_PATH));
    }
}