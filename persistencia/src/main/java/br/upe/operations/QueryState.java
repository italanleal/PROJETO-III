package br.upe.operations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Level;

public class QueryState {
    private static final Logger logger = Logger.getLogger(QueryState.class.getName());

    public static UUID userFromEmail(String email) {
        String rawUser = "";

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\users.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(email)) {
                    rawUser  = line;
                    break;
                }
            }
        } catch(Exception e) {
            logger.log(Level.SEVERE, ("User with following email not found: " + email), e);
        }

        if(rawUser.isEmpty()) return null;

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawUser);

        if(matcher.matches()){
            return UUID.fromString(matcher.group(1));
        } return null;
    }
    private QueryState(){}
}
