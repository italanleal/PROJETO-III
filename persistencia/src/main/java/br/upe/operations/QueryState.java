package br.upe.operations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryState {
    private static final String FILE_PATH = ".\\state\\event.csv";

    public static UUID userFromEmail(String email) {
        String rawUser = "";

        try(BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(email)) {
                    rawUser  = line;
                    break;
                }
            }
        } catch (IOException e) {
            Logger.getLogger(QueryState.class.getName()).log(Level.SEVERE, "Erro ao ler o arquivo: " + FILE_PATH, e);
        }

        if(rawUser.isEmpty()) return null;

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawUser);

        if(matcher.matches()){
            return UUID.fromString(matcher.group(1));
        } return null;
    }
}
