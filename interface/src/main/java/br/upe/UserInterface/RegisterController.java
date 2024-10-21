package br.upe.UserInterface;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    TextField emailField;
    @FXML
    TextField passwordField;
    @FXML
    CheckBox adminStatus;

    @FXML
    private void register() throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        boolean isAdmin = adminStatus.isSelected();
        boolean isCreated = false;

        if(isAdmin) {
            isCreated = AppStateController.authController.createNewAdmin(email, password);
        } else {
            isCreated = AppStateController.authController.createNewUser(email, password);
        }

        if(isCreated) switchToLogin();
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}