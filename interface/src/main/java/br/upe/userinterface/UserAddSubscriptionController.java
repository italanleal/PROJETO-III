package br.upe.userinterface;

import br.upe.entities.Subscription;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class UserAddSubscriptionController {

    @FXML
    Label userEmail;

    @FXML
    TextField userAddSubscriptionTextBar;

    @FXML
    Button userAddSubscriptionButton;

    @FXML
    void switchToHomeUser() throws IOException {
        App.setRoot("homeUser");
    }
    private void initialize() {
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());
    }

    @FXML
    void switchToUserListSubscription() throws IOException {
        App.setRoot("userSearchEvent");
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

    @FXML
    void getThisSubscriptionToAdd(InputMethodEvent event) {
    }

    @FXML
    void addASubscription(MouseEvent event) {
        Subscription subscription = new Subscription();
        AppStateController.subscriptionController.addSubscription();
    }
}