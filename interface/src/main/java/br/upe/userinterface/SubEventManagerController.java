package br.upe.userinterface;

import br.upe.entities.SubEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;

public class SubEventManagerController {
    @FXML
    Label subEventoNome;
    @FXML
    Label description;
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
        SubEvent subEvent = AppStateController.stateController.getCurrentSubEvent();

        eventoNome.setText(AppStateController.stateController.getCurrentEvent().getTitle());
        subEventoNome.setText(subEvent.getTitle());
        eventoDiretor.setText(subEvent.getDirector());
        description.setText(subEvent.getDescription());
        sessionCount.setText(String.valueOf(subEvent.getSessions().size()));

        LocalDate startDate = subEvent.getStartDate();
        LocalDate endDate = subEvent.getEndDate();

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
    private void switchToEventManager() throws IOException {
        App.setRoot("eventManager");
    }
    @FXML
    private void switchToSubEventRegister() throws IOException {
        App.setRoot("subEventRegister");
    }
    @FXML
    private void switchToSessionList() throws IOException {
        App.setRoot("sessionList");
    }
    @FXML
    private void switchToSubEventUpdater() throws IOException{
        App.setRoot("subEventUpdater");
    }
    @FXML
    private void switchToSubEventList() throws IOException {
        App.setRoot("subEventList");
    }
}
