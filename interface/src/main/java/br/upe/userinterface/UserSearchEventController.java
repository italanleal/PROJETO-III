package br.upe.userinterface;

import br.upe.entities.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.List;

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
    private ListView<List<Event>> userSearchEventList;

    @FXML
    private Button searchButton;

    @FXML
    void goToHomeUser(MouseEvent event) throws IOException {
        App.setRoot("homeUser");
    }

    @FXML
    void goToUserRegisterEvent(MouseEvent event) throws IOException {
        App.setRoot("userAddSubscription");
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
    String getThisEventNameToSearch() {
        return  userSearchEvenTypeBox.getText();
    }

    @FXML
    void updateTheEventshown(Button searchButton) {
        List<Event> totalEvents = AppStateController.eventController.getAllEvents();
        List<Event> eventWanted = totalEvents.stream().filter(item ->item.getTitle().equals(getThisEventNameToSearch()))
        userSearchEventList.getItems().addAll(eventWanted);
    }
}