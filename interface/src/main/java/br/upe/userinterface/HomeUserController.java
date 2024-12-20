package br.upe.userinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class HomeUserController {
    @FXML
    Label userEmail;

    @FXML
    private void initialize() {
        userEmail.setText(AppStateController.stateController.getCurrentUser().getName());
    }
    @FXML
    void goToHomeUser() {

    }

    @FXML
    void goToListaDeInscricoes() throws IOException {
        App.setRoot("listaDeInscricoes");

    }

    @FXML
    void goToUserRegisterEvent() {

    }

    @FXML

    void goToUserRemoveSubscription() throws IOException {
        App.setRoot("userRemoveSubscription");
    }

    @FXML
    void logout() throws IOException {
        AppStateController.stateController.close();
        AppStateController.authController.logout();
        App.setRoot("login");
    }

    public void goToListaDeEventos() throws IOException {
        App.setRoot("listaDeEventosUS");
    }
    @FXML
    public void goToProfile() throws IOException {
        App.setRoot("userProfile");
    }
}

