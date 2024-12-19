package br.upe.userinterface;

import br.upe.entities.SubEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;

public class SubEventManagerController {
    public Label subEventoNome;
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

        eventoNome.setText(subEvent.getEvent().getTitle());
        subEventoNome.setText(subEvent.getDescription());
        eventoDiretor.setText(subEvent.getDirector());
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
    private void switchToSubEventManager() throws IOException {
        App.setRoot("subEventManager");
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
