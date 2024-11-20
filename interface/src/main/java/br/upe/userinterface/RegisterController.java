package br.upe.userinterface;

import java.io.IOException;

import br.upe.util.persistencia.SystemException;
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
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField cpfField;
    @FXML
    CheckBox adminStatus;

    @FXML
    private void register() throws IOException {
        warningLabel.setText("");
        String email = emailField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String cpf = cpfField.getText();

        boolean isAdmin = adminStatus.isSelected();

        if(isAdmin) {
            try{
                AppStateController.authController.createNewAdmin(name, surname, cpf, email, password);
            } catch (SystemException e) {
                warningLabel.setText(e.getMessage());
            }
        } else {
            try{
                AppStateController.authController.createNewUser(name, surname, cpf, email, password);
            } catch (SystemException e) {
                warningLabel.setText(e.getMessage());
            }
        }

        switchToLogin();
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}