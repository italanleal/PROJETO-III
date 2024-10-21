package br.upe.UserInterface;

import java.io.IOException;
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
            App.setRoot("home");
        } else {
            warningLabel.setText("Couldn't log in");
        }
    }
}
