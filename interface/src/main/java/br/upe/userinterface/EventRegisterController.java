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

public class EventRegisterController{
    Logger logger = Logger.getLogger(EventRegisterController.class.getName());

    @FXML
    Label warningLabel;
    @FXML
    Label userEmail;
    @FXML
    TextField descriptionField;
    @FXML
    TextField directorField;
    @FXML
    TextField titleField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    private void registerEvent() throws IOException, SystemException {
        LocalDate startDate = null;
        LocalDate endDate = null;

        try {
            startDate = (startDatePicker.getValue() != null) ?startDatePicker.getValue(): null;
            endDate = (endDatePicker.getValue() != null) ? endDatePicker.getValue(): null;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing Date objects", e);
        }

        if(titleField.getText().isEmpty()){ warningLabel.setText("Couldn't create new event");}
        else {
            AppStateController.eventController.createNewEvent(titleField.getText(),
                    descriptionField.getText(),
                    directorField.getText(),
                    startDate,
                    endDate);
            App.setRoot("eventManager");
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
