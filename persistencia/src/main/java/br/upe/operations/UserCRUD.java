package br.upe.operations;

import java.io.*;
import br.upe.pojos.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UserCRUD extends BaseCRUD {
    private static final Logger logger = Logger.getLogger(UserCRUD.class.getName());
    private static final String FILE_PATH = ".\\state\\users.csv";

    public UserCRUD() {
        super();
    }

    public void createUser(User user) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            buffer.write(ParserInterface.validadeString(user.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(user.getEmail()) + ";");
            buffer.write(ParserInterface.validadeString(user.getPassword()) + ";");
            buffer.write(ParserInterface.validadeString(user.getName()) + ";");
            buffer.write(ParserInterface.validadeString(user.isAdmin()) + ";");

            for (Subscription sub : user.getSubscriptions()) {
                buffer.write(ParserInterface.validadeString(sub.getUuid()) + ",");
            }
            buffer.write(";");

            if (user instanceof AdminUser userHandler) {
                for (GreatEvent event : userHandler.getEvents()) {
                    buffer.write(ParserInterface.validadeString(event.getUuid()) + ",");
                }
            }

            buffer.write(";");
            buffer.newLine();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar criar o usuário: {0}", user.getUuid());
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao criar o usuário: {0}", user.getUuid());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void deleteUser(UUID userUUID) {
        ArrayList<String> fileCopy = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            while (buffer.ready()) {
                fileCopy.add(buffer.readLine());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar ler o arquivo de usuários para exclusão: {0}", userUUID);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao ler o arquivo de usuários: {0}", userUUID);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : fileCopy) {
                if (line.contains(userUUID.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar gravar o arquivo de usuários após exclusão: {0}", userUUID);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao gravar o arquivo de usuários após exclusão: {0}", userUUID);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void updateUser(UUID userUUID, User source) {
        User user = returnUser(userUUID);
        if (user == null) {
            logger.log(Level.WARNING, "Usuário não encontrado para o UUID: {0}", userUUID);
            return;
        }
        deleteUser(userUUID);
        HelperInterface.checkout(source, user);
        createUser(user);
    }

    public static User returnUser(UUID userUUID) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.contains(userUUID.toString())) {
                    return ParserInterface.parseUser(line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar recuperar o usuário: {0}, ERRO: {1}", new Object[]{userUUID, e.getMessage()});
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao recuperar o usuário com id: {0}, ERRO: {1}", new Object[]{userUUID, e.getMessage()});
        }
        return null;
    }
}
