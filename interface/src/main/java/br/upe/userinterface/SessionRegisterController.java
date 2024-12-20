package br.upe.userinterface;


import br.upe.util.persistencia.SystemException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Logger;

public class SessionRegisterController {
    public Button submitSessionButton;
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

        startDate = (startDatePicker.getValue() != null) ? startDatePicker.getValue(): null;
        endDate = (endDatePicker.getValue() != null) ? endDatePicker.getValue(): null;

        if(descritorField.getText().isEmpty() || titleField.getText().isEmpty() || guestField.getText().isEmpty() || localField.getText().isEmpty()){

            warningLabel.setText("Couldn't create new session");
            return;
        }
        try {
            AppStateController.sessionController.createNewSession(titleField.getText(),
                    descritorField.getText(),
                    guestField.getText(),
                    localField.getText(),startDate, endDate);
        } catch (SystemException e) {
            warningLabel.setText("Couldn't create new session cause date format was wrong");
        }
        switchToSessionManager();
    }
    @FXML
    private void switchToSessionManager() throws IOException{
        App.setRoot("sessionManager");
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
