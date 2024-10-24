package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventUpdaterController{
    Logger logger = Logger.getLogger(EventUpdaterController.class.getName());
    @FXML
    Label eventDescritor;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        eventDescritor.setText(AppStateController.stateController.getCurrentEvent().getDescritor());
    }
    @FXML
    TextField directorField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    TextField descritorField;
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
    private void switchToEventUpdater() throws IOException {
        App.setRoot("eventUpdater");
    }
    @FXML
    private void updateEvent() throws IOException {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = (startDatePicker.getValue() != null) ? formatter.parse(startDatePicker.getValue().toString()): null;
            endDate = (endDatePicker.getValue() != null) ? formatter.parse(endDatePicker.getValue().toString()): null;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing Date objects", e);
        }
        if (startDate != null) AppStateController.eventController.updateEventStartDate(startDate);
        if (endDate != null) AppStateController.eventController.updateEventEndDate(endDate);
        if (!directorField.getText().isEmpty())
            AppStateController.eventController.updateEventDirector(directorField.getText());
        if (!descritorField.getText().isEmpty())
            AppStateController.eventController.updateEventDescritor(descritorField.getText());

        App.setRoot("eventManager");
    }

    //todo
    @FXML
    private void registerSession() {
    }
}
