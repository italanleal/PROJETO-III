package br.upe.operations;

import br.upe.pojos.HelperInterface;
import br.upe.pojos.Subscription;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubscriptionCRUD extends BaseCRUD {
    private static final Logger logger = Logger.getLogger(SubscriptionCRUD.class.getName());
    private static final String FILE_PATH = ".\\state\\subscriptions.csv";

    public SubscriptionCRUD() {
        super();
    }

    public void createSubscription(Subscription subscription) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            buffer.write(ParserInterface.validadeString(subscription.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(subscription.getSessionUuid()) + ";");
            buffer.write(ParserInterface.validadeString(subscription.getUserUuid()) + ";");
            buffer.write((subscription.getDate() != null ? ParserInterface.validadeString(subscription.getDate().toInstant().toString()) : "") + ";");
            buffer.newLine();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar criar inscrição: {0}", subscription.getUuid());
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao criar inscrição: {0}", subscription.getUuid());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void deleteSubscription(UUID subscriptionUuid) {
        ArrayList<String> fileCopy = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            while (buffer.ready()) {
                fileCopy.add(buffer.readLine());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar ler o arquivo de inscrições para exclusão: {0}", subscriptionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao ler o arquivo de inscrições: {0}", subscriptionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : fileCopy) {
                if (line.contains(subscriptionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar gravar o arquivo de inscrições após exclusão: {0}", subscriptionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao gravar o arquivo de inscrições após exclusão: {0}", subscriptionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void updateSubscription(UUID subscriptionUuid, Subscription source) {
        Subscription subscription = returnSubscription(subscriptionUuid);
        if (subscription == null) {
            logger.log(Level.WARNING, "Inscrição não encontrada para o UUID: {0}", subscriptionUuid);
            return;
        }
        deleteSubscription(subscriptionUuid);
        HelperInterface.checkout(source, subscription);
        createSubscription(subscription);
    }

    public static Subscription returnSubscription(UUID subscriptionUuid) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.contains(subscriptionUuid.toString())) {
                    return ParserInterface.parseSubscription(line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar recuperar a inscrição: {0}", subscriptionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao recuperar a inscrição: {0}", subscriptionUuid);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }

    public static Collection<Subscription> returnSubscription() {
        Collection<Subscription> subscriptions = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (!line.isEmpty()) {
                    subscriptions.add(ParserInterface.parseSubscription(line));
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro de IO ao tentar recuperar as inscrições.");
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao recuperar as inscrições.");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return subscriptions;
    }
}
