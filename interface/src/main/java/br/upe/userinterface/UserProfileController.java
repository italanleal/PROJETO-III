package br.upe.userinterface;

import javafx.fxml.FXML;

import java.io.IOException;

public class UserProfileController {

    @FXML
    private void logout() throws IOException {
        AppStateController.stateController.close();
        AppStateController.authController.logout();
        App.setRoot("login");
    }
}
