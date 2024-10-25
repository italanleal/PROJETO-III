package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionUpdaterController {
    Logger logger = Logger.getLogger(SessionUpdaterController.class.getName());

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
        eventDescritor.setText(AppStateController.stateController.getCurrentEvent().getDescritor());
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
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = (startDatePicker.getValue() != null) ? formatter.parse(startDatePicker.getValue().toString()): null;
            endDate = (endDatePicker.getValue() != null) ? formatter.parse(endDatePicker.getValue().toString()): null;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing Date objects", e);
        }
        if (startDate != null) AppStateController.sessionController.updateSessionStartDate(startDate);
        if (endDate != null) AppStateController.sessionController.updateSessionEndDate(endDate);
        if (!descritorField.getText().isEmpty()) AppStateController.sessionController.updateSessionDescritor(descritorField.getText());

        App.setRoot("sessionManager");
    }
}
