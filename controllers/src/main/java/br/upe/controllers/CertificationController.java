package br.upe.controllers;

import br.upe.entities.*;
import br.upe.util.persistencia.PersistenciaInterface;
import br.upe.util.persistencia.SystemException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CertificationController {
    private final StateController stateController;
    private final DAOController daoController;

    public CertificationController(StateController stateController, DAOController daoController) {
        this.stateController = stateController;
        this.daoController = daoController;
    }

    public void issueCertificate(SystemUser user, Event event) throws SystemException {
        List<Session> userSessions = event.getSessions().stream()
                .filter(session -> session.getSubscriptions().stream()
                        .anyMatch(subscription -> subscription.getUser().equals(user)))
                .collect(Collectors.toList());

        StringBuilder certificateContent = new StringBuilder();
        certificateContent.append("Event: ").append(event.getTitle()).append("\n");
        certificateContent.append("Total Duration: ").append(Duration.between(event.getStartDate().atStartOfDay(), event.getEndDate().atStartOfDay()).toDays()).append(" days\n");
        certificateContent.append("Sessions:\n");

        for (Session session : userSessions) {
            certificateContent.append(" - ").append(session.getTitle());
            if (session.getSubEvent() != null) {
                certificateContent.append(" (Sub-Event: ").append(session.getSubEvent().getTitle()).append(")");
            }
            certificateContent.append("\n");
        }

        Certification certification = PersistenciaInterface.createCertification();
        certification.setUser(user);
        certification.setEvent(event);
        certification.setContent(certificateContent.toString().getBytes());
        certification.setDate(LocalDate.now());

        daoController.certificationDAO.save(certification);
    }

    public void issueAllCertificatesForEvent(Event event) throws SystemException {
        List<SystemUser> users = daoController.systemUserDAO.findAll();

        for (SystemUser user : users) {
            if (event.getSessions().stream()
                    .anyMatch(session -> session.getSubscriptions().stream()
                            .anyMatch(subscription -> subscription.getUser().equals(user)))) {
                issueCertificate(user, event);
            }
        }
    }
}