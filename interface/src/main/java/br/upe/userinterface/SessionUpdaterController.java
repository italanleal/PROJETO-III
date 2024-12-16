package br.upe.userinterface;

import br.upe.util.persistencia.SystemException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        LocalDate startDate = null;
        LocalDate endDate = null;
        try {
            startDate = (startDatePicker.getValue() != null) ? startDatePicker.getValue(): null;
            endDate = (endDatePicker.getValue() != null) ? endDatePicker.getValue(): null;
            AppStateController.sessionController.updateSessionStartDate(startDate);
            AppStateController.sessionController.updateSessionEndDate(endDate);
        } catch (SystemException e) {
            warningLabel.setText(e.getMessage());
        }
        if (!descritorField.getText().isEmpty()) AppStateController.sessionController.updateSessionDescription(descritorField.getText());

        App.setRoot("sessionManager");
    }
}
