package br.upe.dao;

import static org.mockito.Mockito.mock;

import br.upe.entities.Submission;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

class JDBCSubmissionDAODiffblueTest {
    /**
     * Test {@link JDBCSubmissionDAO#JDBCSubmissionDAO(LambdaEntityManagerFactory)}.
     */
    private final JDBCSubmissionDAO submissionDAO = PersistenciaInterface.createJDBCSubmissionDAO(PersistenciaInterface.getDevelopEMF_lambda());
    private final Logger logger = Logger.getLogger(JDBCSubmissionDAODiffblueTest.class.getName());


    @Test
    @DisplayName("Test save method for submissionDAO")
    void saveSessionTest() {

        Submission submission = PersistenciaInterface.createSubmission();
        submission.setFilename("article.txt");
        submission.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        submissionDAO.save(submission);

        Assertions.assertNotEquals(submissionDAO.findById(submission.getId()), Optional.empty());
    }
}
