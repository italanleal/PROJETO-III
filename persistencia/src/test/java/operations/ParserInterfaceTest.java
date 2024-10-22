package operations;

import br.upe.operations.CRUDInterface;
import br.upe.operations.ParserInterface;
import br.upe.operations.SubmissionCRUD;
import br.upe.pojos.KeeperInterface;
import br.upe.pojos.Submission;
import org.junit.jupiter.api.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ParserInterfaceTest {
    private static final String STATE_PATH = ".\\state";
    private static final String SUBMISSIONS_PATH = STATE_PATH+"\\submissions.csv";
    Logger logger = Logger.getLogger(ParserInterface.class.getName());

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

    @Test
    void testParseSubmission_ValidInput() {
        UUID submissionId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        Submission submission = KeeperInterface.createSubmission();

        submission.setUuid(submissionId);
        submission.setEventUuid(eventId);
        submission.setUserUuid(userId);
        submission.setDescritor("descritor");
        submission.setDate(new Date()); // The current date

        SubmissionCRUD submissionCRUD = CRUDInterface.newSubmissionCRUD();
        submissionCRUD.createSubmission(submission);

        // Format the date as Instant to match what the parser expects (ISO-8601 format)
        String input = submissionId + ";" + submission.getDescritor() + ";" + submission.getEventUuid() + ";" +
                submission.getUserUuid() + ";" + submission.getDate().toInstant().toString() + ";";

        Submission retrievedSubmission = ParserInterface.parseSubmission(ParserInterface.validadeString(input));

        assertNotNull(retrievedSubmission, "The parsed submission was null");
        assertEquals(submissionId, retrievedSubmission.getUuid());
        assertEquals(submission.getDescritor(), retrievedSubmission.getDescritor());
        assertEquals(submission.getEventUuid(), retrievedSubmission.getEventUuid());
        assertEquals(submission.getUserUuid(), retrievedSubmission.getUserUuid());
        assertEquals(submission.getDate().toInstant(), retrievedSubmission.getDate().toInstant());
    }


    @Test
    void testParseSubmission_InvalidInput() {
        String rawInput = "";
        Submission submission = ParserInterface.parseSubmission(rawInput);
        assertNull(submission);
    }
}
