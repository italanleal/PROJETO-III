package br.upe.operations;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.upe.pojos.*;

public class UserCRUD extends BaseCRUD {
    private static final Logger logger = Logger.getLogger(UserCRUD.class.getName());

    public UserCRUD() {
        super();
    }

    public void createUser(User user) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\users.csv", true))) {
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
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao escrever arquivo em: "+  this.getClass(), e);
        }
    }

    public void deleteUser(UUID userUUID) {
        ArrayList<String> fileCopy = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\users.csv"))) {
            while (buffer.ready()) {
                fileCopy.add(buffer.readLine());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao ler arquivo em: " + this.getClass(), e);
        }

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\users.csv"))) {
            for (String line : fileCopy) {
                if (line.contains(userUUID.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao escrever arquivo em: {1}" + this.getClass(), e);
        }
    }

    public void updateUser(UUID userUUID, User source) {
        User user = returnUser(userUUID);
        deleteUser(userUUID);
        HelperInterface.checkout(source, user);
        createUser(user);
    }

    public static User returnUser(UUID userUUID) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\users.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.contains(userUUID.toString())) {
                    return ParserInterface.parseUser(line);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao ler arquivo em: UserCRUD", e);
        }

        return null;
    }

    public Collection<User> returnUser() {
        Collection<User> users = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\users.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (!line.isEmpty()) {
                    users.add(ParserInterface.parseUser(line));
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao ler arquivo em: UserCRUD", e);
        }

        return users;
    }
}