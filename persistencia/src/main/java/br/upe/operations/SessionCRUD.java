package br.upe.operations;

import br.upe.pojos.HelperInterface;
import br.upe.pojos.Session;
import br.upe.pojos.Subscription;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionCRUD extends BaseCRUD {
    private static final Logger logger = Logger.getLogger(SessionCRUD.class.getName());

    public SessionCRUD() {
        super();
    }

    public void createSession(Session session) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\sessions.csv", true))) {
            buffer.write(ParserInterface.validadeString(session.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(session.getEventUuid()) + ";");
            buffer.write(ParserInterface.validadeString(session.getDescritor()) + ";");
            buffer.write((session.getStartDate() != null ? ParserInterface.validadeString(session.getStartDate().toInstant()) : "") + ";");
            buffer.write((session.getEndDate() != null ? ParserInterface.validadeString(session.getEndDate().toInstant()) : "") + ";");
            for (Subscription sub : session.getSubscriptions()) {
                buffer.write(ParserInterface.validadeString(sub.getUuid()) + ",");
            }
            buffer.write(";");
            buffer.newLine();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao criar sessão", e);
        }
    }

    public void deleteSession(UUID sessionUuid) {
        ArrayList<String> fileCopy = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\sessions.csv"))) {
            while (buffer.ready()) {
                fileCopy.add(buffer.readLine());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao ler o arquivo de sessões para exclusão", e);
        }

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\sessions.csv"))) {
            for (String line : fileCopy) {
                if (line.contains(sessionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao escrever no arquivo de sessões para exclusão", e);
        }
    }

    public void updateSession(UUID sessionUuid, Session source) {
        try {
            Session session = returnSession(sessionUuid);
            deleteSession(sessionUuid);
            HelperInterface.checkout(source, session);
            createSession(session);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar sessão", e);
        }
    }

    public static Session returnSession(UUID sessionUuid) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\sessions.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.contains(sessionUuid.toString())) {
                    return ParserInterface.parseSession(line);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao ler o arquivo de sessões para retorno por UUID", e);
        }
        return null;
    }

    public static Collection<Session> returnSession() {
        Collection<Session> sessions = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\sessions.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (!line.isEmpty()) {
                    sessions.add(ParserInterface.parseSession(line));
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao ler o arquivo de sessões para retorno da coleção", e);
        }
        return sessions;
    }
}
