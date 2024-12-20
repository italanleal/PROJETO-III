package br.upe.userinterface;

import br.upe.util.persistencia.SystemException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;


public class SessionUpdaterController {
    @FXML
    Label warningLabel;
    @FXML
    Label eventDescritor;
    @FXML
    TextField descritorField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        eventDescritor.setText(AppStateController.stateController.getCurrentEvent().getDescription());
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
    @FXML
    private void updateSession() throws IOException {

        try {
            if(startDatePicker.getValue() != null)AppStateController.sessionController.updateSessionStartDate(startDatePicker.getValue());
            if(endDatePicker.getValue() != null) AppStateController.sessionController.updateSessionEndDate(endDatePicker.getValue());
        } catch (SystemException e) {
            warningLabel.setText(e.getMessage());
        }
        if (!descritorField.getText().isEmpty()) AppStateController.sessionController.updateSessionDescription(descritorField.getText());

        App.setRoot("sessionManager");
    }
}
