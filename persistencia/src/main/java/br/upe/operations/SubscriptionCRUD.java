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

    public SubscriptionCRUD() { super(); }

    public void createSubscription(Subscription subscription) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\subscriptions.csv", true))) {
            buffer.write(ParserInterface.validadeString(subscription.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(subscription.getSessionUuid()) + ";");
            buffer.write(ParserInterface.validadeString(subscription.getUserUuid()) + ";");
            buffer.write((subscription.getDate() != null ? ParserInterface.validadeString(subscription.getDate().toInstant().toString()) : "") + ";");

            buffer.newLine();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao criar assinatura: falha ao escrever no arquivo", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao criar assinatura", e);
        }
    }

    public void deleteSubscription(UUID subscriptionUuid) {
        ArrayList<String> fileCopy = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\subscriptions.csv"))) {
            while (buffer.ready()) {
                fileCopy.add(buffer.readLine());
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Erro ao deletar assinatura: arquivo não encontrado", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao ler o arquivo para deletar assinatura", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao deletar assinatura", e);
        }

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\subscriptions.csv"))) {
            for (String line : fileCopy) {
                if (line.contains(subscriptionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar o arquivo após deletar a assinatura", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao atualizar o arquivo de assinaturas", e);
        }
    }

    public void updateSubscription(UUID subscriptionUuid, Subscription source) {
        try {
            Subscription subscription = returnSubscription(subscriptionUuid);
            deleteSubscription(subscriptionUuid);
            HelperInterface.checkout(source, subscription);
            createSubscription(subscription);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar assinatura", e);
        }
    }

    public static Subscription returnSubscription(UUID subscriptionUuid) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\subscriptions.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.contains(subscriptionUuid.toString())) {
                    return ParserInterface.parseSubscription(line);
                }
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Erro ao retornar assinatura: arquivo não encontrado", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao ler o arquivo para retornar assinatura", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao retornar assinatura", e);
        }
        return null;
    }

    public static Collection<Subscription> returnSubscription() {
        Collection<Subscription> subscriptions = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\subscriptions.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (!line.isEmpty()) {
                    subscriptions.add(ParserInterface.parseSubscription(line));
                }
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Erro ao retornar todas as assinaturas: arquivo não encontrado", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao ler o arquivo para retornar todas as assinaturas", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao retornar todas as assinaturas", e);
        }

        return subscriptions;
    }
}