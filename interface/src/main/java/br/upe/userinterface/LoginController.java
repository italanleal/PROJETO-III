package br.upe.userinterface;

import java.io.IOException;

import br.upe.pojos.AdminUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    Label statusLabel;
    @FXML
    Label warningLabel;
    @FXML
    TextField emailField;
    @FXML
    TextField passwordField;
    @FXML
    Button loginButton;
    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("register");
    }
    @FXML
    private void login() throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();
        warningLabel.setText("");
        boolean isLogged = false;
        if(!email.isEmpty() && !password.isEmpty()){
            isLogged = AppStateController.authController.login(email, password);
        }
        if(isLogged){
            if(AppStateController.stateController.getCurrentUser() instanceof AdminUser){
                App.setRoot("homeAdmin");
            } else {
                App.setRoot("homeUser");
            }
        } else {
            warningLabel.setText("Couldn't log in");
        }
    }
}
