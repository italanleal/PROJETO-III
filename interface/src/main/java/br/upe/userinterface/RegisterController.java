package br.upe.userinterface;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    Label warningLabel;
    @FXML
    TextField emailField;
    @FXML
    TextField passwordField;
    @FXML
    CheckBox adminStatus;

    @FXML
    private void register() throws IOException {
        warningLabel.setText("");
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
        else warningLabel.setText("Couldn't register");
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}