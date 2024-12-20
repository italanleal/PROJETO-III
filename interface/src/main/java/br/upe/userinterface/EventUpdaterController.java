package br.upe.userinterface;

import br.upe.util.persistencia.SystemException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventUpdaterController{

    @FXML
    private Label userName;
    Logger logger = Logger.getLogger(EventUpdaterController.class.getName());


    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        userName.setText(AppStateController.stateController.getCurrentUser().getName());
    }

    @FXML
    TextField titleField;
    @FXML
    TextField directorField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    TextField descriptionField;

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
    private void updateEvent() throws IOException, SystemException {
        LocalDate startDate = null;
        LocalDate endDate = null;
        try {
            startDate = (startDatePicker.getValue() != null) ? startDatePicker.getValue(): null;
            endDate = (endDatePicker.getValue() != null) ? endDatePicker.getValue(): null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing Date objects", e);
        }
        if (startDate != null)
            AppStateController.eventController.updateEventStartDate(startDate);
        if (endDate != null)
            AppStateController.eventController.updateEventEndDate(endDate);
        if (!directorField.getText().isEmpty())
            AppStateController.eventController.updateEventDirector(directorField.getText());
        if (!descriptionField.getText().isEmpty())
            AppStateController.eventController.updateEventDescription(descriptionField.getText());
        if(!titleField.getText().isEmpty())
            AppStateController.eventController.updateEventTitle(titleField.getText());

        App.setRoot("eventManager");
    }

    @FXML
    private void switchToManageEvent() throws IOException{
        App.setRoot("eventManager");
    }
    @FXML
    private void switchToSubEventRegister() throws IOException{
        App.setRoot("subEventRegister");
    }
    @FXML
    private void switchToSubEventList() throws IOException{
        App.setRoot("subEventList");
    }

}
