package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HomeUserController {

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
        @FXML
        void removeThisSubscription(InputMethodEvent event) {
        }
    }

    @FXML
    void goToHomeUser(MouseEvent event) throws IOException {
        App.setRoot("homeUser");
    }

    @FXML
    void goToUserEditSubscription(MouseEvent event) {

    }

    @FXML
    void goToUserListSubscription(MouseEvent event) {

    }

    @FXML
    void goToUserRegisterEvent(MouseEvent event) {

    }

    @FXML
    void goToUserRemoveSubscription(MouseEvent event) {

    }

    @FXML
    void logout(MouseEvent event) {
        AppStateController.authController.logout();
        App.setRoot("login");
    }

}
