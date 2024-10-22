package operations;

import br.upe.operations.SubmissionCRUD;
import br.upe.pojos.Submission;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

class SubmissionCRUDTest {
    private SubmissionCRUD submissionCRUD;
    private static final String STATE_PATH = ".\\state";
    private static final String SUBMISSIONS_PATH = STATE_PATH+"\\submissions.csv";
    private static final Logger logger = Logger.getLogger(SubmissionCRUDTest.class.getName());


    @AfterEach
    void clearFiles() {
        try {
            Files.deleteIfExists(Paths.get(SUBMISSIONS_PATH));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when trying to delete files", e);
        }

        try {
            Files.deleteIfExists(Paths.get(STATE_PATH));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when trying to delete directory", e);
        }
    }

    @BeforeEach
    public void setUp() {
        submissionCRUD = new SubmissionCRUD();
    }

    @Test
    void testCreateSubmission() {
        Submission submission = new Submission();
        UUID submissionUuid = UUID.randomUUID();
        submission.setUuid(submissionUuid);
        submission.setEventUuid(UUID.randomUUID());
        submission.setUserUuid(UUID.randomUUID());
        submission.setDate(new Date());
        submissionCRUD.createSubmission(submission);

        Submission retrievedSubmission = SubmissionCRUD.returnSubmission(submissionUuid);
        assertNotNull(retrievedSubmission, "A submissão retornada não deve ser nula.");
        assertEquals(submission.getUuid(), retrievedSubmission.getUuid(), "O UUID da submissão deve ser igual ao esperado.");
        assertEquals(submission.getEventUuid(), retrievedSubmission.getEventUuid(), "O UUID do evento deve ser igual ao esperado.");
        assertEquals(submission.getUserUuid(), retrievedSubmission.getUserUuid(), "O UUID do usuário deve ser igual ao esperado.");
        assertEquals(submission.getDate(), retrievedSubmission.getDate(), "A data deve ser igual ao esperado.");
    }

    @Test
    void testUpdateSubmission() {
        Submission submission = new Submission();
        UUID submissionUuid = UUID.randomUUID();
        submission.setUuid(submissionUuid);
        submission.setEventUuid(UUID.randomUUID());
        submission.setUserUuid(UUID.randomUUID());
        submission.setDate(new Date());
        submissionCRUD.createSubmission(submission);

        // Atualiza a submissão com novos dados
        Submission updatedSubmission = new Submission();
        updatedSubmission.setUuid(submissionUuid);
        updatedSubmission.setEventUuid(UUID.randomUUID()); // Novo UUID para o evento
        updatedSubmission.setUserUuid(UUID.randomUUID()); // Novo UUID para o usuário
        updatedSubmission.setDate(new Date(System.currentTimeMillis() + 1000000)); // Atualiza a data

        submissionCRUD.updateSubmission(submissionUuid, updatedSubmission);

        Submission retrievedSubmission = SubmissionCRUD.returnSubmission(submissionUuid);
        assertNotNull(retrievedSubmission, "A submissão retornada não deve ser nula.");
        assertEquals(updatedSubmission.getUuid(), retrievedSubmission.getUuid(), "O UUID da submissão deve ser igual ao esperado.");
        assertEquals(updatedSubmission.getEventUuid(), retrievedSubmission.getEventUuid(), "O UUID do evento deve ser igual ao esperado.");
        assertEquals(updatedSubmission.getUserUuid(), retrievedSubmission.getUserUuid(), "O UUID do usuário deve ser igual ao esperado.");
        assertEquals(updatedSubmission.getDate(), retrievedSubmission.getDate(), "A data atualizada deve ser igual à esperada.");
    }

    @Test
    void testDeleteSubmission() {
        Submission submission = new Submission();
        UUID submissionUuid = UUID.randomUUID();
        submission.setUuid(submissionUuid);
        submission.setEventUuid(UUID.randomUUID());
        submission.setUserUuid(UUID.randomUUID());
        submission.setDate(new Date());
        submissionCRUD.createSubmission(submission);

        submissionCRUD.deleteSubmission(submissionUuid);
        Submission removedSubmission = SubmissionCRUD.returnSubmission(submissionUuid);
        assertNull(removedSubmission, "A submissão removida deve ser nula.");
    }

    @Test
    void testReturnSubmission() {

        Submission submission = new Submission();
        UUID submissionUuid = UUID.randomUUID();
        submission.setUuid(submissionUuid);
        submission.setEventUuid(UUID.randomUUID());
        submission.setUserUuid(UUID.randomUUID());
        submission.setDate(new Date());
        submissionCRUD.createSubmission(submission);


        Submission retrievedSubmission = SubmissionCRUD.returnSubmission(submissionUuid);

        assertNotNull(retrievedSubmission, "A submissão retornada não deve ser nula.");
        assertEquals(submission.getUuid(), retrievedSubmission.getUuid(), "O UUID da submissão deve ser igual ao esperado.");
        assertEquals(submission.getEventUuid(), retrievedSubmission.getEventUuid(), "O UUID do evento deve ser igual ao esperado.");
        assertEquals(submission.getUserUuid(), retrievedSubmission.getUserUuid(), "O UUID do usuário deve ser igual ao esperado.");
        assertEquals(submission.getDate(), retrievedSubmission.getDate(), "A data deve ser igual ao esperado.");
    }
}
