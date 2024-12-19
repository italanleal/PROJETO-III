package br.upe.userinterface;

import br.upe.util.persistencia.SystemException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;

public class UserProfileController {
    @FXML
    Label warningLabel;
    @FXML
    TextField nameField;
    @FXML
    TextField emailField;
    @FXML
    TextField surnameField;
    @FXML
    TextField cpfField;


    @FXML
    private void initialize() {
        nameField.appendText(AppStateController.stateController.getCurrentUser().getName());
        surnameField.appendText(AppStateController.stateController.getCurrentUser().getSurname());
        emailField.appendText(AppStateController.stateController.getCurrentUser().getEmail());
        cpfField.appendText(AppStateController.stateController.getCurrentUser().getCpf());
    }

    @FXML
    private void updateUserName() {
        if(!nameField.getText().equals(AppStateController.stateController.getCurrentUser().getName()))
            AppStateController.userController.updateUserName(nameField.getText());
    }
    @FXML
    private void updateUserSurname() {
        if(!surnameField.getText().equals(AppStateController.stateController.getCurrentUser().getSurname()))
            AppStateController.userController.updateUserSurname(surnameField.getText());
    }
    @FXML
    private void updateUserEmail() {
        if(!emailField.getText().equals(AppStateController.stateController.getCurrentUser().getEmail()))
            try {
                AppStateController.userController.updateUserEmail(emailField.getText());
            } catch (SystemException e) {
                warningLabel.setText(e.getMessage());
            }
    }

    @FXML
    private void home() throws IOException {
        if(AppStateController.stateController.getCurrentUser().isSu()){
            App.setRoot("homeAdmin");
        } else {
            App.setRoot("homeUser");
        }
    }
    @FXML
    private void logout() throws IOException {
        AppStateController.stateController.close();
        AppStateController.authController.logout();
        App.setRoot("login");
    }
}
