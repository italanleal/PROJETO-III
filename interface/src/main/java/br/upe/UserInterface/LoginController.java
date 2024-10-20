package br.upe.UserInterface;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
    public Label statusLabel;
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

        boolean isLogged = false;
        if(!email.isEmpty() && !password.isEmpty()){
            isLogged = AppStateController.authController.login(email, password);
        }
        if(isLogged){
            statusLabel.setText("logged in");
        } else {
            statusLabel.setText("logged out");
        }
    }
}
