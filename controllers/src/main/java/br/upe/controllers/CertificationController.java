package br.upe.controllers;

import br.upe.entities.*;
import br.upe.util.persistencia.PersistenciaInterface;
import java.util.List;

public class CertificationController {
    private final StateController stateController;
    private final DAOController daoController;

    public CertificationController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

    public void issueCertificateForUser(SystemUser user, Event event) {
        List<Session> sessions = daoController.sessionDAO.findByEvent(event.getId());
        int totalDuration = sessions.stream()
                .mapToInt(Session::getDuration)
                .sum();

        StringBuilder certificateDetails = new StringBuilder();
        certificateDetails.append("Certificate of Participation\n")
                .append("Event: ").append(event.getName()).append("\n")
                .append("Sessions:\n");

        for (Session session : sessions) {
            certificateDetails.append(" - ").append(session.getName()).append("\n");
            List<SubEvent> subEvents = daoController.subEventDAO.findBySession(session.getId());
            for (SubEvent subEvent : subEvents) {
                certificateDetails.append("   * ").append(subEvent.getName()).append("\n");
            }
        }
        certificateDetails.append("Total Duration: ").append(totalDuration).append(" hours\n");
        certificateDetails.append("Issued to: ").append(user.getName());

        System.out.println(certificateDetails.toString()); // Replace with real certificate generation.
    }

    public void issueCertificatesForEvent(Event event) {
        List<Subscription> subscriptions = daoController.subscriptionDAO.findByEvent(event.getId());
        for (Subscription subscription : subscriptions) {
            issueCertificateForUser(subscription.getUser(), event);
        }
    }
}
