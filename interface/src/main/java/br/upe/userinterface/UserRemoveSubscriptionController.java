package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.scene.input.MouseEvent;

public class UserRemoveSubscriptionController {

    @FXML
    private Hyperlink homeUserLink;

    @FXML
    private Hyperlink registerIntoAnEvent;

    @FXML
    private Hyperlink userEditSubscriptionLink;

    @FXML
    private Hyperlink userRemoveSubscriptionsLink;

    @FXML
    private Hyperlink userListSubscriptionLink;

    @FXML
    private Hyperlink logoutLink;

    @FXML
    private Label userEmail;

    @FXML
    private TextField userRemoveSubscriptionTypeBox;

    @FXML
    private Button userRemoveSubscriptionButton;

    @FXML
    void checkTheSubscriptionTyped(MouseEvent event) {
        String subscriptionType = userRemoveSubscriptionTypeBox.getText();
        if (subscriptionType != null && !subscriptionType.isEmpty()) {
            //removeThisSubscription(subscriptionType);
        }
    }

    @FXML
    void goToHomeUser(MouseEvent event) throws IOException {
        App.setRoot("homeUser");
    }

    @FXML
    void goToUserListSubscription(MouseEvent event) throws IOException{
        App.setRoot("userSearchEvent");
    }

    @FXML
    void goToUserRegisterEvent(MouseEvent event) throws IOException{
        App.setRoot("userAddSubscription");
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }
}