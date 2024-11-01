package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.InputMethodEvent;

public class UserSearchEventController {

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
    private TextField userSearchEvenTypeBox;

    @FXML
    private ListView<?> userSearchEventList;

    @FXML
    void goToHomeUser(MouseEvent event) {

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

    }

    @FXML
    void updateTheEventsShown(InputMethodEvent event) {

    }

}
