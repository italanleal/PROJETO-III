package br.upe.operations;

import br.upe.pojos.HelperInterface;
import br.upe.pojos.Submission;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubmissionCRUD extends BaseCRUD {
    private static final Logger logger = Logger.getLogger(SubmissionCRUD.class.getName());
    public static String FILE_PATH = ".\\state\\submissions.csv";

    public SubmissionCRUD() {
        super();
    }

    public void createSubmission(Submission submission) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            buffer.write(ParserInterface.validadeString(submission.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(submission.getDescritor()) + ";");
            buffer.write(ParserInterface.validadeString(submission.getEventUuid()) + ";");
            buffer.write(ParserInterface.validadeString(submission.getUserUuid()) + ";");
            buffer.write((submission.getDate() != null ? ParserInterface.validadeString(submission.getDate().toInstant()) : "") + ";");
            buffer.newLine();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar criar submissão: {0}", submission.getUuid());
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao criar submissão: {0}", submission.getUuid());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void deleteSubmission(UUID submissionUuid) {
        ArrayList<String> fileCopy = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            while (buffer.ready()) {
                fileCopy.add(buffer.readLine());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar ler o arquivo de submissões para exclusão: {0}", submissionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao ler o arquivo de submissões: {0}", submissionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : fileCopy) {
                if (line.contains(submissionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar gravar o arquivo de submissões após exclusão: {0}", submissionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao gravar o arquivo de submissões após exclusão: {0}", submissionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void updateSubmission(UUID submissionUuid, Submission source) {
        Submission existingSubmission = returnSubmission(submissionUuid);
        if (existingSubmission == null) {
            logger.log(Level.WARNING, "Submissão não encontrada para o UUID: {0}", submissionUuid);
            return;
        }
        deleteSubmission(submissionUuid);
        HelperInterface.checkout(source, existingSubmission);
        createSubmission(existingSubmission);
    }

    public static Submission returnSubmission(UUID submissionUuid) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.contains(submissionUuid.toString())) {
                    return ParserInterface.parseSubmission(line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar recuperar a submissão: {0}", submissionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao recuperar a submissão: {0}", submissionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }

    public static Collection<Submission> returnSubmission() {
        Collection<Submission> submissions = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (!line.isEmpty()) {
                    Submission newSubmission = ParserInterface.parseSubmission(line);
                    if (newSubmission != null) submissions.add(newSubmission);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar recuperar as submissões.");
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao recuperar as submissões.");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return submissions;
    }
}
