package br.upe.userinterface;

import br.upe.entities.Session;
import br.upe.entities.Subscription;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import br.upe.entities.Event;
import java.io.IOException;
import java.util.List;

public class UserAddSubscriptionController {

    @FXML
    Label userEmail;

    @FXML
    TextField userAddSubscriptionTextBar;

    @FXML
    Button userAddSubscriptionButton;

    private LocalDate date;
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
    String getThisSubscriptionToAdd() {
        String subscriptionTypeId = userAddSubscriptionTextBar.getText();
        return  subscriptionTypeId;
    }

    @FXML
    void addASubscription(MouseEvent eventId) {
        List<Session> session = AppStateController.sessionController.getAllEventSessions(AppStateController.sessionController.getSession());

        Event wantedSession = session.stream().filter(item -> item.getId().equals(Long.parseLong(getThisSubscriptionToAdd())).findFirst().orElse(null));
        Subscription subscription = new Subscription();
        subscription.setDate(LocalDate.now());
        subscription.setSession(wantedSession);
        subscription.setUser(AppStateController.stateController.getCurrentUser());
        AppStateController.subscriptionController.addSubscription(subscription);
    }
}