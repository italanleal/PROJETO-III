package br.upe.dao;

import static org.mockito.Mockito.mock;

import br.upe.entities.Certification;
import br.upe.entities.Submission;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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
        submission.setDate(LocalDate.now());
        submissionDAO.save(submission);

        Assertions.assertNotEquals(submissionDAO.findById(submission.getId()), Optional.empty());
    }

    @Test
    @DisplayName("Test update method for submissionDAO")
    public void updateSubmissionTest() {
        Submission submission = PersistenciaInterface.createSubmission();
        submission.setFilename("article.txt");
        submission.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        submission.setDate(LocalDate.now());
        submissionDAO.save(submission);

        submission.setFilename("article2.txt");
        submission.setContent((new String("lots of data2")).getBytes(StandardCharsets.UTF_8));
        submission.setDate(LocalDate.now());
        submissionDAO.update(submission);

        Assertions.assertEquals(submission.getFilename(), "article2.txt");
    }

    @Test
    @DisplayName("Test delete method for submissionDAO")
    public void deleteSubmissionTest() {
        Submission submission = PersistenciaInterface.createSubmission();
        submission.setFilename("article.txt");
        submission.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        submission.setDate(LocalDate.now());

        submissionDAO.save(submission);

        Long id = submission.getId();
        submissionDAO.delete(submission);
        Assertions.assertEquals(submissionDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test deleteById method for submissionDAO")
    public void deleteByIdSubmissionTest() {
        Submission submission = PersistenciaInterface.createSubmission();
        submission.setFilename("article.txt");
        submission.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        submission.setDate(LocalDate.now());

        submissionDAO.save(submission);


        Long id = submission.getId();
        submissionDAO.deleteById(id);
        Assertions.assertEquals(submissionDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test findById method for submissionDAO")
    public void findByIdSubmissionTest() {
        Submission submission = PersistenciaInterface.createSubmission();
        submission.setFilename("article.txt");
        submission.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        submission.setDate(LocalDate.now());

        submissionDAO.save(submission);

        Long id = submission.getId();
        Assertions.assertEquals(submissionDAO.findById(id).get(), submission);
    }

    @Test
    @DisplayName("Test findAll method for certificationDAO")
    public void findAllSubmissionTest() {
        Submission submission = PersistenciaInterface.createSubmission();
        submission.setFilename("article.txt");
        submission.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        submission.setDate(LocalDate.now());

        submissionDAO.save(submission);

        List<Submission> submissions = submissionDAO.findAll();
        Assertions.assertFalse(submissions.isEmpty());
    }
}
