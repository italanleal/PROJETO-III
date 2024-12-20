package br.upe.userinterface;

import br.upe.entities.Event;
import br.upe.entities.Session;
import br.upe.entities.Subscription;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    String checkTheSubscriptionIdTyped() {
        String subscriptionTypeId = userRemoveSubscriptionTypeBox.getText();
        return  subscriptionTypeId;
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

    void userRemoveSubscriptionButton(MouseEvent event) throws IOException{
        List<Session> session = AppStateController.sessionController.getAllEventSessions(AppStateController.sessionController.getSession());

        Event wantedSession = session.stream().filter(item -> item.getId().equals(Long.parseLong(checkTheSubscriptionIdTyped())).findFirst().orElse(null));
        Subscription subscription = new Subscription();
        subscription.setDate(LocalDate.now());
        subscription.setSession(wantedSession);
        subscription.setUser(AppStateController.stateController.getCurrentUser());
        AppStateController.subscriptionController.removeSubscription(subscription);
    }
}