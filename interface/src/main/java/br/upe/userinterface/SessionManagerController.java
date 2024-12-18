package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;

public class SessionManagerController {
    @FXML
    Label sessaoNome;
    @FXML
    Label dataInicio;
    @FXML
    Label dataFim;
    @FXML
    Label subscriptionCount;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        sessaoNome.setText(AppStateController.stateController.getCurrentSession().getTitle());
        subscriptionCount.setText(String.valueOf(AppStateController.stateController.getCurrentSession().getSubscriptions().size()));

        LocalDate startDate = AppStateController.stateController.getCurrentSession().getStartDate();
        LocalDate endDate = AppStateController.stateController.getCurrentSession().getEndDate();

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
    private void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }
    @FXML
    private void switchToSessionUpdater() throws IOException {
        App.setRoot("sessionUpdater");
    }
}
