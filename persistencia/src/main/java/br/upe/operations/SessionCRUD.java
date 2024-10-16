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

    public SessionCRUD(){ super(); }
    private static final String FILE_PATH = ".\\state\\sessions.csv";

    public void createSession(Session session){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_PATH, true))){
            buffer.write(ParserInterface.validadeString(session.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(session.getEventUuid()) + ";");
            buffer.write(ParserInterface.validadeString(session.getDescritor()) + ";");
            buffer.write((session.getStartDate() != null ? ParserInterface.validadeString(session.getStartDate().toInstant()): "") + ";");
            buffer.write((session.getStartDate() != null ? ParserInterface.validadeString(session.getEndDate().toInstant()): "") + ";");

            for (Subscription sub : session.getSubscriptions()){
                buffer.write(ParserInterface.validadeString(sub.getUuid()) + ",");
            }

            buffer.write(";");

            buffer.newLine();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar criar uma sessão: {}", session.getUuid());
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao criar uma sessão: {}", session.getUuid());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    public void deleteSession(UUID sessionUuid){
        ArrayList<String> fileCopy = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))){
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Arquivo não encontrado ao tentar deletar sessão: {}", sessionUuid);
            logger.log(Level.SEVERE, "", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar ler o arquivo para deletar sessão: {}", sessionUuid);
            logger.log(Level.SEVERE, "", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao tentar deletar sessão: {}", sessionUuid);
            logger.log(Level.SEVERE, "", e);
        }

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_PATH))){
            for(String line: fileCopy){
                if(line.contains(sessionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar escrever o arquivo após deletar sessão: {}", sessionUuid);
            logger.log(Level.SEVERE, "", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao reescrever o arquivo após deletar sessão: {}", sessionUuid);
            logger.log(Level.SEVERE, "", e);
        }
    }

    public void updateSession(UUID sessionUuid, Session source) {
        try {
            Session session = returnSession(sessionUuid);
            if (session != null) {
                deleteSession(sessionUuid);
                HelperInterface.checkout(source, session);
                createSession(session);
            } else {
                logger.log(Level.WARNING, "Sessão não encontrada para atualização: {}", sessionUuid);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao tentar atualizar a sessão: {}", sessionUuid);
            logger.log(Level.SEVERE, "", e);
        }
    }

    public static Session returnSession(UUID sessionUuid) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(";");  // Supondo que o separador seja ";"

                // Verifique se o UUID da sessão corresponde ao primeiro campo
                if (data.length > 0 && data[0].equals(sessionUuid.toString())) {
                    return ParserInterface.parseSession(line);  // Reconstrua a sessão a partir da linha
                }
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Arquivo não encontrado ao tentar retornar sessão: {0}", sessionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar ler o arquivo para retornar sessão: {0}", sessionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao tentar retornar sessão: {0}", sessionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        logger.log(Level.WARNING, "Sessão com UUID {0} não encontrada.", sessionUuid);
        return null;
    }


    public static Collection<Session> returnSession(){
        Collection<Session> sessions = new ArrayList<>();
        try(BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(!line.isEmpty()) {
                    sessions.add(ParserInterface.parseSession(line));
                }
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Arquivo não encontrado ao tentar retornar todas as sessões.", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar ler o arquivo para retornar todas as sessões.", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao tentar retornar todas as sessões.", e);
        }
        return sessions;
    }
}
