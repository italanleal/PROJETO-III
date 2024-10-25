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

    private static final String SESSION_PATH = ".\\state\\sessions.csv";
    public SessionCRUD(){ super(SESSION_PATH); }


    public void createSession(Session session){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(SESSION_PATH, true))){
            buffer.write(ParserInterface.validadeString(session.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(session.getEventUuid()) + ";");
            buffer.write(ParserInterface.validadeString(session.getDescritor()) + ";");
            buffer.write((session.getStartDate() != null ? ParserInterface.validadeString(session.getStartDate().toInstant()): "") + ";");
            buffer.write((session.getEndDate() != null ? ParserInterface.validadeString(session.getEndDate().toInstant()): "") + ";");
            for (Subscription sub : session.getSubscriptions()){
                buffer.write(ParserInterface.validadeString(sub.getUuid()) + ",");
            }

            buffer.write(";");

            buffer.newLine();
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error creating session", e);
        }
    }
    public void deleteSession(UUID sessionUuid){
        ArrayList<String> fileCopy = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(SESSION_PATH))){
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            }
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error reading session for deletion", e);
        }

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(SESSION_PATH))){
            for(String line: fileCopy){
                if(line.contains(sessionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error deleting session", e);
        }
    }

    public void updateSession(UUID sessionUuid, Session source) {
        Session existingSession = returnSession(sessionUuid);
        if (existingSession == null) {
            logger.log(Level.WARNING, "Session not found for UUID: {0}", sessionUuid);
            return;
        }
        deleteSession(sessionUuid);
        HelperInterface.checkout(source, existingSession);
        createSession(existingSession);
    }

    public static Session returnSession(UUID sessionUuid){

        try(BufferedReader buffer = new BufferedReader(new FileReader(SESSION_PATH))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(sessionUuid.toString())) {
                    return ParserInterface.parseSession(line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e, () -> "Error reading session for UUID: " + sessionUuid);
        }

        return null;
    }
    public static Collection<Session> returnSession(){
        Collection<Session> sessions = new ArrayList<>();
        try(BufferedReader buffer = new BufferedReader(new FileReader(SESSION_PATH))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(!line.isEmpty()) {
                    sessions.add(ParserInterface.parseSession(line));
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading sessions", e);
        }

        return sessions;
    }
}
