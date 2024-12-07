package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

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
        sessaoNome.setText(AppStateController.stateController.getCurrentSession().getDescritor());
        subscriptionCount.setText(String.valueOf(AppStateController.stateController.getCurrentSession().getSubscriptions().size()));

        Date startDate = AppStateController.stateController.getCurrentSession().getStartDate();
        Date endDate = AppStateController.stateController.getCurrentSession().getEndDate();

        if (startDate != null) dataInicio.setText(DateFormat.getDateInstance().format(startDate));
        if (endDate != null) dataFim.setText(DateFormat.getDateInstance().format(endDate));
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
