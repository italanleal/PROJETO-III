package br.upe.userinterface;


import br.upe.util.controllers.PasswordDoesNotMatch;
import br.upe.util.persistencia.SystemException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    PasswordField currentPassword;
    @FXML
    PasswordField newPassword;
    @FXML
    PasswordField confirmNewPassword;

    @FXML
    private void initialize() {
        nameField.appendText(AppStateController.stateController.getCurrentUser().getName());
        surnameField.appendText(AppStateController.stateController.getCurrentUser().getSurname());
        emailField.appendText(AppStateController.stateController.getCurrentUser().getEmail());
        cpfField.appendText(AppStateController.stateController.getCurrentUser().getCpf());
        currentPassword.appendText(AppStateController.stateController.getCurrentUser().getPassword());
    }

    @FXML
    private void updateUserName() throws IOException {
        if(!nameField.getText().equals(AppStateController.stateController.getCurrentUser().getName()))
            AppStateController.userController.updateUserName(nameField.getText());
        App.setRoot("userProfile");
    }
    @FXML
    private void updateUserSurname() throws IOException {
        if(!surnameField.getText().equals(AppStateController.stateController.getCurrentUser().getSurname()))
            AppStateController.userController.updateUserSurname(surnameField.getText());
        App.setRoot("userProfile");
    }
    @FXML
    private void updateUserEmail() throws IOException {
        if(!emailField.getText().equals(AppStateController.stateController.getCurrentUser().getEmail()))
            try {
                AppStateController.userController.updateUserEmail(emailField.getText());
            } catch (SystemException e) {
                warningLabel.setText(e.getMessage());
            }
        App.setRoot("userProfile");
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
    @FXML
    private void updateUserPassword() throws SystemException, IOException, InterruptedException {
        warningLabel.setText("");
        if(newPassword.getText().equals(confirmNewPassword.getText())
                && currentPassword.getText().equals(AppStateController.stateController.getCurrentUser().getPassword())
                && !confirmNewPassword.getText().isEmpty()){
            try{
                AppStateController.userController.updateUserPassword(confirmNewPassword.getText());
            } catch (PasswordDoesNotMatch e){
                warningLabel.setVisible(true);
                warningLabel.setText("Couldn't update password cause given password doesn't match current user password");
                Thread.sleep(1000);
                return;
            }
        } else {
            warningLabel.setVisible(true);
            warningLabel.setText("New passwords do not match");
            Thread.sleep(1000);
            return;
        }
        warningLabel.setVisible(true);
        warningLabel.setText("Password changed successfully");
        Thread.sleep(1000);
        App.setRoot("userProfile");
    }
}
