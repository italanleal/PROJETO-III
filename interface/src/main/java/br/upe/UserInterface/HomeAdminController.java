package br.upe.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HomeAdminController {
    @FXML
    Label userEmail;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());
    }
    @FXML
    private void switchToEventRegister() throws IOException {
        App.setRoot("eventRegister");
    }
    @FXML
    private void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }
}