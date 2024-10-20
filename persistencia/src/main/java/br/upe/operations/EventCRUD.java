package br.upe.operations;
import  br.upe.pojos.*;

import java.io.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;


public class EventCRUD extends BaseCRUD {
    private static final Logger logger = Logger.getLogger(EventCRUD.class.getName());

    private static final String EVENTS_PATH = ".\\state\\events.csv";
    public EventCRUD(){ super(EVENTS_PATH); }


    public void createEvent(GreatEvent event){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(EVENTS_PATH, true))){
            buffer.write(ParserInterface.validadeString(event.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(event.getDescritor()) + ";");
            buffer.write(ParserInterface.validadeString(event.getDirector()) + ";");
            buffer.write((event.getStartDate() != null ? event.getStartDate().toInstant().toString(): "") + ";");
            buffer.write((event.getEndDate() != null ? event.getEndDate().toInstant().toString(): "") + ";");

            for (Session session : event.getSessions()){
                buffer.write(ParserInterface.validadeString(session.getUuid()) + ",");
            }
            buffer.write(";");
            for (Submission submission : event.getSubmissions()){
                buffer.write(ParserInterface.validadeString(submission.getUuid()) + ",");
            }

            buffer.write(";");

            buffer.newLine();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating subscription", e);
        }
    }
    public void deleteEvent(UUID eventUuid){
        ArrayList<String> fileCopy = new ArrayList<>();
        try(BufferedReader buffer = new BufferedReader(new FileReader(EVENTS_PATH))){
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading subscriptions file for deletion", e);
        }

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(EVENTS_PATH))){
            for(String line: fileCopy){
                if(line.contains(eventUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error writing subscriptions file after deletion", e);
        }
    }

    public void updateEvent(UUID eventUuid, GreatEvent source) {

        GreatEvent existingEvent = returnEvent(eventUuid);

        if (existingEvent == null) {
            logger.log(Level.WARNING, "Event not found for UUID: {0}", eventUuid);
            return;
        }
        deleteEvent(eventUuid);
        HelperInterface.checkout(source, existingEvent);
        createEvent(existingEvent);
    }

    public static GreatEvent returnEvent(UUID eventUuid){
        try(BufferedReader buffer = new BufferedReader(new FileReader(EVENTS_PATH))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(eventUuid.toString())) {
                    return ParserInterface.parseEvent(line);
                }

            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e, () -> "Error reading event for UUID: " + eventUuid);
        }

        return null;
    }
    public static Collection<GreatEvent> returnEvent(){
        Collection<GreatEvent> events = new ArrayList<>();
        try(BufferedReader buffer = new BufferedReader(new FileReader(EVENTS_PATH))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(!line.isEmpty()) {
                    events.add(ParserInterface.parseEvent(line));

                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading events", e);
        }

        return events;
    }
}
