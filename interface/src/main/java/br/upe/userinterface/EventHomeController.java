package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;

public class EventHomeController {
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


    public void goToListaDeEventos() throws IOException{
        App.setRoot("listaDeEventosUS");
    }

    public void goToListaDeSessoes() throws IOException{
        App.setRoot("listaDeSessoes");
    }
    public void goToListaDeSubEventos() throws IOException{
        App.setRoot("listaDeSubEventos");
    }

    public void goToHomeUser() throws IOException {
        App.setRoot("homeUser");

    }

    public void goToSubmeterArtigo() throws IOException {
        App.setRoot("submissaoArtigo");
    }

}
