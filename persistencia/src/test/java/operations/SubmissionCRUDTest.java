package operations;

import br.upe.operations.SubmissionCRUD;
import br.upe.pojos.Submission;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SubmissionCRUDTest {

    private SubmissionCRUD submissionCRUD;
    private Submission submission; // Usar a mesma submissão em vários testes

    @BeforeAll
    public static void clearFiles() {
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\submissions.csv"))) {
            buffer.write("");
        } catch(IOException e) {
            System.out.println("Erro ao limpar arquivo de submissões: " + e.getMessage());
        }
    }

    @BeforeEach
    public void setUp() {
        submissionCRUD = new SubmissionCRUD();

        // Criar uma submissão que pode ser reutilizada em outros testes
        submission = new Submission();
        UUID submissionUuid = UUID.randomUUID();
        submission.setUuid(submissionUuid);
        submission.setEventUuid(UUID.randomUUID());
        submission.setUserUuid(UUID.randomUUID());
        submission.setDate(new Date());
        submissionCRUD.createSubmission(submission);
    }

    @Test
    void testCreateSubmission() {
        Submission retrievedSubmission = SubmissionCRUD.returnSubmission(submission.getUuid());
        assertNotNull(retrievedSubmission, "A submissão retornada não deve ser nula.");
        assertEquals(submission.getUuid(), retrievedSubmission.getUuid(), "O UUID da submissão deve ser igual ao esperado.");
        assertEquals(submission.getEventUuid(), retrievedSubmission.getEventUuid(), "O UUID do evento deve ser igual ao esperado.");
        assertEquals(submission.getUserUuid(), retrievedSubmission.getUserUuid(), "O UUID do usuário deve ser igual ao esperado.");
        assertEquals(submission.getDate(), retrievedSubmission.getDate(), "A data deve ser igual ao esperado.");
    }

    @Test
    void testUpdateSubmission() {
        // Atualiza a submissão com novos dados
        Submission updatedSubmission = new Submission();
        updatedSubmission.setUuid(submission.getUuid());
        updatedSubmission.setEventUuid(UUID.randomUUID()); // Novo UUID para o evento
        updatedSubmission.setUserUuid(UUID.randomUUID()); // Novo UUID para o usuário
        updatedSubmission.setDate(new Date(System.currentTimeMillis() + 1000000)); // Atualiza a data

        submissionCRUD.updateSubmission(submission.getUuid(), updatedSubmission);

        Submission retrievedSubmission = SubmissionCRUD.returnSubmission(submission.getUuid());
        assertNotNull(retrievedSubmission, "A submissão retornada não deve ser nula.");
        assertEquals(updatedSubmission.getUuid(), retrievedSubmission.getUuid(), "O UUID da submissão deve ser igual ao esperado.");
        assertEquals(updatedSubmission.getEventUuid(), retrievedSubmission.getEventUuid(), "O UUID do evento deve ser igual ao esperado.");
        assertEquals(updatedSubmission.getUserUuid(), retrievedSubmission.getUserUuid(), "O UUID do usuário deve ser igual ao esperado.");
        assertEquals(updatedSubmission.getDate(), retrievedSubmission.getDate(), "A data atualizada deve ser igual à esperada.");
    }

    @Test
    void testDeleteSubmission() {
        submissionCRUD.deleteSubmission(submission.getUuid());
        Submission removedSubmission = SubmissionCRUD.returnSubmission(submission.getUuid());
        assertNull(removedSubmission, "A submissão removida deve ser nula.");
    }

    @Test
    void testReturnSubmission() {
        Submission retrievedSubmission = SubmissionCRUD.returnSubmission(submission.getUuid());
        assertNotNull(retrievedSubmission, "A submissão retornada não deve ser nula.");
        assertEquals(submission.getUuid(), retrievedSubmission.getUuid(), "O UUID da submissão deve ser igual ao esperado.");
        assertEquals(submission.getDate(), retrievedSubmission.getDate(), "A data deve ser igual ao esperado.");
        assertInstanceOf(Submission.class, retrievedSubmission, "O objeto retornado deve ser uma instância de Submission.");
    }
}