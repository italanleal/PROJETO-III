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

public class SessionRegisterController {
    Logger logger = Logger.getLogger(SessionRegisterController.class.getName());

    @FXML
    Label userEmail;
    @FXML
    Label warningLabel;
    @FXML
    TextField descritorField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;

    @FXML
    private void registerSession() throws IOException {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            startDate = (startDatePicker.getValue() != null) ? formatter.parse(startDatePicker.getValue().toString()): null;
            endDate = (endDatePicker.getValue() != null) ? formatter.parse(endDatePicker.getValue().toString()): null;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing Date objects", e);
        }

        if(!descritorField.getText().isEmpty()){
            boolean isCreated = AppStateController.sessionController.createNewSession(descritorField.getText());
            if(isCreated){
                if(startDate != null) AppStateController.sessionController.updateSessionStartDate(startDate);
                if(endDate != null) AppStateController.sessionController.updateSessionEndDate(endDate);

            } else {
                warningLabel.setText("Couldn't create new session");
            }
            switchToSessionManager();
        } else {
            warningLabel.setText("Couldn't create new session");
        }
    }
    @FXML
    private void switchToSessionManager() throws IOException{
        App.setRoot("sessionManager");
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
    @FXML
    private void switchToSessionList() throws IOException {
        App.setRoot("sessionList");
    }
}
