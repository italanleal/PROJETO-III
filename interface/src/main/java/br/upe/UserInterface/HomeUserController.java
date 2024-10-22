package br.upe.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HomeUserController {
    @FXML
    Label userEmail;

    @FXML
    public void initialize() {
        // Set the label's text to the value of the variable
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());
    }

    @FXML
    public void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }
}
