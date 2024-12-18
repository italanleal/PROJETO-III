package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class HomeUserController {

    @FXML
    Hyperlink homeUserLink;

    @FXML
    Hyperlink registerIntoAnEvent;

    @FXML
    Hyperlink userEditSubscriptionLink;

    @FXML
    Hyperlink userRemoveSubscriptionsLink;

    @FXML
    Hyperlink logoutLink;

    @FXML
    Label userEmail;

    private void initialize() {
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());
    }

    @FXML
    void switchToUserListSubscription() throws IOException {
        App.setRoot("userSearchEvent");
    }

    @FXML
    void switchToUserRegisterEvent() throws IOException{
        App.setRoot("userAddSubscription");
    }

    @FXML
    void switchToUserRemoveSubscription() throws IOException {
        App.setRoot("userRemoveSubscription");
    }

    @FXML
    void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }
}