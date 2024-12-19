package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;

public class SubEventManagerController {
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
        eventoNome.setText(AppStateController.stateController.getCurrentSubEvent().getDescription());
        eventoDiretor.setText(AppStateController.stateController.getCurrentSubEvent().getDirector());
        sessionCount.setText(String.valueOf(AppStateController.stateController.getCurrentSubEvent().getSessions().size()));

        LocalDate startDate = AppStateController.stateController.getCurrentSubEvent().getStartDate();
        LocalDate endDate = AppStateController.stateController.getCurrentSubEvent().getEndDate();

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
    private void switchToSubEventUpdater() throws IOException{
        App.setRoot("subEventUpdater");
    }
}
