package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;

public class CertificationController {

    @FXML
    private Label statusLabel;

    @FXML
    private void generateCertification() {
        // Logic to generate certification
        statusLabel.setText("Certification generated successfully!");
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
}