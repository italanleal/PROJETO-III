package br.upe.operations;

import br.upe.pojos.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface ParserInterface {
    Logger logger = Logger.getLogger(ParserInterface.class.getName());

    static <T> String validadeString(T str) {
        if (str == null) {
            return "";
        }
        return str.toString();
    }

    static Submission parseSubmission(String rawInput) {
        if (rawInput.isEmpty()) return null;

        Submission newSubmission = new Submission();
        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);

        if (matcher.matches()) {
            try {
                if (!matcher.group(1).isEmpty()) newSubmission.setUuid(UUID.fromString(matcher.group(1)));
                if (!matcher.group(3).isEmpty()) newSubmission.setDescritor(matcher.group(3));
                if (!matcher.group(5).isEmpty()) newSubmission.setEventUuid(UUID.fromString(matcher.group(5)));
                if (!matcher.group(7).isEmpty()) newSubmission.setUserUuid(UUID.fromString(matcher.group(7)));
                if (!matcher.group(9).isEmpty()) newSubmission.setDate(Date.from(Instant.parse(matcher.group(9))));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao parsear Submission: ", e);
                return null;
            }
        } else {
            logger.log(Level.WARNING, "Formato inválido para Submission: {}", rawInput);
            return null;
        }

        return newSubmission;
    }

    static User parseUser(String rawInput) {
        if (rawInput.isEmpty()) {
            return null;
        }
        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);

        if (!matcher.matches()) {
            logger.log(Level.WARNING, "Não pegou um match: {0}", rawInput);
            return null;
        }

        User newUser = createUser(matcher);
        try {
            setUserAttributes(newUser, matcher);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao definir atributos do usuário: ", e);
            return null;
        }
        return newUser;
    }

    private static User createUser(Matcher matcher) {
        User newUser;
        if (matcher.group(9).equals("true")) {
            newUser = new AdminUser();
            ((AdminUser) newUser).setEvents(new ArrayList<>());
        } else {
            newUser = new CommomUser();
        }
        newUser.setSubscriptions(new ArrayList<>());
        return newUser;
    }

    private static void setUserAttributes(User user, Matcher matcher) {
        try {
            if (!matcher.group(1).isEmpty()) user.setUuid(UUID.fromString(matcher.group(1)));
            if (!matcher.group(3).isEmpty()) user.setEmail(matcher.group(3));
            if (!matcher.group(5).isEmpty()) user.setPassword(matcher.group(5));
            if (!matcher.group(7).isEmpty()) user.setName(matcher.group(7));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao definir atributos do usuário: ", e);
        }

        addSubscriptions(user, matcher.group(11));

        if (user instanceof AdminUser userHandler) {
            addEvents(userHandler, matcher.group(13));
        }
    }

    private static void addSubscriptions(User user, String subscriptions) {
        for (String subscription : subscriptions.split(",")) {
            if (!subscription.isEmpty()) {
                try {
                    user.addSubscription(SubscriptionCRUD.returnSubscription(UUID.fromString(subscription)));
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Erro ao adicionar assinatura: ", e);
                }
            }
        }
    }

    private static void addEvents(AdminUser userHandler, String events) {
        for (String event : events.split(",")) {
            if (!event.isEmpty()) {
                try {
                    userHandler.addEvent(EventCRUD.returnEvent(UUID.fromString(event)));
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Erro ao adicionar evento: ", e);
                }
            }
        }
    }

    static Subscription parseSubscription(String rawInput) {
        if (rawInput.isEmpty()) return null;

        Subscription newSubscription = new Subscription();
        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)");

        Matcher matcher = pattern.matcher(rawInput);
        if (matcher.matches()) {
            try {
                if (!matcher.group(1).isEmpty()) newSubscription.setUuid(UUID.fromString(matcher.group(1)));
                if (!matcher.group(3).isEmpty()) newSubscription.setSessionUuid(UUID.fromString(matcher.group(3)));
                if (!matcher.group(5).isEmpty()) newSubscription.setUserUuid(UUID.fromString(matcher.group(5)));
                if (!matcher.group(7).isEmpty()) newSubscription.setDate(Date.from(Instant.parse(matcher.group(7))));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao parsear Subscription: ", e);
                return null;
            }
        } else {
            logger.log(Level.WARNING, "Formato inválido para Subscription: {}", rawInput);
            return null;
        }

        return newSubscription;
    }

    static Session parseSession(String rawInput) {
        if (rawInput.isEmpty()) return null;

        Session newSession = new Session();
        newSession.setSubscriptions(new ArrayList<>());

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);

        if (!matcher.matches()) {
            logger.log(Level.WARNING, "Formato inválido para Session: {0}", rawInput);
            return null;
        }

        try {
            setSessionAttributes(newSession, matcher);
            addSubscriptions(newSession, matcher.group(11));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao parsear Session: ", e);
            return null;
        }

        return newSession;
    }

    private static void setSessionAttributes(Session session, Matcher matcher) {
        try {
            if (!matcher.group(1).isEmpty()) session.setUuid(UUID.fromString(matcher.group(1)));
            if (!matcher.group(3).isEmpty()) session.setEventUuid(UUID.fromString(matcher.group(3)));
            if (!matcher.group(5).isEmpty()) session.setDescritor(matcher.group(5));
            if (!matcher.group(7).isEmpty()) session.setStartDate(Date.from(Instant.parse(matcher.group(7))));
            if (!matcher.group(9).isEmpty()) session.setEndDate(Date.from(Instant.parse(matcher.group(9))));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao definir atributos da sessão: ", e);
        }
    }

    private static void addSubscriptions(Session session, String subscriptions) {
        for (String uuid : subscriptions.split(",")) {
            if (!uuid.isEmpty()) {
                try {
                    session.addSubscription(SubscriptionCRUD.returnSubscription(UUID.fromString(uuid)));
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Erro ao adicionar assinatura à sessão: ", e);
                }
            }
        }
    }

    static GreatEvent parseEvent(String rawInput) {
        if (rawInput.isEmpty()) {
            return null;
        }

        GreatEvent newEvent = new GreatEvent();
        newEvent.setSubmissions(new ArrayList<>());
        newEvent.setSessions(new ArrayList<>());

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);

        if (!matcher.matches()) {
            logger.log(Level.WARNING, "Formato inválido para GreatEvent: {0}", rawInput);
            return null;
        }

        try {
            setEventAttributes(newEvent, matcher);
            addSessions(newEvent, matcher.group(11));
            addSubmissions(newEvent, matcher.group(13));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao parsear GreatEvent: ", e);
            return null;
        }

        return newEvent;
    }

    private static void setEventAttributes(GreatEvent event, Matcher matcher) {
        try {
            if (!matcher.group(1).isEmpty()) event.setUuid(UUID.fromString(matcher.group(1)));
            if (!matcher.group(3).isEmpty()) event.setDescritor(matcher.group(3));
            if (!matcher.group(5).isEmpty()) event.setDirector(matcher.group(5));
            if (!matcher.group(7).isEmpty()) event.setStartDate(Date.from(Instant.parse(matcher.group(7))));
            if (!matcher.group(9).isEmpty()) event.setEndDate(Date.from(Instant.parse(matcher.group(9))));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao definir atributos do evento: ", e);
        }
    }

    private static void addSessions(GreatEvent event, String sessions) {
        for (String uuid : sessions.split(",")) {
            if (!uuid.isEmpty()) {
                try {
                    event.addSession(SessionCRUD.returnSession(UUID.fromString(uuid)));
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Erro ao adicionar sessão ao evento: ", e);
                }
            }
        }
    }

    private static void addSubmissions(GreatEvent event, String submissions) {
        for (String uuid : submissions.split(",")) {
            if (!uuid.isEmpty()) {
                try {
                    event.addSubmission(SubmissionCRUD.returnSubmission(UUID.fromString(uuid)));
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Erro ao adicionar submissão ao evento: ", e);
                }
            }
        }
    }
}