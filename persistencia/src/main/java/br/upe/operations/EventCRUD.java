package br.upe.operations;

import br.upe.pojos.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventCRUD extends BaseCRUD {

    // Configuração do Logger
    private static final Logger logger = Logger.getLogger(EventCRUD.class.getName());

    public EventCRUD() {
        super();
    }

    public void createEvent(GreatEvent event) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\events.csv", true))) {
            buffer.write(ParserInterface.validadeString(event.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(event.getDescritor()) + ";");
            buffer.write(ParserInterface.validadeString(event.getDirector()) + ";");
            buffer.write((event.getStartDate() != null ? event.getStartDate().toInstant().toString() : "") + ";");
            buffer.write((event.getEndDate() != null ? event.getEndDate().toInstant().toString() : "") + ";");

            for (Session session : event.getSessions()) {
                buffer.write(ParserInterface.validadeString(session.getUuid()) + ",");
            }
            buffer.write(";");
            for (Submission submission : event.getSubmissions()) {
                buffer.write(ParserInterface.validadeString(submission.getUuid()) + ",");
            }

            buffer.write(";");
            buffer.newLine();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao criar evento. ", e);
        }
    }

    public void deleteEvent(UUID eventUuid) {
        ArrayList<String> fileCopy = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\events.csv"))) {
            while (buffer.ready()) {
                fileCopy.add(buffer.readLine());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao ler o arquivo para exclusão de evento. ", e);
        }

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\events.csv"))) {
            for (String line : fileCopy) {
                if (line.contains(eventUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao reescrever o arquivo após exclusão de evento. ", e);
        }
    }

    public void updateEvent(UUID eventUuid, GreatEvent source) {
        try {
            GreatEvent event = returnEvent(eventUuid);
            if (event != null) {
                deleteEvent(eventUuid);
                HelperInterface.checkout(source, event);
                createEvent(event);
            } else {
                logger.log(Level.WARNING, "Evento com UUID {0} não encontrado para atualização.", eventUuid);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar evento. ", e);
        }
    }

    public static GreatEvent returnEvent(UUID eventUuid) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\events.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.contains(eventUuid.toString())) {
                    return ParserInterface.parseEvent(line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao retornar evento pelo UUID.", e);
        }
        return null;
    }

    public static Collection<GreatEvent> returnEvent() {
        Collection<GreatEvent> events = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\events.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (!line.isEmpty()) {
                    events.add(ParserInterface.parseEvent(line));
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao retornar lista de eventos. ", e);
        }
        return events;
    }
}
