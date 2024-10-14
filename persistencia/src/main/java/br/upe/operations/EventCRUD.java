package br.upe.operations;

// Importa as classes necessárias para trabalhar com eventos, arquivos e logging
import br.upe.pojos.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

// Gerencia operações de CRUD para eventos, estendendo BaseCRUD
public class EventCRUD extends BaseCRUD {
    private static final Logger logger = Logger.getLogger(EventCRUD.class.getName());
    private static final String FILE_PATH = ".\\state\\event.csv";

    // Construtor que chama o construtor da classe base (BaseCRUD)
    public EventCRUD() { super(); }

    // cria um evento e salvá-lo no arquivo
    public void createEvent(GreatEvent event) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Escreve os dados do evento no arquivo, separados por ponto e vírgula
            buffer.write(ParserInterface.validadeString(event.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(event.getDescritor()) + ";");
            buffer.write(ParserInterface.validadeString(event.getDirector()) + ";");
            buffer.write((event.getStartDate() != null ? event.getStartDate().toInstant().toString() : "") + ";");
            buffer.write((event.getEndDate() != null ? event.getEndDate().toInstant().toString() : "") + ";");

            // Escreve as sessões associadas ao evento
            for (Session session : event.getSessions()) {
                buffer.write(ParserInterface.validadeString(session.getUuid()) + ",");
            }
            buffer.write(";");

            // Escreve as submissões associadas ao evento
            for (Submission submission : event.getSubmissions()) {
                buffer.write(ParserInterface.validadeString(submission.getUuid()) + ",");
            }
            buffer.write(";");
            buffer.newLine();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao escrever evento no arquivo: {}", event.getUuid());
            logger.log(Level.SEVERE, "Erro: ", e);
        }
    }

    // Método para excluir um evento com base no UUID do evento
    public void deleteEvent(UUID eventUuid) {
        File inputFile = new File(FILE_PATH);
        File tempFile = new File(".\\state\\temp_events.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // Se a linha contiver o UUID do evento a ser excluído, pula a linha
                if (currentLine.contains(eventUuid.toString())) {
                    continue;
                }

                writer.write(currentLine);
                writer.newLine();
            }

            // Verifica se o arquivo original foi excluído
            if (!inputFile.delete()) {
                logger.log(Level.SEVERE, "Erro ao excluir o arquivo original de eventos.");
            }

            // Renomeia o arquivo temporário para o nome do arquivo original
            if (!tempFile.renameTo(inputFile)) {
                logger.log(Level.SEVERE, "Erro ao renomear o arquivo temporário para o arquivo original.");
            }

        } catch (Exception e) {
            // Registra uma mensagem de erro caso ocorra algum problema ao excluir o evento
            logger.log(Level.SEVERE, "Erro ao excluir o evento: {}", eventUuid);
            logger.log(Level.SEVERE, "Erro: ", e);
        }
    }

    // atualiza um evento existente com base no UUID do evento
    public void updateEvent(UUID eventUuid, GreatEvent source) {
        GreatEvent event = returnEvent(eventUuid);
        if (event == null) {
            logger.log(Level.WARNING, "Evento não encontrado: {}", eventUuid);
            return;
        }

        // Exclui o evento antigo e cria um novo com os dados atualizados
        deleteEvent(eventUuid);
        HelperInterface.checkout(source, event);
        createEvent(event);
    }

    // retorna um evento específico com base no UUID
    public static GreatEvent returnEvent(UUID eventUuid) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = buffer.readLine()) != null) {
                // Se a linha contiver o UUID do evento, retorna o evento
                if (line.contains(eventUuid.toString())) {
                    return ParserInterface.parseEvent(line);
                }
            }

        } catch (IOException e) {
            Logger.getLogger(EventCRUD.class.getName());
        }

        return null;
    }

    // retorna todos os eventos
    public static Collection<GreatEvent> returnEvent() {
        // Cria uma coleção de eventos
        Collection<GreatEvent> events = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = buffer.readLine()) != null) {
                if (!line.isEmpty()) {
                    // Adiciona cada evento à coleção após fazer o parse da linha
                    events.add(ParserInterface.parseEvent(line));
                }
            }

        } catch (IOException e) {
            Logger.getLogger(EventCRUD.class.getName()).log(Level.SEVERE, "Erro ao ler eventos.", e);
        }

        return events;
    }
}
