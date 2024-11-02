package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

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
    private TextField userAddSubscriptionTextBar;

    @FXML
    private Button userAddSubscriptionButton;

    @FXML
    void goToHomeUser(MouseEvent event) throws IOException {
        App.setRoot("homeUser");
    }
    private void initialize() {
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());
    }

    @FXML
    void goToUserEditSubscription(MouseEvent event) throws IOException {
    }

    @FXML
    void goToUserListSubscription(MouseEvent event) throws IOException {
        App.setRoot("userSearchEvent");
    }

    @FXML
    void goToUserRegisterEvent(MouseEvent event) {

    }

    @FXML
    void goToUserRemoveSubscription(MouseEvent event) throws IOException {
        App.setRoot("userRemoveSubscription");
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }


    @FXML
    void getThisSubscriptionToAdd(InputMethodEvent event) {

    }

    @FXML
    void addASubscription(MouseEvent event) {

    }
}
