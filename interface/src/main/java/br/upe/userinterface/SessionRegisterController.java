package br.upe.userinterface;

import br.upe.util.persistencia.SystemException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionRegisterController {
    Logger logger = Logger.getLogger(SessionRegisterController.class.getName());

    @FXML
    Label userEmail;
    @FXML
    Label warningLabel;
    @FXML
    TextField titleField;
    @FXML
    TextField descritorField;
    @FXML
    TextField guestField;
    @FXML
    TextField localField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;

    @FXML
    private void registerSession() throws IOException {
        LocalDate startDate = null;
        LocalDate endDate = null;
        try {
            startDate = (startDatePicker.getValue() != null) ? startDatePicker.getValue(): null;
            endDate = (endDatePicker.getValue() != null) ? endDatePicker.getValue(): null;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing Date objects", e);
        }

        if(!descritorField.getText().isEmpty() && !titleField.getText().isEmpty() && !guestField.getText().isEmpty() && !localField.getText().isEmpty()){
            AppStateController.sessionController.createNewSession(titleField.getText(),
                    descritorField.getText(),
                    guestField.getText(),
                    localField.getText());
            try{
                AppStateController.sessionController.updateSessionStartDate(startDate);
                AppStateController.sessionController.updateSessionEndDate(endDate);
            }catch (SystemException e){
                warningLabel.setText(e.getMessage());
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
