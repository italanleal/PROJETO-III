package br.upe.userinterface;

import br.upe.entities.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;

public class SessionManagerController {
    @FXML
    Label sessaoNome;
    @FXML
    Label sessionGuest;
    @FXML
    Label sessionLocal;
    @FXML
    Label dataInicio;
    @FXML
    Label dataFim;
    @FXML
    Label subscriptionCount;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        Session session = AppStateController.stateController.getCurrentSession();

        sessaoNome.setText(session.getTitle());
        sessionGuest.setText(session.getGuest());
        sessionLocal.setText(session.getLocal());
        subscriptionCount.setText(String.valueOf(session.getSubscriptions().size()));

        LocalDate startDate = session.getStartDate();
        LocalDate endDate = session.getEndDate();

        if (startDate != null) dataInicio.setText(startDate.toString());
        if (endDate != null) dataFim.setText(endDate.toString());
    }

    @FXML
    private void switchToSessionRegister() throws IOException {
        App.setRoot("sessionRegister");
    }

    @FXML
    private void switchToHomeAdmin() throws IOException {
        App.setRoot("homeAdmin");
    }
    @FXML
    private void switchToManageEvent() throws IOException {
        if(AppStateController.stateController.getCurrentSubEvent() != null){
            App.setRoot("subEventManager");
        } else { App.setRoot("eventManager"); }
    }
    @FXML
    private void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }
    @FXML
    private void switchToSessionUpdater() throws IOException {
        App.setRoot("sessionUpdater");
    }
    @FXML
    private void switchToSessionList() throws IOException {
        App.setRoot("sessionList");
    }
}
