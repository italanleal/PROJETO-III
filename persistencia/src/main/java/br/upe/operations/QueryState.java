package br.upe.operations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryState {
    private static final Logger logger = Logger.getLogger(QueryState.class.getName());

    public static UUID userFromEmail(String email) {
        String rawUser = "";

        // Tentativa de abrir e ler o arquivo de usuários
        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\users.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.contains(email)) {
                    rawUser = line;
                    break;
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao ler o arquivo de usuários para obter o email. ERRO: ", e);
        }

        if (rawUser.isEmpty()) {
            logger.log(Level.WARNING, "Email não encontrado no arquivo de usuários.");
            return null;
        }

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawUser);

        if (matcher.matches()) {
            try {
                return UUID.fromString(matcher.group(1));
            } catch (IllegalArgumentException e) {
                logger.log(Level.SEVERE, "Erro ao converter UUID do usuário para o email. ERRO: ", e);
            }
        } else {
            logger.log(Level.WARNING, "Formato inválido para o registro do usuário");
        }

        return null;
    }
}
