package br.upe.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventRegisterController{
    Logger logger = Logger.getLogger(EventRegisterController.class.getName());

    @FXML
    Label warningLabel;
    @FXML
    Label userEmail;
    @FXML
    TextField descritorField;
    @FXML
    TextField directorField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    private void registerEvent() throws IOException {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            startDate = (startDatePicker.getValue() != null) ? formatter.parse(startDatePicker.getValue().toString()): null;
            endDate = (endDatePicker.getValue() != null) ? formatter.parse(endDatePicker.getValue().toString()): null;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing Date objects", e);
        }

        if(!descritorField.getText().isEmpty() && !directorField.getText().isEmpty()){
            boolean isCreated = AppStateController.eventController.createNewEvent(descritorField.getText(), directorField.getText());
            if(isCreated){
               if(startDate != null) AppStateController.eventController.updateEventStartDate(startDate);
               if(endDate != null) AppStateController.eventController.updateEventEndDate(endDate);

               App.setRoot("eventManager");
            } else {
                warningLabel.setText("Couldn't create new event");
            }
        } else {
            warningLabel.setText("Couldn't create new event");
        }


    }
    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());
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
    private void switchToEventList() throws IOException {
        App.setRoot("eventList");
    }
}
