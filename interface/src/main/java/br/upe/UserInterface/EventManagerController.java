package br.upe.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class EventManagerController{
    @FXML
    public Label eventDescritor;
    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        eventDescritor.setText(AppStateController.stateController.getCurrentEvent().getDescritor());
    }

    @FXML
    private void registerSession() {
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
}
