package br.upe.userinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;

public class EventManagerController{


    @FXML
    Label eventoNome;
    @FXML
    Label eventoDiretor;
    @FXML
    Label dataInicio;
    @FXML
    Label dataFim;
    @FXML
    Label sessionCount;
    @FXML
    Label subEventCount;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        eventoNome.setText(AppStateController.stateController.getCurrentEvent().getDescription());
        eventoDiretor.setText(AppStateController.stateController.getCurrentEvent().getDirector());
        sessionCount.setText(String.valueOf(AppStateController.stateController.getCurrentEvent().getSessions().size()));
        subEventCount.setText(String.valueOf(AppStateController.stateController.getCurrentEvent().getSubEvents().size()));

        LocalDate startDate = AppStateController.stateController.getCurrentEvent().getStartDate();
        LocalDate endDate = AppStateController.stateController.getCurrentEvent().getEndDate();

        if (startDate != null) dataInicio.setText(startDate.toString());
        if (endDate != null) dataFim.setText(endDate.toString());
    }

    @FXML
    private void switchToSessionRegister() throws IOException {
        App.setRoot("sessionRegister");
    }

    @FXML
    private void switchToHomeAdmin() throws IOException {
        App.setRoot("homeAdmin");
    }
    @FXML
    private void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }
    @FXML
    private void switchToEventUpdater() throws IOException {
        App.setRoot("eventUpdater");
    }
    @FXML
    private void switchToSessionList() throws IOException {
        App.setRoot("sessionList");
    }

    @FXML
    private void switchToSubEventRegister() throws IOException {
        App.setRoot("subEventRegister");
    }
    @FXML
    private void switchToSubEventList() throws IOException {
        App.setRoot("subEventList");
    }
}

