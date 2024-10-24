package br.upe.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class EventUpdaterController{
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
        try {
            startDate = (startDatePicker.getValue() != null) ? DateFormat.getDateInstance().parse(startDatePicker.getValue().toString()) : null;
            endDate = (endDatePicker.getValue() != null) ? DateFormat.getDateInstance().parse(endDatePicker.getValue().toString()) : null;

        } catch (Exception e) {
            //implementar logger
        }
        if (startDate != null) AppStateController.eventController.updateEventStartDate(startDate);
        if (endDate != null) AppStateController.eventController.updateEventEndDate(endDate);
        if (!directorField.getText().isEmpty())
            AppStateController.eventController.updateEventDirector(directorField.getText());
        if (!descritorField.getText().isEmpty())
            AppStateController.eventController.updateEventDescritor(descritorField.getText());

        App.setRoot("eventManager");
    }
    @FXML
    private void registerSession() {
    }
}
