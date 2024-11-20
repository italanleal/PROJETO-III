package br.upe.userinterface;

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
    private void initialize() {
        // Set the label's text to the value of the variable
        eventoNome.setText(AppStateController.stateController.getCurrentEvent().getDescription());
        eventoDiretor.setText(AppStateController.stateController.getCurrentEvent().getDirector());
        sessionCount.setText(String.valueOf(AppStateController.stateController.getCurrentEvent().getSessions().size()));

        LocalDate startDate = AppStateController.stateController.getCurrentEvent().getStartDate();
        LocalDate endDate = AppStateController.stateController.getCurrentEvent().getEndDate();

        if (startDate != null) dataInicio.setText(DateFormat.getDateInstance().format(startDate));
        if (endDate != null) dataFim.setText(DateFormat.getDateInstance().format(endDate));
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
}

