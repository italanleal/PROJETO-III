package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;

public class SubEventHomeController {
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
        eventoNome.setText(AppStateController.stateController.getCurrentSubEvent().getDescription());
        eventoDiretor.setText(AppStateController.stateController.getCurrentSubEvent().getDirector());
        sessionCount.setText(String.valueOf(AppStateController.stateController.getCurrentSubEvent().getSessions().size()));

        LocalDate startDate = AppStateController.stateController.getCurrentSubEvent().getStartDate();
        LocalDate endDate = AppStateController.stateController.getCurrentSubEvent().getEndDate();

        if (startDate != null) dataInicio.setText(startDate.toString());
        if (endDate != null) dataFim.setText(endDate.toString());
    }

    @FXML
    private void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }

    public void goToListaDeSessoes() throws IOException{
        App.setRoot("listaDeSessoesSub");
    }
    public void goToListaDeSubEventos() throws IOException{
        App.setRoot("listaDeSubEventos");
    }
    public void goToHomeUser() throws IOException {
        App.setRoot("homeUser");

    }
}
